package de.neo.prodtp.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.neo.prodtp.ProdTPMain;
import de.neo.prodtp.util.TPARequest;

public class TpaHereCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("tp.tpahere")) {
				if(args.length == 1) {
					Player t = Bukkit.getPlayer(args[0]);
					if(t != null && t.isOnline()) {
						if(t.getName().equals(p.getName())) {
							p.sendMessage(ProdTPMain.getMessage("LACK_GESOFFEN"));
							return false;
						}
						if(ProdTPMain.getInstance().getProdTPPlayerManager().get(t.getUniqueId()).getTPARequest(p.getUniqueId()) == null) {
							TPARequest request = new TPARequest(p.getUniqueId(), t.getUniqueId(), true);
							request.sendInvite();
							ProdTPMain.getInstance().getProdTPPlayerManager().get(t.getUniqueId()).addTPARequest(request);
						}else {
							p.sendMessage(ProdTPMain.getMessage("tp_not_expired"));
						}
					}else {
						p.sendMessage(ProdTPMain.getMessage("target_offline"));
					}
				}else {
					p.sendMessage(ProdTPMain.getMessage("usage_tpahere"));
				}
			}else {
				p.sendMessage(ProdTPMain.getMessage("no_perm"));
			}
		}
		return false;
	}

}
