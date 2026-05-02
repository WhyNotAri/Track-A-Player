package com.ari.myplugin.listener;

import com.ari.myplugin.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Deprecated(forRemoval = true)
public class HelloListener implements Listener {

    private final Main plugin;

    public HelloListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(Component.text("Welcome " + event.getPlayer().getName() + "!")
                .color(NamedTextColor.GREEN));
    }
}