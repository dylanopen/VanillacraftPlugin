package com.lapisdev.vanillacraft.link;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.region.RegionRandomiser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import java.util.UUID;

import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class LinkQuestionSubmitListener extends ListenerAdapter {
    @Override
    public void onModalInteraction(ModalInteractionEvent e) {
        if (!e.getModalId().equals("linkquestion")) return;
        String minecraftUsername = e.getValue("username").getAsString();
        UUID minecraftUuid = Bukkit.getOfflinePlayer(minecraftUsername).getUniqueId();

        ServerPlayer existingHolder = ServerPlayer.fromMinecraftUuid(minecraftUuid);
        if (existingHolder != null) {
            if (e.getUser().equals(e.getUser())) {
                e.reply("You've already linked that account! If you're having trouble joining, please feel free to open a ticket.")
                        .setEphemeral(true).queue();
                return;
            }
            String existingDiscordHolder = jda.getUserById(existingHolder.discordUuid).getAsMention();
            e.reply("Sorry, that account doesn't seem to belong to you, but rather to" + existingDiscordHolder +
                            "! Please try linking a different username.")
                    .setEphemeral(true).queue();
            return;
        }

        boolean hasLinkedBefore = Linker.link(e.getUser().getId(), minecraftUuid);

        ServerPlayer player = ServerPlayer.fromDiscordUuid(e.getUser().getId());

        if (hasLinkedBefore) {
            e.reply("Great, transferred this Discord account to a new Minecraft account.")
                    .setEphemeral(true).queue();
            return;
        }

        String regionName = RegionRandomiser.placeInRandomRegion(player);
        e.reply("Thanks! You have successfully linked your discord account to your Minecraft account and can join vanillacraft.org to start playing :)\n" +
                        "You have been placed in the **" + regionName + "** region, and will start out there!")
                .setEphemeral(true).queue();

        e.getMember().modifyNickname(minecraftUsername).queue();
    }
}
