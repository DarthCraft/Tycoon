package net.darthcraft.tycoon.command;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdContext {

    private final CommandSender sender;
    private final Command command;
    private final String label;
    private String[] args;

    public CmdContext(CommandSender sender, Command command, String label, String[] args) {
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

    public int argsLength() {
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
}
