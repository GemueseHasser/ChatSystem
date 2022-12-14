package de.gemuesehasser.command;

import de.gemuesehasser.ChatSystem;
import de.gemuesehasser.handler.CommandHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Mit diesem Befehl kann ein Spieler eine dick gedruckte Nachricht in den globalen Chat senden.
 */
@NotNull
public final class Bold implements CommandExecutor {

    //<editor-fold desc="implementation">
    @Override
    public boolean onCommand(
        @NotNull final CommandSender sender,
        @NotNull final Command command,
        @NotNull final String label,
        @NotNull final String[] args
    ) {
        final CommandHandler boldHandler = new CommandHandler(
            sender,
            0,
            "Bitte benutze /bold <message>",
            args
        );

        // check if player is justified
        if (boldHandler.isNotJustified()) return true;

        final Player player = boldHandler.getPlayer();

        // check if player has the necessary permissions
        assert player != null;
        if (!player.hasPermission("chat.bold")) {
            player.sendMessage(ChatSystem.getPrefix() + " Du kannst leider keine dick gedruckten Nachrichten senden.");
            return true;
        }

        // build message
        final StringBuilder messageBuilder = new StringBuilder();

        for (final String arg : args) {
            messageBuilder.append(arg).append(" ");
        }

        // send bold message
        player.chat(ChatColor.BOLD + messageBuilder.toString());
        return true;
    }
    //</editor-fold>

}
