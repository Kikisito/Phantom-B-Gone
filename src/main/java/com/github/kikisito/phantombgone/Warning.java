package com.github.kikisito.phantombgone;

import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Statistic;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.NumberConversions;

public class Warning implements Runnable {
    String[] EarlyNights;
    List<String> LateNights;
    Plugin plugin;
    Configuration config;
    Long resetTime;

    public Warning(Plugin plugin, String[] EarlyNights, List<String> LateNights, long resetTime) {
        this.EarlyNights = EarlyNights;
        this.LateNights = LateNights;
        this.resetTime = resetTime;
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public void run() {
        Long time = (plugin.getServer().getWorld(config.getString("world"))).getTime();
        if (time == 12520L + resetTime) {
            for (Player P : Bukkit.getServer().getOnlinePlayers()) {
                if(P.getGameMode() == GameMode.CREATIVE){
                    P.setStatistic(Statistic.TIME_SINCE_REST, 0);
                    P.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("creative-reset")));
                } else if(P.hasPermission("pbg.autoresetsleep")){
                    P.setStatistic(Statistic.TIME_SINCE_REST, 0);
                    P.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("permission-reset")));
                } else {
                    long TimeAwake = P.getStatistic(Statistic.TIME_SINCE_REST);
                    int daysAwake = NumberConversions.floor((double) (TimeAwake / 24000L));
                    if (daysAwake < 3) {
                        P.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("msgprefix") + this.EarlyNights[daysAwake].replace("%AWAKE%", Integer.toString(daysAwake))));
                    } else {
                        Random r = new Random();
                        String message = this.LateNights.get(r.nextInt(this.LateNights.size()));
                        P.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("msgprefix") + message.replace("%AWAKE%", Integer.toString(daysAwake))));
                    }
                }
            }
        }
    }
}
