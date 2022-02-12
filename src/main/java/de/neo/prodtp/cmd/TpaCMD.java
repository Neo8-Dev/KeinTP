package de.neo.prodtp.cmd;

import de.neo.prodtp.util.ProdTPPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.neo.prodtp.ProdTPMain;
import de.neo.prodtp.util.TPARequest;

public class TpaCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("tp.tpa")) {
				if(args.length == 1) {
					Player t = Bukkit.getPlayer(args[0]);
					if(t != null && t.isOnline()) {
						if(t.getName().equals(p.getName())) {
							p.sendMessage(ProdTPMain.getMessage("LACK_GESOFFEN"));
						}
						ProdTPPlayerManager mgr = ProdTPMain.getInstance().getProdTPPlayerManager();
						TPARequest in = mgr.get(t.getUniqueId()).getTPARequest(p.getUniqueId());
						if(in != null) {
							mgr.get(t.getUniqueId()).removeTPARequest(in);
							mgr.get(p.getUniqueId()).setOutgoing(null);
						}
						TPARequest request = new TPARequest(p.getUniqueId(), t.getUniqueId(), false);
						request.sendInvite();
						ProdTPMain.getInstance().getProdTPPlayerManager().get(t.getUniqueId()).addTPARequest(request);
						ProdTPMain.getInstance().getProdTPPlayerManager().get(p.getUniqueId()).setOutgoing(request);
						p.sendMessage(ProdTPMain.getMessage("sent"));
					}else {
						p.sendMessage(ProdTPMain.getMessage("target_offline"));
					}
				}else {
					p.sendMessage(ProdTPMain.getMessage("usage_tpa"));
				}
			}else {
				p.sendMessage(ProdTPMain.getMessage("no_perm"));
			}
		}
		return false;
	}

}
