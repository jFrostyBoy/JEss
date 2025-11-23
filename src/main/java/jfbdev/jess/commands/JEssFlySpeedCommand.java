package jfbdev.jess.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class JEssFlySpeedCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public JEssFlySpeedCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        String prefix = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("prefix", ""));

        boolean flySpeedEnable = plugin.getConfig().getBoolean("flyspeed.enable", true);
        boolean flySpeedOtherEnable = plugin.getConfig().getBoolean("flyspeed-other.enable", true);

        String soundFlySpeed = plugin.getConfig().getString("flyspeed.flyspeed-sound.sound", "entity.player.levelup");
        float volumeFlySpeed = (float) plugin.getConfig().getDouble("flyspeed.flyspeed-sound.volume", 1.0);
        float pitchFlySpeed = (float) plugin.getConfig().getDouble("flyspeed.flyspeed-sound.pitch", 2.0);
        boolean soundFlyEnable = plugin.getConfig().getBoolean("flyspeed.flyspeed-sound.enable", true);

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("main-messages.only-players", "")));
            return true;
        }

        if (!player.hasPermission("jess.flyspeed")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("main-messages.no-permission", "")));
            return true;
        }

        if (args.length < 1 || args.length > 2) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("flyspeed.flyspeed-usage", "")));
            return true;
        }

        Player target = player;
        String speedArg = args[args.length - 1];

        if (args.length == 2) {
            if (!flySpeedOtherEnable) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        prefix + plugin.getConfig().getString("main-messages.disabled-command", "")));
                return true;
            }
            if (!player.hasPermission("jess.flyspeed.other")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        prefix + plugin.getConfig().getString("main-messages.no-permission", "")));
                return true;
            }
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        prefix + plugin.getConfig().getString("main-messages.not-found", "")));
                return true;
            }
        } else if (!flySpeedEnable) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("main-messages.disabled-command", "")));
            return true;
        }

        int speed;
        try {
            speed = Integer.parseInt(speedArg);
            if (speed < 1 || speed > 10) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    prefix + plugin.getConfig().getString("flyspeed.no-value", "")));
            return true;
        }

        target.setFlySpeed(speed / 10.0f);

        String messageKey = (target == player) ? "flyspeed.success-self" : "flyspeed.success-other";
        String message = plugin.getConfig().getString(messageKey, "")
                .replace("%player%", target.getName())
                .replace("%speed%", String.valueOf(speed));

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));

        if (target != player) {
            String targetMsg = plugin.getConfig().getString("flyspeed.success-for-you", "")
                    .replace("%speed%", String.valueOf(speed))
                    .replace("%executor%", player.getName());
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + targetMsg));
        }

        if (soundFlyEnable) {
            target.playSound(target.getLocation(), soundFlySpeed, volumeFlySpeed, pitchFlySpeed);
            player.playSound(player.getLocation(), soundFlySpeed, volumeFlySpeed, pitchFlySpeed);
        }

        return true;
    }
}