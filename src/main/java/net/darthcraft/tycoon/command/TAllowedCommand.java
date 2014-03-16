package net.darthcraft.tycoon.command;

import net.darthcraft.tycoon.PermissionLevel;
import net.darthcraft.tycoon.Tycoon;

public class TAllowedCommand extends TCommandBase {

    protected TAllowedCommand(Tycoon plugin) {
        super(plugin, PermissionLevel.DEFAULT, CmdSource.PLAYER);
    }

    @Override
    public void execute(CommandContext context) {

    }
}
