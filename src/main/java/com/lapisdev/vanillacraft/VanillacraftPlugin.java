package com.lapisdev.vanillacraft;

import com.lapisdev.vanillacraft.ban.BanModule;
import com.lapisdev.vanillacraft.database.Database;
import com.lapisdev.vanillacraft.discord.DiscordModule;
import com.lapisdev.vanillacraft.gamechat.GameChatModule;
import com.lapisdev.vanillacraft.kick.KickModule;
import com.lapisdev.vanillacraft.link.LinkModule;
import com.lapisdev.vanillacraft.log.login.LoginModule;
import com.lapisdev.vanillacraft.newspawn.NewSpawnModule;
import com.lapisdev.vanillacraft.team.TeamModule;
import com.lapisdev.vanillacraft.whitelist.WhitelistModule;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class VanillacraftPlugin extends JavaPlugin {
    DiscordModule discordModule;
    LinkModule linkModule;
    KickModule kickModule;
    WhitelistModule whitelistModule;
    LoginModule loginModule;
    NewSpawnModule newSpawnModule;
    GameChatModule gameChatModule;
    BanModule banModule;
    TeamModule teamModule;

    @Override
    public void onEnable() {
        Database.init();
        discordModule = new DiscordModule();
        linkModule = new LinkModule();
        kickModule = new KickModule();
        whitelistModule = new WhitelistModule();
        loginModule = new LoginModule();
        newSpawnModule = new NewSpawnModule();
        gameChatModule = new GameChatModule();
        banModule = new BanModule();
        teamModule = new TeamModule();
    }

    @Override
    public void onDisable() {
        Database.close();
        gameChatModule.disable();
    }

    public static VanillacraftPlugin plugin() {
        return (VanillacraftPlugin)Bukkit.getServer().getPluginManager().getPlugin("VanillacraftPlugin");
    }

    public static void handle(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin());
    }
}
