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

public class JEssHealCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final HashMap<UUID, Long> cooldownHeal;

    public JEssHealCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.cooldownHeal = new HashMap<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        String prefix = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("prefix", ""));

        boolean healSelfEnable = plugin.getConfig().getBoolean("heal.enable", true);
        boolean healOtherEnable = plugin.getConfig().getBoolean("heal-other.enable", true);

        String soundHeal = plugin.getConfig().getString("heal.heal-sound.sound", "entity.player.levelup");
        float volumeHeal = (float) plugin.getConfig().getDouble("heal.heal-sound.volume", 1.0);
        float pitchHeal = (float) plugin.getConfig().getDouble("heal.heal-sound.pitch", 2.0);
        boolean soundHealEnable = plugin.getConfig().getBoolean("heal.heal-sound.enable", true);

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("main-messages.only-players", "")));
            return true;
        }

        if (!player.hasPermission("jess.heal")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("main-messages.no-permission", "")));
            return true;
        }

        String group = getPrimaryGroup(player);
        int cooldownSeconds = Objects.requireNonNull(plugin.getConfig()
                        .getConfigurationSection("heal.cooldowns"))
                .getInt(group, plugin.getConfig().getInt("heal.cooldowns.default", 300));

        long now = System.currentTimeMillis();
        Long lastUsage = cooldownHeal.get(player.getUniqueId());

        if (lastUsage != null && cooldownSeconds > 0) {
            long remaining = cooldownSeconds - (now - lastUsage) / 1000;
            if (remaining > 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                prefix + plugin.getConfig().getString("heal.cooldown", ""))
                        .replace("%sec%", String.valueOf(remaining)));
                return true;
            }
        }

        if (args.length == 0 ) {
            if (!healSelfEnable) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        prefix + plugin.getConfig().getString("main-messages.disabled-command", "")));
                return true;
            }

            player.setHealth(20.0);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("heal.success", "")));

            cooldownHeal.put(player.getUniqueId(), now);

            if (soundHealEnable) {
                player.playSound(player.getLocation(), soundHeal, volumeHeal, pitchHeal);
            }
            return true;
        }

        if (!healOtherEnable) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("main-messages.disabled-command", "")));
            return true;
        }

        if (!player.hasPermission("jess.heal.other")) {
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
            player.setHealth(20.0);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("heal.success", "")));
            player.playSound(player.getLocation(), soundHeal, volumeHeal, pitchHeal);

            cooldownHeal.put(player.getUniqueId(), now);
            return true;
        }

        target.setHealth(20.0);
        target.sendMessage(ChatColor.translateAlternateColorCodes('&',
                prefix + plugin.getConfig().getString("heal.success-healed", ""))
                .replace("%player%", player.getName()));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                prefix + plugin.getConfig().getString("heal.success-other", ""))
                .replace("%player%", target.getName()));

        cooldownHeal.put(player.getUniqueId(), now);

        if (soundHealEnable) {
            player.playSound(player.getLocation(), soundHeal, volumeHeal, pitchHeal);
            target.playSound(target.getLocation(), soundHeal, volumeHeal, pitchHeal);
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