package fr.bendertales.mc.roleplay.commands.subcommands.character;

import fr.bendertales.mc.roleplay.impl.RolePlayManager;
import fr.bendertales.mc.roleplay.impl.vo.RolePlayException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;


public class CmdCharacterPerceptionCheck {

	private final RolePlayManager rolePlayManager;

	public CmdCharacterPerceptionCheck(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var playerSelector = context.getArgument("player", EntitySelector.class);
		var otherPlayerSelector = context.getArgument("other-player", EntitySelector.class);
		int characterIndex = context.getArgument("characterIndex", Integer.class);
		var player = playerSelector.getPlayer(cmdSource);
		var otherPlayer = otherPlayerSelector.getPlayer(cmdSource);

		var config = rolePlayManager.getOrCreatePlayerConfiguration(player);
		try {
			var character = config.getCharacter(characterIndex);
			var perception = character.getPlayerPerceptions().get(otherPlayer.getUuid());
			var visibility = perception == null ? null : perception.getVisibility();
			var readability = perception == null ? null : perception.getReadability();
			var msg = "%s's perception of %s: Visibility=%s - Readability=%s".formatted(
					player.getEntityName(), character.formatName(), visibility, readability);
			cmdSource.sendFeedback(Text.of(msg), false);
		}
		catch (RolePlayException e) {
			throw e.asCommandException();
		}

		return SINGLE_SUCCESS;
	}
}
