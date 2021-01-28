package com.github.kikisito.phantombgone;

import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ResetCommand implements CommandExecutor {

    Plugin plugin;
    Long BedDelay;

    public ResetCommand(Plugin plugin, long BedDelay) {
        this.plugin = plugin;
        this.BedDelay = BedDelay;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args){
        if(command.getName().equalsIgnoreCase("resetsleep")){
            if(args.length == 1 && sender.hasPermission("pbg.staff.resetsleep")){
                if(plugin.getServer().getOnlinePlayers().contains(plugin.getServer().getPlayerExact(args[0]))) {
                    Player p = plugin.getServer().getPlayerExact(args[0]);
                    p.setStatistic(Statistic.TIME_SINCE_REST, 0);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("reset")));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("player-reset").replace("%USER%", p.getName())));
                } else if(args[0].equals("all")) {
                    for(Player player : plugin.getServer().getOnlinePlayers()){
                        player.setStatistic(Statistic.TIME_SINCE_REST, 0);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("reset")));
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("global-reset")));
                }
            } else {
                Player p = (Player) sender;
                if (p.hasPermission("pbg.resetsleep")) {
                    p.setStatistic(Statistic.TIME_SINCE_REST, 0);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("reset")));
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("no-permission")));
                }
            }
        }
        return true;
    }
}
