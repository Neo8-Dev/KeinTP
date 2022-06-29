package de.neo.prodtp.util;

import java.util.HashMap;
import java.util.UUID;

import de.neo.nlaf.manager.Manageable;

public class ProdTPPlayer implements Manageable<UUID> {
	
	private UUID uuid;
	private boolean blocked;
	private HashMap<UUID, TPARequest> req;
	private TPARequest out;
	private long time;
	private HashMap<UUID, Long> cooldown;
	private long tpaTime;
	
	public ProdTPPlayer(UUID uuid, boolean blocked) {
		this.uuid = uuid;
		this.blocked = blocked;
		this.req = new HashMap<>();
		this.time = 0L;
		this.cooldown = new HashMap<>();
	}
	
	public void addTPARequest(TPARequest req) {
		this.req.put(req.getSender(), req);
	}
	
	public void setOutgoing(TPARequest req) {
		this.out = req;
	}
	
	public TPARequest getOutgoing() {
		return this.out;
	}
	
	public void removeTPARequest(TPARequest req) {
		this.req.remove(req.getSender());
	}
	
	public TPARequest getTPARequest(UUID sender) {
		return this.req.get(sender);
	}
	
	public void setTPBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
	public boolean isTpBlocked() {
		return this.blocked;
	}
	
	public void setLastTP() {
		this.time = System.currentTimeMillis() / 1000;
	}
	
	public long getLastTP() {
		return this.time;
	}

	public void setCooldown(UUID uuid, long cooldownLength) {
		this.cooldown.put(uuid, System.currentTimeMillis() + (cooldownLength * 1000));
	}

	public boolean isOnCooldown(UUID uuid) {
		return this.cooldown.getOrDefault(uuid, 0L) <= System.currentTimeMillis();
	}

	public void setTpaTime(long time) {
		this.tpaTime = time;
	}

	public long getTpaTime() {
		return this.tpaTime;
	}

	@Override
	public UUID getIdentifier() {
		return this.uuid;
	}
}
