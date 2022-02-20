package de.neo.prodtp.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.neo.prodtp.ProdTPMain;
import de.neo.prodtp.util.TPUtil;

public class TpHereCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("tp.tphere")) {
				if(args.length == 1) {
					Player t = Bukkit.getPlayer(args[0]);
					if(t != null && t.isOnline()) {
						if(t.getName().equals(p.getName())) {
							p.sendMessage(ProdTPMain.getMessage("LACK_GESOFFEN"));
							return false;
						}
						if(!ProdTPMain.getInstance().getProdTPPlayerManager().get(p.getUniqueId()).isTpBlocked()) {
							TPUtil.safeTP(t, p);
						}else {
							p.sendMessage(ProdTPMain.getMessage("tp_blocked"));
						}
					}else {
						p.sendMessage(ProdTPMain.getMessage("target_offline"));
					}
				}else {
					p.sendMessage(ProdTPMain.getMessage("usage_tphere"));
				}
			}else {
				p.sendMessage(ProdTPMain.getMessage("no_perm"));
			}
		}
		return false;
	}

}
