package me.yiyi1234.boostergift.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BoosterGiftCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7BoosterGift 自動加成獎勵派發"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6製作者: 依依#1350 &7| &bVersion &e1.0"));
        }

        return false;
    }


}
