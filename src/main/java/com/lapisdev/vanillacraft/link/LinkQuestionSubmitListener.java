package com.lapisdev.vanillacraft.link;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.region.RegionRandomiser;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import java.util.UUID;

public class LinkQuestionSubmitListener extends ListenerAdapter {
    @Override
    public void onModalInteraction(ModalInteractionEvent e) {
        if (!e.getModalId().equals("linkquestion")) return;
        String minecraftUsername = e.getValue("username").getAsString();
        UUID minecraftUuid = Bukkit.getOfflinePlayer(minecraftUsername).getUniqueId();

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
    }
}
