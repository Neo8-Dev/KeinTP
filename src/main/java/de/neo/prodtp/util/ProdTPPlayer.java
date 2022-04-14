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
	
	public ProdTPPlayer(UUID uuid, boolean blocked) {
		this.uuid = uuid;
		this.blocked = blocked;
		this.req = new HashMap<>();
		this.time = 0L;
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

	@Override
	public UUID getIdentifier() {
		return this.uuid;
	}
}
