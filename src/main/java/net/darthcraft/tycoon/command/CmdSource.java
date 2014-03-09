package net.darthcraft.tycoon.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum CmdSource {

    ALL("everyone"),
    PLAYER("players"),
    CONSOLE("the console");

    private final String chatText;

    CmdSource(String chatText) {
        this.chatText = chatText;
    }

    public boolean applies(CommandSender sender) {
        if (this == ALL) {
            return true;
        } else if (sender instanceof Player) {
            return this == PLAYER;
        } else {
            return this == CONSOLE;
        }
    }

    public String chatText() {
        return chatText;
    }
}
