package de.neo.prodtp.util;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import de.neo.prodtp.ProdTPMain;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class TPARequest {
	
	private UUID sender;
	private UUID receiver;
	private boolean tpahere;
	private long time;
	
	public TPARequest(UUID sender, UUID receiver) {
		this(sender, receiver, false);
	}
	
	public TPARequest(UUID sender, UUID receiver, boolean tpahere) {
		this.sender = sender;
		this.receiver = receiver;
		this.tpahere = tpahere;
		this.time = System.currentTimeMillis() / 1000;
	}
	
	public void accept() {
		OfflinePlayer s = Bukkit.getOfflinePlayer(this.sender);
		OfflinePlayer r = Bukkit.getOfflinePlayer(this.receiver);
		if(this.tpahere) {
			if(s.isOnline() && r.isOnline()) {
				s.getPlayer().sendMessage(ProdTPMain.getMessage("tpahere_accepted").replace("%player%", r.getName()));
				TPUtil.safeTP(r.getPlayer(), s.getPlayer());
			}else if(s.isOnline()) {
				s.getPlayer().sendMessage(ProdTPMain.getMessage("expired"));
			}else if(r.isOnline()) {
				r.getPlayer().sendMessage(ProdTPMain.getMessage("expired"));
			}
		}else {
			if(s.isOnline() && r.isOnline()) {
				s.getPlayer().sendMessage(ProdTPMain.getMessage("tpa_accepted").replace("%player%", r.getName()));
				TPUtil.safeTP(s.getPlayer(), r.getPlayer());
			}else if(s.isOnline()) {
				s.getPlayer().sendMessage(ProdTPMain.getMessage("expired"));
			}else if(r.isOnline()) {
				r.getPlayer().sendMessage(ProdTPMain.getMessage("expired"));
			}
		}
	}
	
	public void sendInvite() {
		if(!ProdTPMain.getInstance().getProdTPPlayerManager().get(this.receiver).isTpBlocked()) {
			OfflinePlayer r = Bukkit.getOfflinePlayer(this.receiver);
			OfflinePlayer s = Bukkit.getOfflinePlayer(this.sender);
			if(r.isOnline() && s.isOnline()) {
				BaseComponent[] texts_arr = TextComponent.fromLegacyText(ProdTPMain.getInstance().getConfig().getString("messages.accepttpa"));
				ArrayList<BaseComponent> texts = new ArrayList<>();
				if(this.tpahere) {
					for (BaseComponent elem : texts_arr) {
						elem.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§aKlicke hier zum akzeptieren")));
						elem.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + s.getName()));
						texts.add(elem);
					}
					BaseComponent[] tmp_arr = TextComponent.fromLegacyText(ProdTPMain.getInstance().getConfig().getString("messages.denytpa"));
					ArrayList<BaseComponent> tmp = new ArrayList<BaseComponent>();
					for (BaseComponent elem : tmp_arr) {
						elem.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cKlicke hier zum ablehnen")));
						elem.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny " + s.getName()));
						tmp.add(elem);
					}
					texts.addAll(tmp);
					r.getPlayer().spigot().sendMessage(TextComponent.fromLegacyText(ProdTPMain.getMessage("tpahere").replace("%player%", s.getName())));
					r.getPlayer().spigot().sendMessage(texts.toArray(new BaseComponent[0]));
					s.getPlayer().sendMessage(ProdTPMain.getMessage("sent"));
				}else {
					for (BaseComponent elem : texts_arr) {
						elem.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§aKlicke hier zum akzeptieren")));
						elem.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + s.getName()));
						texts.add(elem);
					}
					BaseComponent[] tmp_arr = TextComponent.fromLegacyText(ProdTPMain.getInstance().getConfig().getString("messages.denytpa"));
					ArrayList<BaseComponent> tmp = new ArrayList<BaseComponent>();
					for (BaseComponent elem : tmp_arr) {
						elem.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cKlicke hier zum ablehnen")));
						elem.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny " + s.getName()));
						tmp.add(elem);
					}
					texts.addAll(tmp);
					r.getPlayer().spigot().sendMessage(TextComponent.fromLegacyText(ProdTPMain.getMessage("tpa").replace("%player%", s.getName())));
					r.getPlayer().spigot().sendMessage(texts.toArray(new BaseComponent[0]));
					s.getPlayer().sendMessage(ProdTPMain.getMessage("sent"));
				}
			}
		}else {
			if(Bukkit.getOfflinePlayer(this.sender).isOnline()) {
				Bukkit.getOfflinePlayer(this.sender).getPlayer().sendMessage(ProdTPMain.getMessage("tp_blocked"));
			}
		}
	}
	
	public boolean isExpired() {
		return ProdTPMain.getInstance().getConfig().getInt("options.tpa_expire") < ((System.currentTimeMillis() / 1000) - this.time);
	}
	
	public UUID getSender() {
		return this.sender;
	}
	
	public UUID getReceiver() {
		return this.receiver;
	}
	
	public boolean isTPAHere() {
		return this.tpahere;
	}
}
