package fr.bendertales.mc.roleplay.commands.nodes.character;

import java.util.Collection;
import java.util.List;

import fr.bendertales.mc.roleplay.commands.subcommands.character.CmdCharacterInfo;
import fr.bendertales.mc.roleplay.impl.RolePlayManager;
import fr.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class NodeInfo implements TalesCommandNode {

	private final Collection<String> PERMISSIONS = List.of("roleplay.commands.*", "roleplay.commands.character.info");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_JUNIOR, PERMISSIONS);

	private final CmdCharacterInfo cmdInfo;

	public NodeInfo(RolePlayManager rolePlayManager) {
		cmdInfo = new CmdCharacterInfo(rolePlayManager);
	}

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("info")
		       .requires(getRequirements().asPredicate())
		       .then(argument("player", EntityArgumentType.player())
	                .then(argument("characterIndex", IntegerArgumentType.integer(0))
                        .executes(cmdInfo::run)));
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}
}
