package com.bendertales.mc.roleplay;

import com.bendertales.mc.chatapi.api.ChatAPI;
import com.bendertales.mc.roleplay.commands.CommandsRegistrar;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.bendertales.mc.roleplay.impl.channels.SayChannel;
import com.bendertales.mc.roleplay.impl.channels.WhisperChannel;
import com.bendertales.mc.roleplay.impl.channels.YellChannel;
import com.bendertales.mc.roleplay.impl.placeholders.CharacterPlaceholder;
import com.bendertales.mc.roleplay.impl.placeholders.RolePlayMessagePlaceholder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;


public class Roleplay implements ModInitializer {

	@Override
	public void onInitialize() {
		var rolePlayManager = new RolePlayManager();

		var messageSender = ChatAPI.getMessageSender();
		CommandsRegistrar.registerCommands(rolePlayManager, messageSender);

		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			ChatAPI.registerPlaceholder(new CharacterPlaceholder(rolePlayManager));
			ChatAPI.registerPlaceholder(new RolePlayMessagePlaceholder(rolePlayManager));

			ChatAPI.registerChannel(new SayChannel(rolePlayManager));
			ChatAPI.registerChannel(new WhisperChannel(rolePlayManager));
			ChatAPI.registerChannel(new YellChannel(rolePlayManager));
		});

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			rolePlayManager.load();
		});
	}

}
