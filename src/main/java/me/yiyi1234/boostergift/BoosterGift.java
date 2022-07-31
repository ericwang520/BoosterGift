package me.yiyi1234.boostergift;

import github.scarsz.discordsrv.DiscordSRV;
import me.yiyi1234.boostergift.api.AccountLinked;
import me.yiyi1234.boostergift.api.AccountUnlinked;
import me.yiyi1234.boostergift.commands.BoosterGiftCommand;
import me.yiyi1234.boostergift.discordbot.CreateBot;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class BoosterGift extends JavaPlugin {
    private FileConfiguration config;
    private FileConfiguration linkedData;
    private static BoosterGift instance;
    public CreateBot createBot;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);
        this.createBot = new CreateBot();
        config();
        setLinkedData();
        try {
            createBot.createJDA();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getCommand("BoosterGift").setExecutor(new BoosterGiftCommand());
        DiscordSRV.api.subscribe(new AccountLinked());
        DiscordSRV.api.subscribe(new AccountUnlinked());
        getServer().getPluginManager().registerEvents(new AccountLinked(), this);
        getServer().getPluginManager().registerEvents(new AccountUnlinked(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7<&6BoosterGift&7> &aBoosterGift Version 1.0 啟動成功"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7<&6BoosterGift&7> &6製作者 依依#1350"));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        DiscordSRV.api.unsubscribe(new AccountLinked());
        DiscordSRV.api.unsubscribe(new AccountUnlinked());
    }

    public void config() {
        File file = new File(this.getDataFolder().getAbsolutePath() + "/config.yml");
        if (!file.exists()) {
            Bukkit.getConsoleSender().sendMessage("§7<§6BoosterGift§7> §f正在生成config！");
            this.getDataFolder().mkdir();
            this.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLinkedData() {
        File data = new File(this.getDataFolder().getAbsolutePath() + "/LinkedData.yml");

        if (!data.exists()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7<&6BoosterGift&7> &f正在生成 LinkedData.yml"));
            this.getDataFolder().mkdir();
        }
        linkedData = YamlConfiguration.loadConfiguration(data);
        try {
            linkedData.save(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void saveLinkedData() {
        File data = new File(this.getDataFolder().getAbsolutePath() + "/LinkedData.yml");
        try {
            linkedData.save(data);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7<&6BoosterGift&7> &f無法儲存資料到 " + data.getName()));
        }
    }


    public FileConfiguration getLinkedData() {return linkedData;}

    private void setInstance(BoosterGift instance) {
        BoosterGift.instance = instance;
    }

    public static BoosterGift getInstance() {
        return instance;
    }
}
