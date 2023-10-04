package de.laurinhummel.skytimer;

import de.laurinhummel.skytimer.commands.TimerCommand;
import de.laurinhummel.skytimer.commands.TimerTabCompletion;
import de.laurinhummel.skytimer.timer.Timer;
import de.laurinhummel.skytimer.utils.SkyLogger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyTimer extends JavaPlugin {
    private static SkyTimer plugin;

    private Timer timer;

    @Override
    public void onEnable() {
        plugin = this;

        SkyLogger.sendConsole("creating default config...");
        FileConfiguration config = getPlugin().getConfig();
            config.addDefault("Timer.seconds", 0);
            config.addDefault("Timer.minutes", 0);
            config.addDefault("Timer.hours", 0);
            config.addDefault("Timer.days", 0);
            config.options().copyDefaults(true);
        saveConfig();

        SkyLogger.sendConsole("Loading time...");
        try {
            timer = new Timer(false, config.getInt("Timer.days"),
                    config.getInt("Timer.hours"), config.getInt("Timer.minutes"), config.getInt("Timer.seconds"));
            SkyLogger.sendConsole("Time loaded successfully...");
        } catch (Exception ex) {
            timer = new Timer(false, 0, 0, 0, 0);
            SkyLogger.sendConsole("loading time failed... resetting");
        }


        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("timer").setTabCompleter(new TimerTabCompletion());
    }

    @Override
    public void onDisable() {
        FileConfiguration config = getPlugin().getConfig();
            config.set("Timer.days", timer.getTime()[0]);
            config.set("Timer.hours", timer.getTime()[1]);
            config.set("Timer.minutes", timer.getTime()[2]);
            config.set("Timer.seconds", timer.getTime()[3]);
        saveConfig();
        SkyLogger.sendConsole("Time was saved to config!");
    }

    public Timer getTimer() {
        return timer;
    }

    public static SkyTimer getPlugin() {
        return plugin;
    }
}
