package de.neo.prodtp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import de.neo.prodtp.ProdTPMain;

public class PlayerTeleportListener implements Listener {
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		if(e.getCause().equals(TeleportCause.PLUGIN)) {
			ProdTPMain.getInstance().getProdTPPlayerManager().get(e.getPlayer().getUniqueId()).setLastTP();
		}
	}
}
