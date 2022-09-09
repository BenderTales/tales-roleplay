package fr.bendertales.mc.roleplay;

import fr.bendertales.mc.roleplay.commands.CommandsRegistrar;
import fr.bendertales.mc.roleplay.impl.RolePlayManager;
import fr.bendertales.mc.roleplay.impl.channels.SayChannel;
import fr.bendertales.mc.roleplay.impl.channels.WhisperChannel;
import fr.bendertales.mc.roleplay.impl.channels.YellChannel;
import fr.bendertales.mc.roleplay.impl.placeholders.CharacterPlaceholder;
import fr.bendertales.mc.roleplay.impl.placeholders.RolePlayMessagePlaceholder;
import fr.bendertales.mc.channels.api.ChannelsAPI;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;


public class Roleplay implements ModInitializer {

	@Override
	public void onInitialize() {
		var rolePlayManager = new RolePlayManager();

		var messageSender = ChannelsAPI.getMessenger();
		CommandsRegistrar.registerCommands(rolePlayManager, messageSender);

		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			ChannelsAPI.registerPlaceholder(new CharacterPlaceholder(rolePlayManager));
			ChannelsAPI.registerPlaceholder(new RolePlayMessagePlaceholder(rolePlayManager));

			ChannelsAPI.registerChannel(new SayChannel(rolePlayManager));
			ChannelsAPI.registerChannel(new WhisperChannel(rolePlayManager));
			ChannelsAPI.registerChannel(new YellChannel(rolePlayManager));
		});

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			rolePlayManager.load();
		});
	}

}
