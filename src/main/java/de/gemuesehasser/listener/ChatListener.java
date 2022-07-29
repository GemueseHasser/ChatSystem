package de.gemuesehasser.listener;

import de.gemuesehasser.ChatSystem;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Mithilfe dieses {@link ChatListener} werden alle Aktionen geregelt, die dieses {@link ChatSystem} übernehmen soll und
 * die mit dem globalen Chat zu tun haben.
 */
@NotNull
public final class ChatListener implements Listener {

    //<editor-fold desc="implementation">
    @EventHandler
    public void onChat(@NotNull final AsyncPlayerChatEvent e) {
        // get current message
        String message = e.getMessage();

        final List<String> blacklist = ChatSystem.getInstance().getConfig().getStringList("blacklist");
        final List<String> replace = ChatSystem.getInstance().getConfig().getStringList("replace");

        // replace bad words
        for (int i = 0; i < blacklist.size(); i++) {
            final String replaceWord = i >= replace.size() ? replace.get(replace.size() - 1) : replace.get(i);

            message = message.replaceAll("(?i)" + blacklist.get(i), replaceWord);
        }

        // translate color codes
        if (e.getPlayer().hasPermission("chat.color")) {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }

        // set new message
        e.setMessage(message);
    }
    //</editor-fold>

}
