package me.yiyi1234.boostergift.api;

import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.AccountUnlinkedEvent;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;
import github.scarsz.discordsrv.util.DiscordUtil;
import me.yiyi1234.boostergift.BoosterGift;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

public class AccountUnlinked implements Listener {
    @Subscribe
    public void accountUnlinked(AccountUnlinkedEvent event) {
        User user = DiscordUtil.getJda().getUserById(event.getDiscordId());

        if (user != null) {
            Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&7" + event.getPlayer().getName() + " 剛剛取消與他的 Discord 帳號連結，已將他從 discord list 移除: " + user.getName() + " (" + user.getId() + ")" + "!"));
            BoosterGift.getInstance().getLinkedData().set(event.getDiscordId(),null);
            BoosterGift.getInstance().saveLinkedData();
        }
    }
}
