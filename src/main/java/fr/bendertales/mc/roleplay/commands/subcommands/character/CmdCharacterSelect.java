package fr.bendertales.mc.roleplay.commands.subcommands.character;

import fr.bendertales.mc.roleplay.impl.RolePlayManager;
import fr.bendertales.mc.roleplay.impl.vo.RolePlayException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;


public class CmdCharacterSelect {

	private final RolePlayManager rolePlayManager;

	public CmdCharacterSelect(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var playerSelector = context.getArgument("player", EntitySelector.class);
		var player = playerSelector.getPlayer(cmdSource);

		var characterIndex = context.getArgument("characterIndex", Integer.class);

		try {
			var character = rolePlayManager.selectCharacter(player, characterIndex);
			cmdSource.sendFeedback(Text.literal("You selected " + character.formatName()).formatted(Formatting.GREEN), false);
		}
		catch (RolePlayException e) {
			throw e.asCommandException();
		}

		return SINGLE_SUCCESS;
	}
}
