package net.darthcraft.tycoon;

import org.bukkit.command.CommandSender;

public enum PermissionLevel {

    DEFAULT("default"),
    PREMIUM("premium"),
    MODERATOR("moderator"),
    ADMIN("admin");

    private static final String PERM_PREFIX = "tycoon.";

    private final String perm;

    PermissionLevel(String perm) {
        this.perm = PERM_PREFIX + perm;
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(perm);
    }
}
