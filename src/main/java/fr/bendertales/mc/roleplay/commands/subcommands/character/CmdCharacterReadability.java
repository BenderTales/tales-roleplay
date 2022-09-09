package fr.bendertales.mc.roleplay.commands.subcommands.character;

import fr.bendertales.mc.roleplay.data.CharacterReadabilityMode;
import fr.bendertales.mc.roleplay.impl.RolePlayManager;
import fr.bendertales.mc.roleplay.impl.vo.RolePlayException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;


public class CmdCharacterReadability {

	private static final SimpleCommandExceptionType readabilityException =
			new SimpleCommandExceptionType(Text.literal("Invalid readability").formatted(Formatting.RED));

	private final RolePlayManager rolePlayManager;

	public CmdCharacterReadability(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	public int playerDefault(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var playerSelector = context.getArgument("player", EntitySelector.class);
		var characterIndex = context.getArgument("characterIndex", Integer.class);
		var player = playerSelector.getPlayer(cmdSource);
		var mode = getMode(context);

		try {
			rolePlayManager.setDefaultReadability(player, characterIndex, mode);
			cmdSource.sendFeedback(Text.of("New default readability set to " + mode), true);
		}
		catch (RolePlayException e) {
			throw e.asCommandException();
		}

		return SINGLE_SUCCESS;
	}

	public int clearPlayer(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var playerSelector = context.getArgument("player", EntitySelector.class);
		var characterIndex = context.getArgument("characterIndex", Integer.class);
		var player = playerSelector.getPlayer(cmdSource);
		var otherPlayerSelector = context.getArgument("other-player", EntitySelector.class);
		var otherPlayer = otherPlayerSelector.getPlayer(cmdSource);

		try {
			rolePlayManager.clearPlayerReadability(player, characterIndex, otherPlayer);
			cmdSource.sendFeedback(Text.of("Player readability clear"), true);
		}
		catch (RolePlayException e) {
			throw e.asCommandException();
		}

		return SINGLE_SUCCESS;
	}

	public int setPlayer(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var playerSelector = context.getArgument("player", EntitySelector.class);
		var characterIndex = context.getArgument("characterIndex", Integer.class);
		var player = playerSelector.getPlayer(cmdSource);
		var otherPlayerSelector = context.getArgument("other-player", EntitySelector.class);
		var otherPlayer = otherPlayerSelector.getPlayer(cmdSource);
		var mode = getMode(context);

		try {
			rolePlayManager.setPlayerReadability(player, characterIndex, otherPlayer, mode);
			cmdSource.sendFeedback(Text.of("Player readability set to " + mode), true);
		}
		catch (RolePlayException e) {
			throw e.asCommandException();
		}
		return SINGLE_SUCCESS;
	}

	private CharacterReadabilityMode getMode(CommandContext<ServerCommandSource> context)
	throws CommandSyntaxException {
		var modeStr = context.getArgument("mode", String.class);
		var mode = CharacterReadabilityMode.byName(modeStr.toUpperCase());
		if (mode == null) {
			throw readabilityException.create();
		}
		return mode;
	}
}
