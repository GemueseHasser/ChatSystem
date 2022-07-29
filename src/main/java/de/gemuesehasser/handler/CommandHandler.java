package de.gemuesehasser.handler;

import de.gemuesehasser.ChatSystem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

/**
 * Ein {@link CommandHandler} wird für jeden Befehl instanziiert. Mit diesem {@link CommandHandler} werden grundlegende
 * Dinge geprüft.
 */
@NotNull
public final class CommandHandler {

    //<editor-fold desc="LOCAL FIELDS">
    /** Der Spieler, der diesen Befehl ausführt. */
    @Nullable
    private final Player player;
    /** Der Zustand, ob der Spieler berechtigt ist diesen Befehl weiterhin auszuführen. */
    private final boolean justified;
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt eine neue und vollständig unabhängige Instanz eines {@link CommandHandler}. Ein {@link CommandHandler}
     * wird für jeden Befehl instanziiert. Mit diesem {@link CommandHandler} werden grundlegende Dinge geprüft.
     *
     * @param sender Der Sender dieses Befehls.
     * @param length Die mindestens benötigte Länge der Argumente.
     * @param usage  Die richtige Art der Benutzung.
     * @param args   Die Argumente, die der Spieler zusätzlich zu dem Befehl eingibt.
     */
    public CommandHandler(
        @NotNull final CommandSender sender,
        @Range(from = 0, to = Integer.MAX_VALUE) final int length,
        @NotNull final String usage,
        @NotNull final String[] args
    ) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Du musst ein Spieler sein, um diesen Befehl auszuführen.");
            this.player = null;
            this.justified = false;
            return;
        }

        this.player = (Player) sender;

        if (args.length < length) {
            player.sendMessage(ChatSystem.getPrefix() + " " + usage);
            this.justified = false;
            return;
        }

        this.justified = true;
    }
    //</editor-fold>


    /**
     * Gibt den Spieler, der diesen Befehl ausführt zurück.
     *
     * @return Der Spieler, der diesen Befehl ausführt.
     */
    @Nullable
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Gibt zurück, ob der Spieler nicht berechtigt ist diesen Befehl weiterhin auszuführen.
     *
     * @return Wenn der Spieler nicht dazu berechtigt ist diesen Befehl weiterhin auszuführen {@code true}, ansonsten
     *     {@code false}.
     */
    public boolean isNotJustified() {
        return !this.justified;
    }

}
