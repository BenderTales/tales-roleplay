package fr.bendertales.mc.roleplay.commands.nodes.root;

import java.util.List;

import fr.bendertales.mc.roleplay.commands.subcommands.CmdRollDice;
import fr.bendertales.mc.roleplay.impl.RolePlayManager;
import fr.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class NodeRollDice implements TalesCommandNode {

	private final List<String> permissions = List.of("roleplay.commands.*","roleplay.commands.rolldice");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_JUNIOR, permissions);

	private final CmdRollDice cmdRollDice;

	public NodeRollDice(RolePlayManager rolePlayManager) {
		cmdRollDice = new CmdRollDice(rolePlayManager);
	}

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("rolldice")
		       .requires(getRequirements().asPredicate())
		       .executes(cmdRollDice::runBaseDice)
		       .then(literal("--global")
	                .then(argument("dices", StringArgumentType.greedyString())
	                    .executes(cmdRollDice::rollGlobal)))
		       .then(argument("dices", StringArgumentType.greedyString())
	                .executes(cmdRollDice::run));
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}
}
