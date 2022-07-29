package de.gemuesehasser.command;

import de.gemuesehasser.ChatSystem;
import de.gemuesehasser.handler.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class Message implements CommandExecutor {

    @Override
    public boolean onCommand(
        @NotNull final CommandSender sender,
        @NotNull final Command command,
        @NotNull final String label,
        @NotNull final String[] args
    ) {
        final CommandHandler messageHandler = new CommandHandler(
            sender,
            1,
            "Bitte nutze /msg <player> <message>",
            args
        );

        if (!messageHandler.isJustified()) return true;

        final Player player = messageHandler.getPlayer();
        final Player target = Bukkit.getPlayer(args[0]);

        assert target != null;
        if (!target.isOnline()) {
            player.sendMessage(ChatSystem.getPrefix() + " Der Spieler ist nicht online!");
            return true;
        }

        if (target.equals(player)) {
            player.sendMessage(ChatSystem.getPrefix() + " Dir selber kannst du keine Nachricht schicken!");
            return true;
        }

        final StringBuilder messageBuilder = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            messageBuilder.append(args[i]).append(" ");
        }

        player.sendMessage(player.getDisplayName() + " > " + target.getDisplayName() + " >> " + messageBuilder);
        target.sendMessage(player.getDisplayName() + " > " + target.getDisplayName() + " >> " + messageBuilder);
        return true;
    }
}
