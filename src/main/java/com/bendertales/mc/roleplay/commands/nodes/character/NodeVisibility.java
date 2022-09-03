package com.bendertales.mc.roleplay.commands.nodes.character;

import java.util.Collection;
import java.util.List;

import com.bendertales.mc.roleplay.commands.subcommands.character.CmdCharacterVisibility;
import com.bendertales.mc.roleplay.commands.suggestions.VisibilitySuggestionProvider;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import com.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class NodeVisibility implements TalesCommandNode {

	private final Collection<String> PERMISSIONS = List.of("roleplay.commands.*", "roleplay.commands.visibility");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_SENIOR, PERMISSIONS);

	private final CmdCharacterVisibility cmdVisibility;

	public NodeVisibility(RolePlayManager rolePlayManager) {
		cmdVisibility = new CmdCharacterVisibility(rolePlayManager);
	}

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		var suggestionProvider = new VisibilitySuggestionProvider();

		return literal("visibility")
		       .requires(getRequirements().asPredicate())
		       .then(argument("player", EntityArgumentType.player())
	                .then(argument("characterIndex", IntegerArgumentType.integer(0))
	                    .then(literal("default")
                            .then(argument("mode", StringArgumentType.word())
                                .suggests(suggestionProvider)
                                .executes(cmdVisibility::playerDefault)))
	                    .then(argument("other-player", EntityArgumentType.player())
                            .then(literal("clear")
                                .executes(cmdVisibility::clearPlayer))
                            .then(literal("set")
                                .then(argument("mode", StringArgumentType.word())
                                    .suggests(suggestionProvider)
                                    .executes(cmdVisibility::setPlayer))))));
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}
}
