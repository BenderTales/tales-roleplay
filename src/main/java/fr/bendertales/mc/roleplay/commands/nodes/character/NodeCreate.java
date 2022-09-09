package fr.bendertales.mc.roleplay.commands.nodes.character;

import java.util.List;

import fr.bendertales.mc.roleplay.commands.subcommands.character.CmdCharacterCreate;
import fr.bendertales.mc.roleplay.impl.RolePlayManager;
import fr.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class NodeCreate implements TalesCommandNode {

	private final List<String> PERMISSIONS_SELF = List.of("roleplay.commands.*",
	                                                      "roleplay.commands.character.create");

	private final List<String> PERMISSIONS_OTHER = List.of("roleplay.commands.*",
	                                                       "roleplay.commands.character.create.other");

	private final CommandNodeRequirements requirementsSelf = CommandNodeRequirements.of(OP_MEDIOR, PERMISSIONS_SELF);
	private final CommandNodeRequirements requirementsOther = CommandNodeRequirements.of(OP_MEDIOR, PERMISSIONS_OTHER);

	private final CmdCharacterCreate cmdCreate;

	public NodeCreate(RolePlayManager rolePlayManager) {
		cmdCreate = new CmdCharacterCreate(rolePlayManager);
	}

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("create")
		       .requires(getRequirements().asPredicate())
		       .executes(cmdCreate::createForSelf)
		       .then(argument("target", EntityArgumentType.player())
	                .requires(requirementsOther.asPredicate())
	                .executes(cmdCreate::createForOther));
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirementsSelf;
	}
}
