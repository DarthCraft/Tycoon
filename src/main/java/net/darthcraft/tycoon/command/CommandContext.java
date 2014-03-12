package net.darthcraft.tycoon.command;

import net.darthcraft.tycoon.message.TycoonMessages;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandContext {

    private final CommandSender sender;
    private final Command command;
    private final String label;
    private String[] args;

    public CommandContext(CommandSender sender, Command command, String label, String[] args) {
        this.sender = sender;
        this.command = command;
        this.label = label;
        this.args = args;
    }

    public CommandSender getSender() {
        return sender;
    }

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public Player getPlayer() {
        return (Player) sender;
    }

    public Command getCommand() {
        return command;
    }

    public String getLabel() {
        return label;
    }

    public int getArgsLength() {
        return args.length;
    }

    public String getArg(int i) {
        return args[i];
    }

    public String getJoinedArgs(int startIndex, int length, String delim) {
        Object[] sub = ArrayUtils.subarray(args, startIndex, startIndex + length);
        return StringUtils.join(sub, delim);
    }

    public void cutArgs(int indexOfStart) {
        String[] temp = new String[args.length - indexOfStart];
        System.arraycopy(args, indexOfStart, temp, 0, temp.length);
        args = temp;
    }

    // Convenience
    public void sendMessage(String message, Object... args) {
        sender.sendMessage(TycoonMessages.MESSAGE_PREFIX + String.format(message, args));
    }

    public void sendError(String message, Object... args) {
        sender.sendMessage(TycoonMessages.ERROR_PREFIX + String.format(message, args));
    }
}
