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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class TPARequest {
	
	private final UUID sender;
	private final UUID receiver;
	private final boolean tpahere;
	private final long time;
	
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
		OfflinePlayer sender = Bukkit.getOfflinePlayer(this.sender);
		OfflinePlayer receiver = Bukkit.getOfflinePlayer(this.receiver);
		if(sender.isOnline() && receiver.isOnline()) {
			sender.getPlayer().sendMessage(ProdTPMain.getMessage("tpa" + (tpahere ? "here" : "") + "_accepted")
					.replace("%player%", receiver.getName()));
			TPUtil.safeTP((tpahere ? receiver : sender).getPlayer(), (tpahere ? sender : receiver).getPlayer());
			ProdTPPlayer tpPlayer = ProdTPMain.getInstance().getProdTPPlayerManager().get(sender.getUniqueId());
			tpPlayer.setCooldown(receiver.getUniqueId(), ProdTPMain.getInstance().getConfig()
					.getInt("options.tpa_cooldown"));
		}else if(sender.isOnline()) {
			sender.getPlayer().sendMessage(ProdTPMain.getMessage("expired"));
		}else if(receiver.isOnline()) {
			receiver.getPlayer().sendMessage(ProdTPMain.getMessage("expired"));
		}
	}
	
	public void sendInvite() {
		if(!ProdTPMain.getInstance().getProdTPPlayerManager().get(this.receiver).isTpBlocked()) {
			OfflinePlayer receiver = Bukkit.getOfflinePlayer(this.receiver);
			OfflinePlayer sender = Bukkit.getOfflinePlayer(this.sender);
			FileConfiguration config = ProdTPMain.getInstance().getConfig();
			ProdTPPlayer tpPlayer = ProdTPMain.getInstance().getProdTPPlayerManager().get(sender.getUniqueId());
			if(tpPlayer.getTpaTime() <= System.currentTimeMillis()) {
				Player p = sender.getPlayer();
				p.sendMessage(ProdTPMain.getMessage("on_cooldown"));
				return;
			}
			tpPlayer.setTpaTime(System.currentTimeMillis() + (config.getInt("options.tpa_sent_cooldown") * 1000L));

			BaseComponent[] texts_arr = TextComponent.fromLegacyText(ProdTPMain.getMessage("accepttpa"));
			ArrayList<BaseComponent> texts = new ArrayList<>();
			for (BaseComponent elem : texts_arr) {
				elem.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§aKlicke hier zum akzeptieren")));
				elem.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + sender.getName()));
				texts.add(elem);
			}
			BaseComponent[] tmp_arr = TextComponent.fromLegacyText(ProdTPMain.getMessage("denytpa"));
			ArrayList<BaseComponent> tmp = new ArrayList<>();
			for (BaseComponent elem : tmp_arr) {
				elem.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cKlicke hier zum ablehnen")));
				elem.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny " + sender.getName()));
				tmp.add(elem);
			}
			texts.addAll(tmp);
			receiver.getPlayer().spigot().sendMessage(TextComponent
					.fromLegacyText(ProdTPMain.getMessage(this.tpahere ? "tpahere" : "tpa")
							.replace("%player%", sender.getName())));
			receiver.getPlayer().spigot().sendMessage(texts.toArray(new BaseComponent[0]));
			sender.getPlayer().sendMessage(ProdTPMain.getMessage("sent"));
		}else {

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
