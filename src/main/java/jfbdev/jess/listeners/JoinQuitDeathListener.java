package jfbdev.jess.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinQuitDeathListener implements Listener {

    private final JavaPlugin plugin;

    public JoinQuitDeathListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Deprecated
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String soundJoin = plugin.getConfig().getString("join.sounds.join.sound", "entity.player.levelup");
        float volumeJoin = (float) plugin.getConfig().getDouble("join.sounds.join.volume", 1.0);
        float pitchJoin = (float) plugin.getConfig().getDouble("join.sounds.join.pitch", 2.0);
        boolean soundJoinEnable = plugin.getConfig().getBoolean("join.sounds.join.enable", true);

        String soundFirstJoin = plugin.getConfig().getString("join.sounds.first-join.sound",
                "block.beacon.activate");
        float volumeFirstJoin = (float) plugin.getConfig().getDouble("join.sounds.first-join.volume", 1.0);
        float pitchFirstJoin = (float) plugin.getConfig().getDouble("join.sounds.first-join.pitch", 1.0);
        boolean soundFirstJoinEnable = plugin.getConfig().getBoolean("join.sounds.first-join.enable", true);

        if (!plugin.getConfig().getBoolean("join.enable", true)) {
            event.setJoinMessage(null);
            return;
        }

        if (player.hasPlayedBefore()) {
            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("join.join-message", ""))
                    .replace("%player%", player.getName()));
            if (soundJoinEnable) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.playSound(online.getLocation(), soundJoin, volumeJoin, pitchJoin);
                }
            }
        } else {
            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("join.first-join-message", ""))
                    .replace("%player%", player.getName()));

            if (soundFirstJoinEnable) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.playSound(online.getLocation(), soundFirstJoin, volumeFirstJoin, pitchFirstJoin);
                }
            }
        }
    }

    @Deprecated
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        String soundQuit = plugin.getConfig().getString("quit.sound.sound", "block.anvil.place");
        float volumeQuit = (float) plugin.getConfig().getDouble("quit.sound.volume", 1.0);
        float pitchQuit = (float) plugin.getConfig().getDouble("quit.sound.pitch", 1.5);
        boolean soundQuitEnable = plugin.getConfig().getBoolean("quit.sound.enable", true);

        if (!plugin.getConfig().getBoolean("quit.enable", true)) {
            event.setQuitMessage(null);
            return;
        }

        event.setQuitMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("quit.message", ""))
                .replace("%player%", player.getName()));

        if (soundQuitEnable) {
            for (Player online : Bukkit.getOnlinePlayers())
                online.playSound(online.getLocation(), soundQuit, volumeQuit, pitchQuit);
        }
    }

    @Deprecated
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        String soundDeath = plugin.getConfig().getString("death.sound.sound", "entity.wither.death");
        float volumeDeath = (float) plugin.getConfig().getDouble("death.sound.volume", 1.0);
        float pitchDeath = (float) plugin.getConfig().getDouble("death.sound.pitch", 1.1);
        boolean soundDeathEnable = plugin.getConfig().getBoolean("death.sound.enable", true);

        if (!plugin.getConfig().getBoolean("death.enable", true)) {
            event.setDeathMessage(null);
            return;
        }

        event.setDeathMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("death.message", ""))
                .replace("%player%", player.getName()));

        if (soundDeathEnable) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.playSound(online.getLocation(), soundDeath, volumeDeath, pitchDeath);
            }
        }
    }
}