package de.neo.prodtp.cmd;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.neo.prodtp.ProdTPMain;
import de.neo.prodtp.util.TPUtil;

public class TpCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("tp.tp")) {
				if(args.length == 1) {
					Player t = Bukkit.getPlayer(args[0]);
					if(t != null && t.isOnline()) {
						if(t.getName().equals(p.getName())) {
							p.sendMessage(ProdTPMain.getMessage("LACK_GESOFFEN"));
							return false;
						}
						if(!ProdTPMain.getInstance().getProdTPPlayerManager().get(p.getUniqueId()).isTpBlocked()) {
							TPUtil.safeTP(p, t);
						}else {
							p.sendMessage(ProdTPMain.getMessage("tp_blocked"));
						}
					}else {
						p.sendMessage(ProdTPMain.getMessage("target_offline"));
					}
				}else if(args.length == 3) {
					try {
						Location loc = new Location(p.getWorld(), Double.valueOf(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2]), p.getLocation().getYaw(), p.getLocation().getPitch());
						TPUtil.safeTP(p, loc);
					}catch(NumberFormatException e) {
						e.printStackTrace();
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
