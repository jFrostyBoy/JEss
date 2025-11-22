package jfbdev.jess;

import jfbdev.jess.commands.JEssReloadCommand;
import jfbdev.jess.listeners.JoinQuitDeathListener;
import jfbdev.jess.listeners.ServerMotdListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class JEss extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new JoinQuitDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new ServerMotdListener(this), this);

        Objects.requireNonNull(getCommand("jessreload")).setExecutor(new JEssReloadCommand(this));

    }
}
