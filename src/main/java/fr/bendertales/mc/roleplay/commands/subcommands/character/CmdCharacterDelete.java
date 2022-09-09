package fr.bendertales.mc.roleplay.commands.subcommands.character;

import fr.bendertales.mc.roleplay.impl.RolePlayManager;
import fr.bendertales.mc.roleplay.impl.vo.RolePlayException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;


public class CmdCharacterDelete {

	private final RolePlayManager rolePlayManager;

	public CmdCharacterDelete(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var playerSelector = context.getArgument("player", EntitySelector.class);
		var player = playerSelector.getPlayer(cmdSource);
		var characterIndex = context.getArgument("characterIndex", Integer.class);

		try {
			rolePlayManager.deleteCharacter(player, characterIndex);
		}
		catch (RolePlayException e) {
			throw e.asCommandException();
		}
		return 0;
	}
}
