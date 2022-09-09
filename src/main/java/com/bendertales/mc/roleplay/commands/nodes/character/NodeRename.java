package com.bendertales.mc.roleplay.commands.nodes.character;

import java.util.List;

import com.bendertales.mc.roleplay.commands.subcommands.character.CmdCharacterRename;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import fr.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class NodeRename implements TalesCommandNode {

	private final List<String> permissions = List.of("roleplay.commands.*", "roleplay.commands.character.rename");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_MEDIOR, permissions);

	private final CmdCharacterRename cmdRename;

	public NodeRename(RolePlayManager rolePlayManager) {
		cmdRename = new CmdCharacterRename(rolePlayManager);
	}

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("rename")
		       .requires(getRequirements().asPredicate())
		       .then(argument("player", EntityArgumentType.player())
	                .then(argument("characterIndex", IntegerArgumentType.integer(0))
	                    .then(argument("new-name", StringArgumentType.greedyString())
                            .executes(cmdRename::run))));
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}
}
