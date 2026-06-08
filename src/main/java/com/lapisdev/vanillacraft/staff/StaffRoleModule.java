package com.lapisdev.vanillacraft.staff;

import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class StaffRoleModule {
    public StaffRoleModule() {
	jda.addEventListener(new StaffRoleAddCmd());
	jda.addEventListener(new StaffRoleRemoveCmd());
    }
}
