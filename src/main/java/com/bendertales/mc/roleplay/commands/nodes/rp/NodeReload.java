package com.bendertales.mc.roleplay.commands.nodes.rp;

import java.util.Collection;
import java.util.List;

import com.bendertales.mc.roleplay.commands.subcommands.rp.CmdReload;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import com.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;


public class NodeReload implements TalesCommandNode {

	private final Collection<String> PERMISSIONS = List.of("roleplay.commands.*", "roleplay.commands.reload");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_FULL, PERMISSIONS);

	private final CmdReload cmdReload;

	public NodeReload(RolePlayManager rolePlayManager) {
		cmdReload = new CmdReload(rolePlayManager);
	}

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("reload")
		       .requires(getRequirements().asPredicate())
		       .executes(cmdReload::run);
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}
}
