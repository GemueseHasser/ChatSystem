package de.gemuesehasser.task;

import de.gemuesehasser.ChatSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class NewsTask extends BukkitRunnable {

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
}
