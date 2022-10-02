package de.neo.prodtp.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.neo.prodtp.ProdTPMain;
import de.neo.prodtp.util.TPUtil;

public class TpAllCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("tp.tpall")) {
				for(Player t : Bukkit.getOnlinePlayers()) {
					if(t.getName().equals(p.getName())) {
						continue;
					}
					TPUtil.safeTP(t, p);
					p.sendMessage(ProdTPMain.getMessage("teleported") + " " + ProdTPMain.getInstance().getConfig().getString("messages.tp_all_player_ext").replace("%player%", t.getName()));
				}
				p.sendMessage(ProdTPMain.getMessage("tp_all_players"));
			}else {
				p.sendMessage(ProdTPMain.getMessage("no_perm"));
			}
		}
		return false;
	}

}
