package fr.bendertales.mc.roleplay.commands.nodes.character;

import java.util.Collection;
import java.util.List;

import fr.bendertales.mc.roleplay.commands.subcommands.character.CmdCharacterPerceptionCheck;
import fr.bendertales.mc.roleplay.impl.RolePlayManager;
import fr.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class NodePerceptionCheck implements TalesCommandNode {

	private final Collection<String> PERMISSIONS = List.of("roleplay.commands.*", "roleplay.commands.check.perception");
	private final CommandNodeRequirements     requirements = CommandNodeRequirements.of(OP_MEDIOR, PERMISSIONS);
	private final CmdCharacterPerceptionCheck cmdCheck;

	public NodePerceptionCheck(RolePlayManager rolePlayManager) {
		cmdCheck = new CmdCharacterPerceptionCheck(rolePlayManager);
	}

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("check")
		       .requires(getRequirements().asPredicate())
		       .then(literal("perception")
	                .then(argument("player", EntityArgumentType.player())
	                    .then(argument("characterIndex", IntegerArgumentType.integer(0))
                            .then(argument("other-player", EntityArgumentType.player())
                                .executes(cmdCheck::run)))));
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}
}
