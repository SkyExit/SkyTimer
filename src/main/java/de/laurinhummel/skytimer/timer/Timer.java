package de.laurinhummel.skytimer.timer;

import de.laurinhummel.skytimer.SkyTimer;
import de.laurinhummel.skytimer.utils.SkyLogger;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer {

    private boolean running;
    private int seconds;
    private int minutes;
    private int hours;
    private int days;

    int stage = 0;

    public Timer(boolean running, int days, int hours, int minutes, int seconds) {
        this.running = running;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;

        run();
    }

    public boolean isRunning() {
        return running;
    }

    public int[] getTime() {
        return new int[]{days, hours, minutes, seconds};
    }

    public void addTime() {
        this.seconds++;
        if(this.seconds == 60) {
            this.seconds = 0;
            this.minutes++;
            if(this.minutes == 60) {
                this.minutes = 0;
                this.hours++;
                if(this.hours == 24) {
                    this.hours = 0;
                    this.days++;
                }
            }
        }
    }

    public void resetTimer() {
        this.seconds = 0;
        this.minutes = 0;
        this.hours = 0;
        this.days = 0;
    }

    public void setRunning(boolean bool) { this.running = bool; }

    public void sendActionBar(boolean primary) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if(!isRunning()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED.toString() + ChatColor.BOLD + "Timer ist pausiert"));
                continue;
            }

            StringBuilder builder = new StringBuilder();
            if(days != 0) { builder.append(ChatColor.of(colorFade(primary, 0))).append(ChatColor.BOLD).append(this.days).append("d "); }
            if(hours != 0) { builder.append(ChatColor.of(colorFade(primary, 1))).append(ChatColor.BOLD).append(this.hours).append("h "); }
            if(minutes != 0) { builder.append(ChatColor.of(colorFade(primary, 2))).append(ChatColor.BOLD).append(this.minutes).append("m "); }
            if(seconds != 0) { builder.append(ChatColor.of(colorFade(primary, 3))).append(ChatColor.BOLD).append(this.seconds).append("s"); }

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(builder.toString()));
        }
    }

    private void run() {
        new BukkitRunnable() {
            int i = 0;
            boolean primary = true;
            @Override
            public void run() {
                if(!isRunning()) { return; }

                switch (i = i+2) {
                    case 2 -> { stage = 0; primary = !primary; }
                    case 20,40,60 -> addTime();
                    case 80 -> { stage = 1; addTime(); }
                    case 82 -> stage = 2;
                    case 84 -> stage = 3;
                    case 86 -> stage = 4;
                    case 88 -> stage = 5;
                    case 90 -> stage = 6;
                    case 92 -> stage = 7;
                    case 94 -> stage = 8;
                    case 96 -> stage = 9;
                    case 98 -> stage = 10;
                    case 100 -> { i = 0; addTime(); stage = 11; }
                }

                sendActionBar(primary);
            }
        }.runTaskTimer(SkyTimer.getPlugin(), 2, 2); //Wait 0 ticks before executing for the first time. Wait 1 tick between each consecutive execution
    }

    private String colorFade(boolean primary, int offset) {
        String prime = "#ffd700";
        String second = "#ff0000";
        return switch (stage - offset) {
            case 1 -> primary ? "#ffbc00" : "#ff4b00";
            case 2 -> primary ? "#ffb000" : "#ff5700";
            case 3 -> primary ? "#ffa500" : "#ff6200";
            case 4 -> primary ? "#ff9500" : "#ff7900";
            case 5 -> primary ? "#ff8c00" : "#ff8c00";
            case 6 -> primary ? "#ff7900" : "#ff9500";
            case 7 -> primary ? "#ff6200" : "#ffa500";
            case 8 -> primary ? "#ff5700" : "#ffb000";
            case 9,10,11 -> primary ? "#ff4b00" : "#ffbc00";
            default -> primary ? prime : second;
        };

        /*Dark Blue -> Light Blue
        String prime = "#020024";
        String second = "#00d4ff";
        return switch (stage - offset) {
            case 1 -> primary ? "#020d31" : "#0287ad";
            case 2 -> primary ? "#021a3e" : "#02769b";
            case 3 -> primary ? "#02274b" : "#026185";
            case 4 -> primary ? "#023458" : "#025276";
            case 5 -> primary ? "#024367" : "#024367";
            case 6 -> primary ? "#025276" : "#023458";
            case 7 -> primary ? "#026185" : "#02274b";
            case 8 -> primary ? "#02769b" : "#021a3e";
            case 9 -> primary ? "#0287ad" : "#020d31";
            default -> primary ? prime : second;
        };
         */
    }

}
