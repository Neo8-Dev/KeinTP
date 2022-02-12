package de.neo.prodtp;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.neo.prodtp.cmd.ProdTPVerCMD;
import de.neo.prodtp.cmd.TpAllCMD;
import de.neo.prodtp.cmd.TpCMD;
import de.neo.prodtp.cmd.TpHereCMD;
import de.neo.prodtp.cmd.TpOfflineCMD;
import de.neo.prodtp.cmd.TpaCMD;
import de.neo.prodtp.cmd.TpaHereCMD;
import de.neo.prodtp.cmd.TpaallCMD;
import de.neo.prodtp.cmd.TpacancelCMD;
import de.neo.prodtp.cmd.TpacceptCMD;
import de.neo.prodtp.cmd.TpdenyCMD;
import de.neo.prodtp.cmd.TpoCMD;
import de.neo.prodtp.cmd.TpoHereCMD;
import de.neo.prodtp.cmd.TptoggleCMD;
import de.neo.prodtp.listener.JoinQuitListener;
import de.neo.prodtp.listener.PlayerDamageListener;
import de.neo.prodtp.listener.PlayerTeleportListener;
import de.neo.prodtp.util.ProdTPPlayerManager;

public class ProdTPMain extends JavaPlugin {
	
	private static ProdTPMain INSTANCE;
	
	public static ProdTPMain getInstance() {
		return INSTANCE;
	}
	
	public static String getMessage(String key) {
		return getInstance().getConfig().getString("messages.prefix") + " " + getInstance().getConfig().getString("messages." + key);
	}
	
	private ProdTPPlayerManager pMan;
	
	public void onEnable() {
		INSTANCE = this;
		Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDamageListener(), this);
		getCommand("prodtpver").setExecutor(new ProdTPVerCMD());
		getCommand("tpaall").setExecutor(new TpaallCMD());
		getCommand("tpacancel").setExecutor(new TpacancelCMD());
		getCommand("tpaccept").setExecutor(new TpacceptCMD());
		getCommand("tpa").setExecutor(new TpaCMD());
		getCommand("tpahere").setExecutor(new TpaHereCMD());
		getCommand("tpall").setExecutor(new TpAllCMD());
		getCommand("tp").setExecutor(new TpCMD());
		getCommand("tpdeny").setExecutor(new TpdenyCMD());
		getCommand("tphere").setExecutor(new TpHereCMD());
		getCommand("tpo").setExecutor(new TpoCMD());
		getCommand("tpohere").setExecutor(new TpoHereCMD());
		getCommand("tptoggle").setExecutor(new TptoggleCMD());
		getCommand("tpoffline").setExecutor(new TpOfflineCMD());
		this.pMan = new ProdTPPlayerManager();
		loadConfig();
	}
	
	public void loadConfig() {
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		File conf = new File(getDataFolder(), "config.yml");
		if(!conf.exists()) {
			saveDefaultConfig();
		}
	}
	
	public ProdTPPlayerManager getProdTPPlayerManager() {
		return this.pMan;
	}

}