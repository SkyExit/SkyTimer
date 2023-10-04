package de.laurinhummel.skytimer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TimerTabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length == 1) {
            List<String> params = new ArrayList<>();
                params.add("start");
                params.add("resume");
                params.add("stop");
                params.add("pause");
                params.add("reset");

            return params;
        }

        return null;
    }
}
