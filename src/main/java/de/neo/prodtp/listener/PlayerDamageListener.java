package de.neo.prodtp.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import de.neo.prodtp.ProdTPMain;
import de.neo.prodtp.util.ProdTPPlayer;

public class PlayerDamageListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity().getType().equals(EntityType.PLAYER)) {
			ProdTPPlayer tp = ProdTPMain.getInstance().getProdTPPlayerManager().get(e.getEntity().getUniqueId());
			if(((System.currentTimeMillis() / 1000) - tp.getLastTP()) <= 5) {
				e.setCancelled(true);
			}
		}
	}
}
