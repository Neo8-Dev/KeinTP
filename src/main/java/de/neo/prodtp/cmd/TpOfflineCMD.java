package de.neo.prodtp.cmd;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

import de.neo.prodtp.ProdTPMain;
import de.neo.prodtp.util.TPUtil;

import java.util.UUID;

public class TpOfflineCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("tp.tpoffline")) {
				if(args.length == 1) {
					User u = Essentials.getPlugin(Essentials.class).getOfflineUser(args[0]);
					if(u != null) {
						TPUtil.safeTP(p, u.getLogoutLocation());
					}else {
						p.sendMessage(ProdTPMain.getMessage("unknown_target"));
					}
				}else {
					p.sendMessage(ProdTPMain.getMessage("usage_tpoffline"));
				}
			}else {
				p.sendMessage(ProdTPMain.getMessage("no_perm"));
			}
		}
		return false;
	}

}
