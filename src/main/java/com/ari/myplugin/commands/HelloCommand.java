package com.ari.myplugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Deprecated(forRemoval = true)
public class HelloCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        player.sendMessage(
                Component.text("¡Hello, " + player.getName() + "!")
                        .color(NamedTextColor.AQUA)
        );

        return true;
    }
}
