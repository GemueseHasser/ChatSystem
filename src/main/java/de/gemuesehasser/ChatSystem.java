package de.gemuesehasser;

import de.gemuesehasser.task.NewsTask;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatSystem extends JavaPlugin {

    private static ChatSystem instance;


    @Override
    public void onEnable() {
        super.onEnable();

        // definde plugin instance
        instance = this;

        // load config
        getConfig().options().copyDefaults(true);
        saveConfig();

        // initialise news task
        new NewsTask().runTaskTimerAsynchronously(
            this,
            500,
            getConfig().getInt("newsPeriodMinutes") * 1200L
        );
        getLogger().info("Der News-Task wurde erfolgreich geladen.");

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
}
