package de.gemuesehasser.handler;

import de.gemuesehasser.ChatSystem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public final class CommandHandler {

    private Player player;
    private boolean justified;


    public CommandHandler(
        @NotNull final CommandSender sender,
        @Range(from = 0, to = Integer.MAX_VALUE) final int length,
        @NotNull final String usage,
        @NotNull final String[] args
    ) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Du musst ein Spieler sein, um diesen Befehl auszuführen.");
            this.justified = false;
            return;
        }

        this.player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ChatSystem.getPrefix() + " " + usage);
            this.justified = false;
        }

        this.justified = true;
    }


    public Player getPlayer() {
        return this.player;
    }

    public boolean isJustified() {
        return this.justified;
    }

}
