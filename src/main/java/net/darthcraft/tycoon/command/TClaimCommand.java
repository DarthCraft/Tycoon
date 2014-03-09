package net.darthcraft.tycoon.command;

import net.darthcraft.tycoon.PermissionLevel;
import net.darthcraft.tycoon.Tycoon;
import net.darthcraft.tycoon.player.PlayerInfo;
import net.darthcraft.tycoon.plot.PlotInformation;
import net.darthcraft.tycoon.plot.PlotManager;
import org.bukkit.entity.Player;

public class TClaimCommand extends TCommandBase {

    public TClaimCommand(Tycoon plugin) {
        super(plugin, PermissionLevel.DEFAULT, CmdSource.PLAYER);
    }

    @Override
    public void execute(CommandContext context) {
        Player player = context.getPlayer();
        if (context.getArgsLength() == 0) {
            PlayerInfo info = plugin.getPlayerManager().getPlayerInfo(player);
            info.update();
            PlotInformation plot = info.getCachedPlotInfo();
            if (plot == null) {
                context.sendError("You can only use this in the Tycoon world");
            } else if (!info.isInPlot()) {
                context.sendError("Please stand in a plot to claim it");
            } else if (plot.hasOwner() || plot.isGlobalOwned()) {
                context.sendError("You can't claim a claimed plot");
            } else {
                plugin.getPlotManager().claimPlot(player.getName(), plot);
            }
        } else if (context.getArg(0).equalsIgnoreCase(PlotManager.GLOBAL_NAME)) {

        }
    }
}
