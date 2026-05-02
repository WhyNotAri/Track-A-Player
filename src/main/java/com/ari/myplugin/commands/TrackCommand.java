package com.ari.myplugin.commands;

import com.ari.myplugin.managers.TrackManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class TrackCommand implements CommandExecutor {

    private final TrackManager trackManager;
    private final JavaPlugin plugin;

    public TrackCommand(TrackManager trackManager, JavaPlugin plugin) {
        this.trackManager = trackManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can execute this command");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Component.text("Usage: /track <player>").color(NamedTextColor.RED));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null || !target.isOnline()) {
            sender.sendMessage(Component.text("Player not found...").color(NamedTextColor.RED));
            return true;
        }

        if (target.equals(player)) {
            sender.sendMessage(Component.text("You can't track yourself").color(NamedTextColor.RED));
            return true;
        }

        ItemStack compass = new ItemStack(Material.COMPASS, 1);

        // register the compass
        ItemMeta meta = compass.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, "tracking_compass");
        meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        compass.setItemMeta(meta);
        
        player.getInventory().addItem(compass);

        // set the compass to point the target instance
        trackManager.setTracker(player, target);
        player.setCompassTarget(target.getLocation());

        player.sendMessage(Component.text("Tracking " + target.getName()).color(NamedTextColor.WHITE));
        return true;
    }
}