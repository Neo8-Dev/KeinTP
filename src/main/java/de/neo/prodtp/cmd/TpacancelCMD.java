package de.neo.prodtp.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.neo.prodtp.ProdTPMain;
import de.neo.prodtp.util.TPARequest;

public class TpacancelCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("tp.tpacancel")) {
				ProdTPMain main = ProdTPMain.getInstance();
				TPARequest out = main.getProdTPPlayerManager().get(p.getUniqueId()).getOutgoing();
				if(out == null) {
					p.sendMessage(ProdTPMain.getMessage("no_outgoing_tpa"));
					return false;
				}
				Player t = Bukkit.getPlayer(out.getReceiver());
				if(t != null && !t.isOnline()) {
					p.sendMessage(ProdTPMain.getMessage("target_offline"));
					return false;
				}
				if(t.getName().equals(p.getName())) {
					p.sendMessage(ProdTPMain.getMessage("LACK_GESOFFEN"));
					return false;
				}
				TPARequest in = main.getProdTPPlayerManager().get(t.getUniqueId()).getTPARequest(out.getReceiver());
				if(in != null) {
					main.getProdTPPlayerManager().get(t.getUniqueId()).removeTPARequest(in);
					main.getProdTPPlayerManager().get(p.getUniqueId()).setOutgoing(null);
					p.sendMessage(ProdTPMain.getMessage("tpa_aborted"));
					t.sendMessage(ProdTPMain.getMessage("tpa_canceled").replace("%player%", p.getName()));
				}else {
					p.sendMessage(ProdTPMain.getMessage("no_tpreq"));
				}
			}else {
				p.sendMessage(ProdTPMain.getMessage("no_perm"));
			}
		}
		return false;
	}

}
