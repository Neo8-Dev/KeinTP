package de.neo.prodtp.util;

import org.bukkit.Bukkit;
import de.neo.prodtp.ProdTPMain;

public class DebugUtil {
	
	private DebugUtil() {
	}
	
	public static void printDebug(String output) {
		Bukkit.broadcast("[" + ProdTPMain.class.getName() + "] " + output, "neopl.debug");
	}
}
