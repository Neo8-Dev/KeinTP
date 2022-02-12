package de.neo.prodtp.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.neo.prodtp.ProdTPMain;

public class ProdTPVerCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.hasPermission("neopl.ver")) {
			sender.sendMessage("");
			sender.sendMessage("ProdTP");
			sender.sendMessage("Version: " + ProdTPMain.getInstance().getDescription().getVersion());
			sender.sendMessage("");
		}
		return false;
	}
	
}
