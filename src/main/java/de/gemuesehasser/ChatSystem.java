package de.gemuesehasser;

import de.gemuesehasser.command.Bold;
import de.gemuesehasser.command.Message;
import de.gemuesehasser.listener.ChatListener;
import de.gemuesehasser.task.NewsTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Die Haupt- und Main-Klasse dieses Chat-Systems. In dieser Klasse wird das gesamte Chat-System initialisiert.
 */
@NotNull
public final class ChatSystem extends JavaPlugin {

    //<editor-fold desc="STATIC FIELDS">
    /** Die Instanz, womit man auf dieses {@link ChatSystem} zugreifen kann. */
    private static ChatSystem instance;
    /** Der Prefix dieses {@link ChatSystem}. */
    private static String prefix;
    //</editor-fold>


    //<editor-fold desc="start">
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
        Objects.requireNonNull(getCommand("bold")).setExecutor(new Bold());

        getLogger().info("Das Plugin wurde erfolgreich aktiviert!");
    }
    //</editor-fold>

    //<editor-fold desc="stop">
    @Override
    public void onDisable() {
        super.onDisable();

        getLogger().info("Das Plugin wurde deaktiviert!");
    }
    //</editor-fold>

    /**
     * Gibt die Instanz dieses {@link ChatSystem} zurück.
     *
     * @return Die Instanz dieses {@link ChatSystem}.
     */
    @NotNull
    public static ChatSystem getInstance() {
        return instance;
    }

    /**
     * Gibt den Prefix dieses {@link ChatSystem} zurück.
     *
     * @return Der Prefix dieses {@link ChatSystem}.
     */
    @NotNull
    public static String getPrefix() {
        return prefix;
    }
}
