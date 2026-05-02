package com.ari.myplugin.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameRules;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldLoadEvent;

public class LocatorBarListener implements Listener {

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        event.getWorld().setGameRule(GameRules.LOCATOR_BAR, false);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Boolean locatorBarEnabled = player.getWorld().getGameRuleValue(GameRules.LOCATOR_BAR);

        if (Boolean.FALSE.equals(locatorBarEnabled)) {
            player.sendMessage(Component.text("Locator Bar has been disabled").color(NamedTextColor.RED));
        }
    }
}
