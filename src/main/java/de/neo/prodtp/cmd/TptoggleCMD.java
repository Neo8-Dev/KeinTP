package de.neo.prodtp.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.neo.prodtp.ProdTPMain;
import de.neo.prodtp.util.ProdTPPlayer;

public class TptoggleCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("tp.tptoggle")) {
				ProdTPPlayer ptp = ProdTPMain.getInstance().getProdTPPlayerManager().get(p.getUniqueId());
				if(ptp.isTpBlocked()) {
					ptp.setTPBlocked(false);
					p.sendMessage(ProdTPMain.getMessage("tptoggled_on"));
				}else {
					ptp.setTPBlocked(true);
					p.sendMessage(ProdTPMain.getMessage("tptoggled_off"));
				}
			}else {
				p.sendMessage(ProdTPMain.getMessage("no_perm"));
			}
		}
		return false;
	}

}
