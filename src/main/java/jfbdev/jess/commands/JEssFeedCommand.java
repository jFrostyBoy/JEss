package jfbdev.jess.commands;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class JEssFeedCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final HashMap<UUID, Long> cooldownFeed;

    public JEssFeedCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.cooldownFeed = new HashMap<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        String prefix = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("prefix", ""));

        boolean feedSelfEnable = plugin.getConfig().getBoolean("feed.enable", true);
        boolean feedOtherEnable = plugin.getConfig().getBoolean("feed-other.enable", true);

        String soundFeed = plugin.getConfig().getString("feed.feed-sound.sound", "entity.player.levelup");
        float volumeFeed = (float) plugin.getConfig().getDouble("feed.feed-sound.volume", 1.0);
        float pitchFeed = (float) plugin.getConfig().getDouble("feed.feed-sound.pitch", 2.0);
        boolean soundFeedEnable = plugin.getConfig().getBoolean("feed.feed-sound.enable", true);

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("main-messages.only-players", "")));
            return true;
        }

        if (!player.hasPermission("jess.feed")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("main-messages.no-permission", "")));
            return true;
        }

        String group = getPrimaryGroup(player);
        int cooldownSeconds = Objects.requireNonNull(plugin.getConfig()
                        .getConfigurationSection("feed.cooldowns"))
                .getInt(group, plugin.getConfig().getInt("feed.cooldowns.default", 300));

        long now = System.currentTimeMillis();
        Long lastUsage = cooldownFeed.get(player.getUniqueId());

        if (lastUsage != null && cooldownSeconds > 0) {
            long remaining = cooldownSeconds - (now - lastUsage) / 1000;
            if (remaining > 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                prefix + plugin.getConfig().getString("feed.cooldown", ""))
                        .replace("%sec%", String.valueOf(remaining)));
                return true;
            }
        }

        if (args.length == 0 ) {
            if (!feedSelfEnable) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        prefix + plugin.getConfig().getString("main-messages.disabled-command", "")));
                return true;
            }

            player.setFoodLevel(20);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("feed.success", "")));

            cooldownFeed.put(player.getUniqueId(), now);

            if (soundFeedEnable) {
                player.playSound(player.getLocation(), soundFeed, volumeFeed, pitchFeed);
            }
            return true;
        }

        if (!feedOtherEnable) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("main-messages.disabled-command", "")));
            return true;
        }

        if (!player.hasPermission("jess.feed.other")) {
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

        if (target == sender) {
            player.setFoodLevel(20);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("feed.success", "")));
            player.playSound(player.getLocation(), soundFeed, volumeFeed, pitchFeed);

            cooldownFeed.put(player.getUniqueId(), now);
            return true;
        }

        target.setFoodLevel(20);
        target.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        prefix + plugin.getConfig().getString("feed.success-feeded", ""))
                .replace("%player%", player.getName()));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        prefix + plugin.getConfig().getString("feed.success-other", ""))
                .replace("%player%", target.getName()));

        cooldownFeed.put(player.getUniqueId(), now);

        if (soundFeedEnable) {
            player.playSound(player.getLocation(), soundFeed, volumeFeed, pitchFeed);
            target.playSound(target.getLocation(), soundFeed, volumeFeed, pitchFeed);
        }

        return true;
    }

    private String getPrimaryGroup(Player player) {
        try {
            LuckPerms api = LuckPermsProvider.get();
            User user = api.getUserManager().getUser(player.getUniqueId());
            if (user != null) {
                return user.getCachedData().getMetaData().getPrimaryGroup();
            }
        } catch (Throwable ignored) {

        }
        return "default";
    }
}