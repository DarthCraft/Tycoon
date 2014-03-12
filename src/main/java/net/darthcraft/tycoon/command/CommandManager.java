package net.darthcraft.tycoon.command;

import net.darthcraft.tycoon.Tycoon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CommandManager implements CommandExecutor {

    private final Tycoon plugin;
    private final Map<String, TCommandBase> commands;
    private final TCommandBase helpCommand;

    public CommandManager(Tycoon plugin) {
        this.plugin = plugin;
        this.commands = new HashMap<String, TCommandBase>();
        this.helpCommand = new THelpCommand(plugin);
        registerCommands();
    }

    private void registerCommands() {
        commands.put("help", helpCommand);
        commands.put("claim", new TClaimCommand(plugin));
        commands.put("info", new TInfoCommand(plugin));
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandContext context = new CommandContext(sender, command, label, args);
        if (args.length == 0) {
            helpCommand.execute(context);
        } else {
            String cmd = args[0].toLowerCase();
            TCommandBase tCMD = commands.get(cmd);
            if (tCMD == null) {
                context.sendError("Unknown Command '%s'", args[0]);
            } else if (tCMD.getSource() != null && !tCMD.getSource().applies(sender)) {
                context.sendError("This command only applies to %s", tCMD.getSource().chatText());
            } else if (tCMD.getPermission() != null && !tCMD.getPermission().hasPermission(sender)) {
                context.sendError("You don't have permission for this command");
            } else {
                context.cutArgs(1);
                tCMD.execute(context);
            }
        }
        return true;
    }
}
