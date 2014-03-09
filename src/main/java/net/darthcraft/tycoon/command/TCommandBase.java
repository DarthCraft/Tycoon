package net.darthcraft.tycoon.command;

import net.darthcraft.tycoon.PermissionLevel;
import net.darthcraft.tycoon.Tycoon;

public abstract class TCommandBase {

    protected final Tycoon plugin;
    private final PermissionLevel level;
    private final CmdSource source;

    protected TCommandBase(Tycoon plugin, PermissionLevel level, CmdSource source) {
        this.plugin = plugin;
        this.level = level;
        this.source = source;
    }

    public abstract void execute(CommandContext context);

    public PermissionLevel getPermission() {
        return level;
    }

    public CmdSource getSource() {
        return source;
    }
}
