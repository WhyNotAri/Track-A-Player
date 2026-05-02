package com.ari.myplugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TrackManager {

    private final JavaPlugin plugin;
    private final Map<UUID, UUID> trackers = new HashMap<>();

    public TrackManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void setTracker(@NonNull Player tracker, @NonNull Player target) {
        trackers.put(tracker.getUniqueId(), target.getUniqueId());
    }

    public Player getTarget(@NonNull Player tracker) {
        UUID targetId = trackers.get(tracker.getUniqueId());
        if (targetId == null) {
            return null;
        }
        return Bukkit.getPlayer(targetId);
    }

    public boolean isTracking(@NonNull Player tracker) {
        return trackers.containsKey(tracker.getUniqueId());
    }

    public void removeTracker(@NonNull Player tracker) {
        trackers.remove(tracker.getUniqueId());
    }

    public void updateCompassTarget(@NonNull Player tracker) {
        Player target = getTarget(tracker);
        if (target != null && target.isOnline()) {
            tracker.setCompassTarget(target.getLocation());
            tracker.sendMessage("Compass updated to " + target.getName() + "'s location");
        } else {
            tracker.sendMessage("Target player is no longer online");
            removeTracker(tracker);
        }
    }
}
