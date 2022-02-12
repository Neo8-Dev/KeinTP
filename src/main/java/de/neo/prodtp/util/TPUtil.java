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
	
	public static void safeTP(Player p, Location loc) {
		if(loc.getWorld().getEnvironment().equals(Environment.NETHER)) {
			if(!p.getAdvancementProgress(Bukkit.getAdvancement(NamespacedKey.minecraft("story/enter_the_nether"))).isDone()) {
				p.sendMessage(ProdTPMain.getMessage("tp_not_allowed"));
				return;
			}
		}
		if(loc.getWorld().getEnvironment().equals(Environment.THE_END)) {
			if(!p.getAdvancementProgress(Bukkit.getAdvancement(NamespacedKey.minecraft("story/enter_the_end"))).isDone()) {
				p.sendMessage(ProdTPMain.getMessage("tp_not_allowed"));
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
						return;
					}
				}
			}
		}
		p.sendMessage(ProdTPMain.getMessage("can_not_safetp"));
	}
	
	public static void safeTP(Player p, Player t) {
		safeTP(p, t.getLocation());
	}
}
