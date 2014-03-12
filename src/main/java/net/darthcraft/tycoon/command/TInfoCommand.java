package net.darthcraft.tycoon.command;

import net.darthcraft.tycoon.Tycoon;
import net.darthcraft.tycoon.player.PlayerInfo;
import net.darthcraft.tycoon.plot.PlotInformation;
import net.darthcraft.tycoon.plot.PlotManager;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TInfoCommand extends TCommandBase {

    protected TInfoCommand(Tycoon plugin) {
        super(plugin, null, CmdSource.PLAYER);
    }

    @Override
    public void execute(CommandContext context) {
        Player player = context.getPlayer();
        PlayerInfo info = plugin.getPlayerManager().getPlayerInfo(player);
        info.update();
        PlotInformation plot = info.getCachedPlotInfo();
        if (plot == null) {
            context.sendError("No plot found!");
        } else {
            String owner = "UNOWNED";
            if (plot.hasOwner()) {
                owner = plot.getOwner();
            } else if (plot.isGlobalOwned()) {
                owner = PlotManager.GLOBAL_NAME;
            }
            int x = plot.getCoords().getPlotX();
            int z = plot.getCoords().getPlotZ();
            context.sendMessage("Plot (%d,%d) - Owner: %s", x, z, owner);
            context.sendMessage("In plot: %b", info.isInPlot());
            context.sendMessage("Allowed: %s", Arrays.toString(plot.getAllowed().toArray()));
            context.sendMessage("Denied: %s", Arrays.toString(plot.getDenied().toArray()));
            context.sendMessage("Effects: %s", Arrays.toString(plot.getEffects().toArray()));
        }
    }
}
