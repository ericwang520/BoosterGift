package me.yiyi1234.boostergift.api;

import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.AccountLinkedEvent;
import me.yiyi1234.boostergift.BoosterGift;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

public class AccountLinked implements Listener {
    @Subscribe
    public void accountsLinked(AccountLinkedEvent event) {
        // Example of broadcasting a message when a new account link has been made

        Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&7" +event.getPlayer().getName() + " 剛剛與他的 DC 帳號連結，已記錄到 discord list 名單內: " + event.getUser() + "!"));
        BoosterGift.getInstance().getLinkedData().set(event.getUser().getId(),event.getPlayer().getUniqueId().toString());
        BoosterGift.getInstance().saveLinkedData();

    }
}
