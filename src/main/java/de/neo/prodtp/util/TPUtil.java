package de.neo.prodtp.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.Material;

import de.neo.prodtp.ProdTPMain;

public class TPUtil {
	
	private TPUtil() {
	}
	
	public static void safeTP(Player p, Location loc, Player receiver) {
		if(loc.getWorld().getEnvironment().equals(Environment.NETHER)) {
			if(!p.getAdvancementProgress(Bukkit.getAdvancement(NamespacedKey.minecraft("story/enter_the_nether"))).isDone()) {
				receiver.sendMessage(ProdTPMain.getMessage("tp_not_allowed_nether"));
				return;
			}
		}
		if(loc.getWorld().getEnvironment().equals(Environment.THE_END)) {
			if(!p.getAdvancementProgress(Bukkit.getAdvancement(NamespacedKey.minecraft("story/enter_the_end"))).isDone()) {
				receiver.sendMessage(ProdTPMain.getMessage("tp_not_allowed_end"));
				return;
			}
		}
		double defaulty = loc.getY();
		for(double i = defaulty; i > defaulty - 10; i--) {
			loc.setY(i);
			if(loc.getBlock().getType().isSolid()) {
				loc.setY(i + 1);
				if(!loc.getBlock().getType().isSolid() && !loc.getBlock().getType().equals(Material.LAVA)) {
					loc.setY(i + 1);
					if(!loc.getBlock().getType().isSolid() && !loc.getBlock().getType().equals(Material.LAVA)) {
						loc.setY(i + 1);
						p.teleport(loc, TeleportCause.PLUGIN);
						p.sendMessage(ProdTPMain.getMessage("teleported"));
						receiver.sendMessage(ProdTPMain.getMessage("teleported"));
						return;
					}
				}
			}
		}
		for(double i = defaulty + 10; i > defaulty; i--) {
			loc.setY(i);
			if(loc.getBlock().getType().isSolid()) {
				loc.setY(i + 1);
				if(!loc.getBlock().getType().isSolid() && !loc.getBlock().getType().equals(Material.LAVA)) {
					loc.setY(i + 1);
					if(!loc.getBlock().getType().isSolid() && !loc.getBlock().getType().equals(Material.LAVA)) {
						loc.setY(i + 1);
						p.teleport(loc, TeleportCause.PLUGIN);
						p.sendMessage(ProdTPMain.getMessage("teleported"));
						receiver.sendMessage(ProdTPMain.getMessage("teleported"));
						return;
					}
				}
			}
		}
		receiver.sendMessage(ProdTPMain.getMessage("can_not_safetp"));
	}

	public static void safeTP(Player p, Location loc) {
		safeTP(p, loc, p);
	}
	
	public static void safeTP(Player p, Player t, boolean switchMessage) {
		if(switchMessage) safeTP(p, t.getLocation(), t);
		else safeTP(t, t.getLocation(), p);
	}

	public static void safeTP(Player p, Player t) {
		safeTP(p, t, false);
	}
}
