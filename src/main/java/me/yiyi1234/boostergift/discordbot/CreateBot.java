package me.yiyi1234.boostergift.discordbot;


import me.yiyi1234.boostergift.BoosterGift;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.Bukkit;

import java.util.UUID;


public class CreateBot implements EventListener {
    public void createJDA() throws Exception {
        JDA jda = JDABuilder.createDefault(BoosterGift.getInstance().getConfig().getString("discord-bot-token"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new CreateBot())
                .build();
    }

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof ReadyEvent) {
            Bukkit.getConsoleSender().sendMessage("加成機器人已成功啟動。");
        }
        if (event instanceof GuildMemberRoleAddEvent) {
            for (Role role : ((GuildMemberRoleAddEvent) event).getRoles()) {
                linked(role, ((GuildMemberRoleAddEvent) event).getUser(), event.getJDA());
            }
        }
        if (event instanceof GuildMemberRoleRemoveEvent) {
            for (Role role : ((GuildMemberRoleRemoveEvent) event).getRoles()) {
                unlinked(role, ((GuildMemberRoleRemoveEvent) event).getUser(), event.getJDA());
            }
        }
    }
    public void linked(Role role, User user, JDA jda) {

        long configId = BoosterGift.getInstance().getConfig().getLong("booster-role-id");
        if (role.getIdLong() == configId) {
            String UserID = String.valueOf(user.getIdLong());
            if (BoosterGift.getInstance().getLinkedData().getString(UserID) != null) {
                UUID playeruuid = UUID.fromString(BoosterGift.getInstance().getLinkedData().getString(UserID));
                for (String cmd : BoosterGift.getInstance().getConfig().getStringList("linkedcommands")) {
                    String cmdReplaced = cmd.replace("%player%", Bukkit.getOfflinePlayer(playeruuid).getName());
                    Bukkit.getScheduler().runTask(BoosterGift.getInstance(), () -> {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmdReplaced);
                    });
                }
                User owner = jda.getUserById(BoosterGift.getInstance().getConfig().getString("owner-discord-id"));
                owner.openPrivateChannel().queue((channel) ->
                        channel.sendMessageFormat(":o: Discord 用戶:" + (user.getAsTag() + " 剛剛加成了伺服器，成功給予玩家獎勵。")).queue());
            }else {

                User owner = jda.getUserById(BoosterGift.getInstance().getConfig().getString("owner-discord-id"));
                owner.openPrivateChannel().queue((channel) ->
                        channel.sendMessageFormat(":x: Discord 用戶:" + (user.getAsTag() + " 剛剛加成了伺服器，但她沒有綁定遊戲所以沒有給予禮物")).queue());


                user.openPrivateChannel().queue((channel) ->
                        channel.sendMessageFormat(BoosterGift.getInstance().getConfig().getString("booster-sendmsg")).queue());
            }


        }
    }
    public void unlinked(Role role, User user, JDA jda) {
        long configId = BoosterGift.getInstance().getConfig().getLong("booster-role-id");
        if (role.getIdLong() == configId) {
            String UserID = String.valueOf(user.getIdLong());
            if (BoosterGift.getInstance().getLinkedData().getString(UserID) != null) {
                UUID playeruuid = UUID.fromString(BoosterGift.getInstance().getLinkedData().getString(UserID));
                for (String cmd : BoosterGift.getInstance().getConfig().getStringList("unlinkedcommands")) {
                    String cmdReplaced = cmd.replace("%player%", Bukkit.getOfflinePlayer(playeruuid).getName());
                    Bukkit.getScheduler().runTask(BoosterGift.getInstance(), () -> {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmdReplaced);
                    });
                }
                User owner = jda.getUserById(BoosterGift.getInstance().getConfig().getString("owner-discord-id"));
                owner.openPrivateChannel().queue((channel) ->
                        channel.sendMessageFormat(":o: Discord 用戶:" + (user.getAsTag() + " 剛剛取消了加成伺服器，成功收回玩家的獎勵。")).queue());

                user.openPrivateChannel().queue((channel) ->
                        channel.sendMessageFormat(BoosterGift.getInstance().getConfig().getString("booster-cancel")).queue());
            }else {
                User owner = jda.getUserById(BoosterGift.getInstance().getConfig().getString("owner-discord-id"));
                owner.openPrivateChannel().queue((channel) ->
                        channel.sendMessageFormat(":x: Discord 用戶:" + (user.getAsTag() + " 剛剛取消加成了伺服器，但她沒有綁定遊戲所以沒有取消..")).queue());
            }
        }
    }


}