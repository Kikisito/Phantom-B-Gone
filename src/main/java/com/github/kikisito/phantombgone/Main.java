package com.github.kikisito.phantombgone;

import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    FileConfiguration config = this.getConfig();
    int scheduler;

    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("resetsleep").setExecutor(new ResetCommand(this, this.config.getLong("BedDelay")));
        this.getServer().getPluginManager().registerEvents(new SleepListener(this, this.config.getLong("BedDelay")), this);
        String[] InitNights = new String[3];

        for(int i = 1; i <= 3; ++i) {
            InitNights[i - 1] = this.config.getString(Integer.toString(i));
        }

        List<String> LateNights = this.config.getStringList("4");
        Long resetTime = this.config.getLong("ResetTime");
        scheduler = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Warning(this, InitNights, LateNights, resetTime), 0L, 1L);
    }

    public void onDisable() {
        HandlerList.unregisterAll(new SleepListener(this, this.config.getLong("BedDelay")));
        this.getServer().getScheduler().cancelTask(scheduler);
    }
}
