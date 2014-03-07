package net.darthcraft.tycoon.command;

import net.darthcraft.tycoon.PermissionLevel;

public @interface CmdPermission {

    PermissionLevel permission() default PermissionLevel.DEFAULT;

    CmdSource source() default CmdSource.ALL;
}
