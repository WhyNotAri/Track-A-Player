package com.ari.myplugin.listener;

import com.ari.myplugin.managers.TrackManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class TrackerListener implements Listener {
    private final TrackManager trackManager;
    private final JavaPlugin plugin;

    public TrackerListener(TrackManager trackManager, JavaPlugin plugin) {
        this.trackManager = trackManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player disconnected = event.getPlayer();

        for(Player online : disconnected.getServer().getOnlinePlayers()) {
            Player target = trackManager.getTarget(online);

            if(target != null && target.equals(disconnected)) {
                online.sendMessage(disconnected.getName() + " left the game");
            }
        }
    }

    @EventHandler
    public void onCompassClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || item.getType() != Material.COMPASS) {
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (trackManager.isTracking(player)) {
                trackManager.updateCompassTarget(player);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();

        if (trackManager.isTracking(player)) {
            NamespacedKey key = new NamespacedKey(plugin, "tracking_compass");
            event.getDrops().removeIf(item -> {
                if (item.getType() != Material.COMPASS) {
                    return false;
                }

                ItemMeta meta = item.getItemMeta();

                return meta != null && meta.getPersistentDataContainer().has(key, PersistentDataType.BOOLEAN);
            });
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        
        if (trackManager.isTracking(player)) {

            ItemStack compass = new ItemStack(Material.COMPASS, 1);
            ItemMeta meta = compass.getItemMeta();
            NamespacedKey key = new NamespacedKey(plugin, "tracking_compass");
            meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
            compass.setItemMeta(meta);
            
            player.getInventory().addItem(compass);
            trackManager.updateCompassTarget(player);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {

        ItemStack item = event.getItemDrop().getItemStack();

        if (item.getType() == Material.COMPASS) {
            ItemMeta meta = item.getItemMeta();

            NamespacedKey key = new NamespacedKey(plugin, "tracking_compass");

            if (meta != null && meta.getPersistentDataContainer().has(key, PersistentDataType.BOOLEAN)) {
                event.setCancelled(true);
            }
        }
    }
}
