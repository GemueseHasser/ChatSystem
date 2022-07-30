package de.gemuesehasser.listener;

import de.gemuesehasser.ChatSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Mithilfe dieses {@link ChatListener} werden alle Aktionen geregelt, die dieses {@link ChatSystem} übernehmen soll und
 * die mit dem globalen Chat zu tun haben.
 */
@NotNull
public final class ChatListener implements Listener {

    //<editor-fold desc="CONSTANTS">
    /** Eine Liste, die alle Farbcodes beinhaltet, die ein Spieler standardmäßig nutzen kann. */
    @NotNull
    private static final List<String> DEFAULT_CHAT_COLORS = new ArrayList<>();
    /** Alle Farbcodes, die ein Spieler nutzen kann, wenn er die Permission 'chat.color' besitzt. */
    @NotNull
    private static final List<String> ALLOWED_CHAT_COLORS = new ArrayList<>();
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt einen neuen {@link ChatListener}. Mithilfe dieses {@link ChatListener} werden alle Aktionen geregelt, die
     * dieses {@link ChatSystem} übernehmen soll und die mit dem globalen Chat zu tun haben.
     */
    public ChatListener() {
        DEFAULT_CHAT_COLORS.addAll(ChatSystem.getInstance().getConfig().getStringList("defaultColors"));
        ALLOWED_CHAT_COLORS.addAll(ChatSystem.getInstance().getConfig().getStringList("allowedColors"));
    }
    //</editor-fold>


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

        // translate default color codes
        message = getWithTranslatedColors(message, DEFAULT_CHAT_COLORS);

        // translate allowed color codes
        if (e.getPlayer().hasPermission("chat.color")) {
            message = getWithTranslatedColors(message, ALLOWED_CHAT_COLORS);
        }

        // set new message
        e.setMessage(message);
    }
    //</editor-fold>

    /**
     * Übersetzt alle Farbcodes in einem bestimmten Text, die in einer Liste aufgeführt sind. Alle anderen Farbcodes
     * werden nicht beachtet. Der übersetzte Text wird dann wieder zurückgegeben.
     *
     * @param message Der Text, welcher auf Farbcodes untersucht werden soll.
     * @param colors  Alle Farbcodes, die in dem Text übersetzt werden sollen.
     *
     * @return Der übersetzte Text.
     */
    private String getWithTranslatedColors(@NotNull final String message, @NotNull final List<String> colors) {
        String tempMessage = message;

        for (@NotNull final String color : colors) {
            while (tempMessage.contains(color)) {
                String[] split = tempMessage.split("(?=" + color + ")", 2);

                if (split.length == 1) {
                    tempMessage = "§" + split[0].substring(1);
                    continue;
                }

                tempMessage = split[0] + "§" + split[1].substring(1);
            }
        }

        return tempMessage;
    }

}
