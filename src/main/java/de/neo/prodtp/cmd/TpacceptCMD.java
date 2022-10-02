package de.neo.prodtp.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.neo.prodtp.ProdTPMain;
import de.neo.prodtp.util.TPARequest;

public class TpacceptCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("tp.tpaccept")) {
				ProdTPMain main = ProdTPMain.getInstance();
				if(args.length == 1) {
					Player t = Bukkit.getPlayer(args[0]);
					if(t != null && t.isOnline()) {
						if(t.getName().equals(p.getName())) {
							p.sendMessage(ProdTPMain.getMessage("LACK_GESOFFEN"));
							return false;
						}
						TPARequest req = main.getProdTPPlayerManager().get(p.getUniqueId()).getTPARequest(t.getUniqueId());
						if(req != null && (req.equals(main.getProdTPPlayerManager().get(t.getUniqueId()).getOutgoing()) || req.isTPAHere()) && !req.isExpired()) {
							req.accept();
							main.getProdTPPlayerManager().get(p.getUniqueId()).removeTPARequest(req);
						}else {
							if(req.isExpired()) {
								p.sendMessage(ProdTPMain.getMessage("expired"));
								return false;
							}
							p.sendMessage(ProdTPMain.getMessage("no_tpreq"));
						}
					}else {
						p.sendMessage(ProdTPMain.getMessage("target_offline"));
					}
				}else {
					p.sendMessage(ProdTPMain.getMessage("usage_tpaccept"));
				}
			}else {
				p.sendMessage(ProdTPMain.getMessage("no_perm"));
			}
		}
		return false;
	}

}
