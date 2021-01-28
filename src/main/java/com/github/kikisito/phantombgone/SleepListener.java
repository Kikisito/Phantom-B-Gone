package com.github.kikisito.phantombgone;

import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.plugin.Plugin;

public class SleepListener implements Listener {
    Plugin plugin;
    Long BedDelay;

    public SleepListener(Plugin plugin, long BedDelay) {
        this.plugin = plugin;
        this.BedDelay = BedDelay;
    }

    @EventHandler
    public void PlayerBedEnterEvent(PlayerBedEnterEvent event) {
        Player p = event.getPlayer();
        p.setStatistic(Statistic.TIME_SINCE_REST, 0);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("reset")));
    }

    @EventHandler
    public void PlayerGamemodeEvent(PlayerGameModeChangeEvent event) {
        Player p = event.getPlayer();
        p.setStatistic(Statistic.TIME_SINCE_REST, 0);
    }
}
