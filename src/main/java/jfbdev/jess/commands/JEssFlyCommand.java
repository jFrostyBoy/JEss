package jfbdev.jess.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class JEssFlyCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public JEssFlyCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        String prefix = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("prefix", ""));

        boolean flySelfEnabled = plugin.getConfig().getBoolean("fly.enable", true);
        boolean flyOtherEnabled = plugin.getConfig().getBoolean("fly-other.enable", true);

        String soundFly = plugin.getConfig().getString("fly.fly-sound.sound", "entity.player.levelup");
        float volumeFly = (float) plugin.getConfig().getDouble("fly.fly-sound.volume", 1.0);
        float pitchFly = (float) plugin.getConfig().getDouble("fly.fly-sound.pitch", 2.0);
        boolean soundFlyEnable = plugin.getConfig().getBoolean("fly.fly-sound.enable", true);

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("main-messages.only-players", "")));
            return true;
        }

        if (!player.hasPermission("jess.fly")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("main-messages.no-permission", "")));
            return true;
        }

        if (!flySelfEnabled) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("main-messages.disabled-command", "")));
            return true;
        }

        if (args.length == 0) {
            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        prefix + plugin.getConfig().getString("fly.disabled")));
            } else {
                player.setAllowFlight(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        prefix + plugin.getConfig().getString("fly.enabled")));
            }

            if (soundFlyEnable) {
                player.playSound(player.getLocation(), soundFly, volumeFly, pitchFly);
            }
            return true;
        }

        if (!flyOtherEnabled) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("main-messages.disabled-command", "")));
            return true;
        }

        if (!player.hasPermission("jess.fly.other")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("main-messages.no-permission", "")));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("main-messages.not-found", "")));
            return true;
        }

        if (target.getAllowFlight()) {
            target.setAllowFlight(false);
            target.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            prefix + plugin.getConfig().getString("fly.disabled-other", ""))
                    .replace("%player%", player.getName()));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("fly.disabled-other-success", ""))
                    .replace("%player%", target.getName()));

        } else {
            target.setAllowFlight(true);
            target.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            prefix + plugin.getConfig().getString("fly.enabled-other", ""))
                    .replace("%player%", player.getName()));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("fly.enabled-other-success", ""))
                    .replace("%player%", target.getName()));
        }

        if (soundFlyEnable) {
            target.playSound(target.getLocation(), soundFly, volumeFly, pitchFly);
            player.playSound(player.getLocation(), soundFly, volumeFly, pitchFly);
        }
        return true;
    }
}