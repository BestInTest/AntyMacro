package me.antymacro.listeners;

import me.antymacro.Main;
import me.antymacro.Utils.ChatFix;
import me.antymacro.managers.User;
import me.antymacro.managers.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ClickListener implements Listener {
	int maxCPS = Main.getPlugin().getConfig().getInt("max_cps");
	int maxCPSkick = Main.getPlugin().getConfig().getInt("max-cps-kick");
	boolean async = Main.getPlugin().getConfig().getBoolean("async");

	String kickMessageUp = ChatFix.fixColor(Main.getPlugin().getConfig().getString("warn-message-up").replaceAll("<CPSLimit>", String.valueOf(maxCPS)));
	String kickMessageDown = ChatFix.fixColor(Main.getPlugin().getConfig().getString("warn-message-down").replaceAll("<CPSLimit>", String.valueOf(maxCPS)));
	String kickReason = ChatFix.fixColor(Main.getPlugin().getConfig().getString("kick-reason").replaceAll("<NewLine>", "\n").replaceAll("<CPSLimit>", String.valueOf(maxCPS)));

	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR) {
			if (async) {
				Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
					Player p = event.getPlayer();
					User u = UserManager.getUser(p.getName());
					u.setCps(u.getCps() + 1);
					if (u.getCps() > maxCPS) {
						event.setCancelled(true);
						Bukkit.getScheduler().runTask(Main.getPlugin(), () -> p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1)));
						if (!u.getAlert()) {
							u.setAlert(true);
							p.sendTitle(kickMessageUp, kickMessageDown, 5, 40, 5);
						}
						if (u.getCps() > maxCPSkick) {
							Bukkit.getScheduler().runTask(Main.getPlugin(), () -> p.kickPlayer(kickReason));
						}
					}
				});
			} else {
                Player p = event.getPlayer();
                User u = UserManager.getUser(p.getName());
                u.setCps(u.getCps() + 1);
                if (u.getCps() > maxCPS) {
                    event.setCancelled(true);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
                    if (!u.getAlert()) {
                        u.setAlert(true);
                        p.sendTitle(kickMessageUp, kickMessageDown, 5, 40, 5);
                    }
                    if (u.getCps() > maxCPSkick) {
                        p.kickPlayer(kickReason);
                    }

					//event.getPlayer().kickPlayer(ChatFix.fixColor(Main.getPlugin().getConfig().getString(("kick-reason")).replaceAll("<NewLine>", "\n").replaceAll("<CPSLimit>", Main.getPlugin().getConfig().getString("max_cps"))));
				}
			}
		} // TO DO: test szybkosci
	}
}
