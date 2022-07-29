package de.gemuesehasser;

import de.gemuesehasser.command.Message;
import de.gemuesehasser.listener.ChatListener;
import de.gemuesehasser.task.NewsTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ChatSystem extends JavaPlugin {

    private static ChatSystem instance;
    private static String prefix;


    @Override
    public void onEnable() {
        super.onEnable();

        // definde plugin instance
        instance = this;

        // load config
        getConfig().options().copyDefaults(true);
        saveConfig();

        // load prefix
        prefix = ChatColor.translateAlternateColorCodes(
            '&',
            Objects.requireNonNull(getConfig().getString("pluginPrefix"))
        );

        // initialize news task
        new NewsTask().runTaskTimerAsynchronously(
            this,
            500,
            getConfig().getInt("newsPeriodMinutes") * 1200L
        );
        getLogger().info("Der News-Task wurde erfolgreich geladen.");

        // initialize listener
        final PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ChatListener(), this);

        // initialize commands
        Objects.requireNonNull(getCommand("message")).setExecutor(new Message());

        getLogger().info("Das Plugin wurde erfolgreich aktiviert!");
    }

    @Override
    public void onDisable() {
        super.onDisable();

        getLogger().info("Das Plugin wurde deaktiviert!");
    }

    public static ChatSystem getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return prefix;
    }
}
