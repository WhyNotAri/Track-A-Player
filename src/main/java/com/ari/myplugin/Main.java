package com.ari.myplugin;

import com.ari.myplugin.commands.TrackCommand;
import com.ari.myplugin.listener.LocatorBarListener;
import com.ari.myplugin.listener.TrackerListener;
import com.ari.myplugin.managers.TrackManager;
import org.bukkit.Bukkit;
import org.bukkit.GameRules;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("'Track-A-Player' plugin activated");

        TrackManager trackManager = new TrackManager(this);

        if (getCommand("track") != null) {
            Objects.requireNonNull(getCommand("track")).setExecutor(new TrackCommand(trackManager, this));
        }else {
            getLogger().info("track command not found");
        }

        for(World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRules.LOCATOR_BAR, false);
        }

        // Register Events
        getServer().getPluginManager().registerEvents(new TrackerListener(trackManager, this), this);
        getServer().getPluginManager().registerEvents(new LocatorBarListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("'Track-A-Player' plugin deactivated");
    }
}