package com.bendertales.mc.roleplay;

import com.bendertales.mc.roleplay.commands.CommandsRegistrar;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import net.fabricmc.api.ModInitializer;


public class Roleplay implements ModInitializer {

	@Override
	public void onInitialize() {
		var rolePlayManager = new RolePlayManager();
		CommandsRegistrar.registerCommands(rolePlayManager);
	}

}
