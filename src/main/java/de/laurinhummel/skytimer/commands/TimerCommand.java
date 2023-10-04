package de.laurinhummel.skytimer.commands;

import de.laurinhummel.skytimer.SkyTimer;
import de.laurinhummel.skytimer.timer.Timer;
import de.laurinhummel.skytimer.utils.SkyLogger;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TimerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length != 1) { SkyLogger.sendMessage(sender, "no args given"); return false; }
        Timer timer = SkyTimer.getPlugin().getTimer();

        switch (args[0]) {
            case "start", "resume" -> {
                timer.setRunning(true);
                SkyLogger.sendMessage(sender, "The timer was " + args[0] + "ed");
            }
            case "stop", "pause" -> {
                timer.setRunning(false);
                SkyLogger.sendMessage(sender, "The timer was stopped");
            }
            case "reset" -> {
                timer.setRunning(false);
                timer.resetTimer();
                SkyLogger.sendMessage(sender, "Der Timer wurde zurückgesetzt.");
            }
            default -> SkyLogger.sendMessage(sender, ChatColor.of(args[0]) + "This is a test msg");
        }
        return false;
    }
}
