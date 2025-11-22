package jfbdev.jess.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerMotdListener implements Listener {

    private final JavaPlugin plugin;
    public ServerMotdListener(JavaPlugin plugin) { this.plugin = plugin; }

    @Deprecated
    @EventHandler(priority = EventPriority.HIGH)
    public void onServerList(ServerListPingEvent event) {
        if (!plugin.getConfig().getBoolean("motd.enable", true)) {
            return;
        }

        String line1 = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("motd.line-1", ""));
        String line2 = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("motd.line-2", ""));

        String motd = line1 + "\n" + line2;
        motd = motd.replace("%online%", String.valueOf(plugin.getServer().getOnlinePlayers().size()))
                .replace("%max%", String.valueOf(plugin.getServer().getMaxPlayers()));

        event.setMotd(motd);

    }
}