package jfbdev.jess;

import jfbdev.jess.commands.JEssFeedCommand;
import jfbdev.jess.commands.JEssHealCommand;
import jfbdev.jess.commands.JEssReloadCommand;
import jfbdev.jess.listeners.JoinQuitDeathListener;
import jfbdev.jess.listeners.ServerMotdListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class JEss extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new JoinQuitDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new ServerMotdListener(this), this);

        Objects.requireNonNull(getCommand("jessreload")).setExecutor(new JEssReloadCommand(this));
        Objects.requireNonNull(getCommand("heal")).setExecutor(new JEssHealCommand(this));
        Objects.requireNonNull(getCommand("feed")).setExecutor(new JEssFeedCommand(this));

        Bukkit.getScheduler().runTaskLater(this, () -> {
            Bukkit.getConsoleSender().sendMessage("");
            Bukkit.getConsoleSender().sendMessage("§b    ░░░░░██╗███████╗░██████╗░██████╗");
            Bukkit.getConsoleSender().sendMessage("§b    ░░░░░██║██╔════╝██╔════╝██╔════╝§6    Manager features at your server");
            Bukkit.getConsoleSender().sendMessage("§b    ░░░░░██║█████╗░░╚█████╗░╚█████╗░§f    Plugin version: §bJESS-v1.1");
            Bukkit.getConsoleSender().sendMessage("§b    ██╗░░██║██╔══╝░░░╚═══██╗░╚═══██╗§f    Author: §bjFrostyBoy");
            Bukkit.getConsoleSender().sendMessage("§b    ╚█████╔╝███████╗██████╔╝██████╔╝§f    Website: §bhttps://jfrostyboy.42web.io");
            Bukkit.getConsoleSender().sendMessage("§b    ░╚════╝░╚══════╝╚═════╝░╚═════╝░");
            Bukkit.getConsoleSender().sendMessage("");
            Bukkit.getConsoleSender().sendMessage("§8[§bJESS§8] §fThe plugin is §aenabled §fand ready to §awork!");
        }, 20L);
    }
}
