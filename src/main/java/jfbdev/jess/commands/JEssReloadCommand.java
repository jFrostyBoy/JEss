package jfbdev.jess.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class JEssReloadCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public JEssReloadCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        String prefix = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("prefix", ""));

        if (sender instanceof Player player) {
            if (!player.hasPermission("jess.reload")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        prefix + plugin.getConfig().getString("main-messages.no-permission", "")));
                return true;
            }
            if (args.length != 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        prefix + plugin.getConfig().getString("main-messages.reload-usage", "")));
                return true;
            }
        }

        plugin.reloadConfig();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                prefix + plugin.getConfig().getString("main-messages.reloaded", "")));
        return true;
    }
}