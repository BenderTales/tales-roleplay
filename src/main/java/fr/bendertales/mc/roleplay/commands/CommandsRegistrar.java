package fr.bendertales.mc.roleplay.commands;

import java.util.stream.Stream;

import fr.bendertales.mc.roleplay.RolePlayConstants;
import fr.bendertales.mc.roleplay.commands.nodes.root.NodeRP;
import fr.bendertales.mc.roleplay.commands.nodes.root.NodeRollDice;
import fr.bendertales.mc.roleplay.commands.nodes.root.ShortcutNode;
import fr.bendertales.mc.roleplay.impl.RolePlayManager;
import fr.bendertales.mc.roleplay.impl.channels.SayChannel;
import fr.bendertales.mc.roleplay.impl.channels.WhisperChannel;
import fr.bendertales.mc.roleplay.impl.channels.YellChannel;
import fr.bendertales.mc.channels.api.Messenger;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;


public class CommandsRegistrar {

	public static void registerCommands(RolePlayManager rolePlayManager, Messenger messageSender) {
		var event = CommandRegistrationCallback.EVENT;

		event.register((dispatcher, ra, env) -> {
			commands(rolePlayManager, messageSender)
				.forEach(cmd -> dispatcher.register(cmd.asBrigadierNode()));
		});
	}

	public static Stream<TalesCommandNode> commands(RolePlayManager rolePlayManager, Messenger messenger) {
		return Stream.of(
			new NodeRP(rolePlayManager),
			new NodeRollDice(rolePlayManager),
			new ShortcutNode("say", messenger, RolePlayConstants.Ids.Channels.SAY, SayChannel.PERMISSION),
			new ShortcutNode("whisper", messenger, RolePlayConstants.Ids.Channels.WHISPER, WhisperChannel.PERMISSION),
			new ShortcutNode("yell", messenger, RolePlayConstants.Ids.Channels.YELL, YellChannel.PERMISSION)
		);
	}
}
