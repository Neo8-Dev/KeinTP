package de.neo.prodtp.cmd;

import de.neo.prodtp.ProdTPMain;
import de.neo.prodtp.util.NBTUtil;
import de.neo.prodtp.util.TPUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpOfflineCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("tp.tpoffline")) {
				if(args.length == 1) {
					OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
					if(t.hasPlayedBefore()) {
						Location loc = NBTUtil.getLastLocation(t.getUniqueId());
						TPUtil.safeTP(p, loc);
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
