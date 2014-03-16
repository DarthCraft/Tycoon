package net.darthcraft.tycoon.command;

import net.darthcraft.tycoon.PermissionLevel;
import net.darthcraft.tycoon.Tycoon;
import net.darthcraft.tycoon.player.PlayerInfo;
import net.darthcraft.tycoon.plot.PlotInformation;

public class TDeniedCommand extends TCommandBase {

    protected TDeniedCommand(Tycoon plugin) {
        super(plugin, PermissionLevel.DEFAULT, CmdSource.PLAYER);
    }

    @Override
    public void execute(CommandContext context) {
        PlayerInfo info = plugin.getPlayerManager().getPlayerInfo(context.getPlayer());
        info.update();
        PlotInformation plotInfo = info.getCachedPlotInfo();
        if (plotInfo == null) {
            context.sendError("You can only use this command in Tycoon");
        } else if (plotInfo.isGlobalOwned()) {
            context.sendError("You do not own this plot");
        } else if (!plotInfo.hasOwner()) {
            context.sendError("This plot has no owner");
        } else if (!plotInfo.getOwner().equalsIgnoreCase(context.getPlayer().getName())) {
            context.sendError("You are not the owner of this plot");
        } else if (context.getArg(0).equalsIgnoreCase("add")) {
            plotInfo.addDenied(context.getArg(1));
            plugin.getPlotManager().savePlotInformation(plotInfo);
        } else if (context.getArg(0).equalsIgnoreCase("remove")) {
            plotInfo.removeDenied(context.getArg(1));
            plugin.getPlotManager().savePlotInformation(plotInfo);
        }
    }
}
