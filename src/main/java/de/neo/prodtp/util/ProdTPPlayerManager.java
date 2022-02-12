package de.neo.prodtp.util;

import java.util.UUID;

import de.neo.nlaf.manager.Manager;

public class ProdTPPlayerManager extends Manager<UUID, ProdTPPlayer> {
	
	@Override
	public boolean isRegistered(UUID uuid) {
		if(!super.isRegistered(uuid)) {
			this.register(new ProdTPPlayer(uuid, false));
		}
		return true;
	}
	
	@Override
	public boolean isRegistered(ProdTPPlayer player) {
		if(!super.isRegistered(player)) {
			this.register(player);
		}
		return true;
	}
	
	@Override
	public ProdTPPlayer get(UUID uuid) {
		if(super.get(uuid) == null) {
			this.register(new ProdTPPlayer(uuid, false));
		}
		return super.get(uuid);
	}
}
