package de.neo.prodtp.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.neo.prodtp.ProdTPMain;
import de.neo.prodtp.util.TPARequest;

public class TpdenyCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("tp.tpdeny")) {
				ProdTPMain main = ProdTPMain.getInstance();
				if(args.length == 1) {
					Player t = Bukkit.getPlayer(args[0]);
					if(t != null && t.isOnline()) {
						if(t.getName().equals(p.getName())) {
							p.sendMessage(ProdTPMain.getMessage("LACK_GESOFFEN"));
							return false;
						}
						TPARequest req = main.getProdTPPlayerManager().get(p.getUniqueId()).getTPARequest(t.getUniqueId());
						if(req != null) {
							main.getProdTPPlayerManager().get(p.getUniqueId()).removeTPARequest(req);
							p.sendMessage(ProdTPMain.getMessage("tpdeny"));
							t.sendMessage(ProdTPMain.getMessage("tpdenied"));
						}else {
							p.sendMessage(ProdTPMain.getMessage("no_tpreq"));
						}
					}else {
						p.sendMessage(ProdTPMain.getMessage("target_offline"));
					}
				}else {
					p.sendMessage(ProdTPMain.getMessage("usage_tpdeny"));
				}
			}else {
				p.sendMessage(ProdTPMain.getMessage("no_perm"));
			}
		}
		return false;
	}

}
