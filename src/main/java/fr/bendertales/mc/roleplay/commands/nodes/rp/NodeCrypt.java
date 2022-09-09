package fr.bendertales.mc.roleplay.commands.nodes.rp;

import java.util.Collection;
import java.util.List;

import fr.bendertales.mc.roleplay.commands.subcommands.rp.CmdCrypt;
import fr.bendertales.mc.roleplay.impl.RolePlayManager;
import fr.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class NodeCrypt implements TalesCommandNode {

	private final Collection<String> PERMISSIONS = List.of("roleplay.commands.*", "roleplay.commands.crypt");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_JUNIOR, PERMISSIONS);

	private final CmdCrypt cmdCrypt;

	public NodeCrypt(RolePlayManager rolePlayManager) {
		cmdCrypt = new CmdCrypt(rolePlayManager);
	}

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("crypt")
		        .requires(requirements.asPredicate())
                .then(literal("do")
	                .then(argument("message", StringArgumentType.greedyString())
	                    .executes(cmdCrypt::encrypt)
				    )
	            )
		        .then(literal("undo")
	                .then(argument("message", StringArgumentType.greedyString())
	                    .executes(cmdCrypt::decrypt)
	                )
		        );
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}
}
