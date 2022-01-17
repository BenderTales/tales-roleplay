package com.bendertales.mc.roleplay.commands.character;

import java.util.Collection;
import java.util.List;

import com.bendertales.mc.roleplay.commands.ModCommand;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.bendertales.mc.roleplay.impl.vo.RolePlayException;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class CmdCharacterSelect implements ModCommand {

	private static final Collection<String> PERMISSIONS = List.of("roleplay.commands.*", "roleplay.commands.character.select");

	private final RolePlayManager rolePlayManager;

	public CmdCharacterSelect(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	@Override
	public void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
		dispatcher.register(
			literal("rp").then(literal("character").then(literal("select")
                .requires(getRequirements())
                .then(argument("player", EntityArgumentType.player())
                    .then(argument("characterIndex", IntegerArgumentType.integer(0))
                        .executes(this)))
			))
		);
	}

	@Override
	public Collection<String> getRequiredPermissions() {
		return PERMISSIONS;
	}

	@Override
	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var playerSelector = context.getArgument("player", EntitySelector.class);
		var player = playerSelector.getPlayer(cmdSource);

		var characterIndex = context.getArgument("characterIndex", Integer.class);

		try {
			var character = rolePlayManager.selectCharacter(player, characterIndex);
			cmdSource.sendFeedback(new LiteralText("You selected " + character.formatName()).formatted(Formatting.GREEN), false);
		}
		catch (RolePlayException e) {
			throw e.asCommandException();
		}

		return SINGLE_SUCCESS;
	}
}
