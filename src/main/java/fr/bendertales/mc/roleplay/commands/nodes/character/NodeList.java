package fr.bendertales.mc.roleplay.commands.nodes.character;

import java.util.List;

import fr.bendertales.mc.roleplay.commands.subcommands.character.CmdCharacterList;
import fr.bendertales.mc.roleplay.impl.RolePlayManager;
import fr.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class NodeList implements TalesCommandNode {

	private final List<String> permissions = List.of("roleplay.commands.*", "roleplay.commands.character.list");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_JUNIOR, permissions);

	private final CmdCharacterList cmdList;

	public NodeList(RolePlayManager rolePlayManager) {
		cmdList = new CmdCharacterList(rolePlayManager);
	}

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("list")
		       .requires(getRequirements().asPredicate())
		       .then(argument("player", EntityArgumentType.player())
	                .executes(cmdList::run));
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}
}
