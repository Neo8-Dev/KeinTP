package de.neo.prodtp.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import de.neo.prodtp.ProdTPMain;

public class TpoCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("tp.tpo")) {
				if(args.length == 1) {
					Player t = Bukkit.getPlayer(args[0]);
					if(t != null && t.isOnline()) {
						if(t.getName().equals(p.getName())) {
							p.sendMessage(ProdTPMain.getMessage("LACK_GESOFFEN"));
						}
						p.teleport(t, TeleportCause.PLUGIN);
						p.sendMessage(ProdTPMain.getMessage("teleported"));
					}else {
						p.sendMessage(ProdTPMain.getMessage("target_offline"));
					}
				}else {
					p.sendMessage(ProdTPMain.getMessage("usage_tp"));
				}
			}else {
				p.sendMessage(ProdTPMain.getMessage("no_perm"));
			}
		}
		return false;
	}

}
