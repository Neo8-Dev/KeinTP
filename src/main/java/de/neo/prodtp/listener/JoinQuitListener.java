package de.neo.prodtp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.neo.prodtp.ProdTPMain;

public class JoinQuitListener implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		ProdTPMain.getInstance().getProdTPPlayerManager().unregister(e.getPlayer().getUniqueId());
	}
}
