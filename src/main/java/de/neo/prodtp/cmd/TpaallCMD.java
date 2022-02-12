package de.neo.prodtp.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.neo.prodtp.ProdTPMain;
import de.neo.prodtp.util.TPARequest;

public class TpaallCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("tp.tpaall")) {
				for(Player t : Bukkit.getOnlinePlayers()) {
					if(t.getName().equals(p.getName())) {
						continue;
					}
					TPARequest req = new TPARequest(p.getUniqueId(), t.getUniqueId(), true);
					req.sendInvite();
					ProdTPMain.getInstance().getProdTPPlayerManager().get(t.getUniqueId()).addTPARequest(req);
				}
				p.sendMessage(ProdTPMain.getMessage("tpa_all_players"));
			}else {
				p.sendMessage(ProdTPMain.getMessage("no_perm"));
			}
		}
		return false;
	}

}
