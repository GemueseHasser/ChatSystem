package de.gemuesehasser.task;

import de.gemuesehasser.ChatSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Der {@link NewsTask} stellt eine sich konstant wiederholende Prozedur dar, in der immer und immer wieder die in der
 * Config hinterlegten Neuigkeiten angezeigt werden.
 */
@NotNull
public final class NewsTask extends BukkitRunnable {

    //<editor-fold desc="implementation">
    @Override
    public void run() {
        // get config
        final FileConfiguration config = ChatSystem.getInstance().getConfig();

        // send news
        for (@NotNull final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(
                ChatColor.translateAlternateColorCodes(
                    '&', Objects.requireNonNull(config.getString("news"))
                )
            );
        }
    }
    //</editor-fold>

}
