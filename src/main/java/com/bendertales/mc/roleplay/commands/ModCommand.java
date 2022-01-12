package com.bendertales.mc.roleplay.commands;

import java.util.Collection;
import java.util.function.Predicate;

import com.bendertales.mc.roleplay.impl.helper.Perms;
import com.mojang.brigadier.Command;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;


public interface ModCommand extends CommandRegistrationCallback, Command<ServerCommandSource> {

	int ERROR = -1;
	int SUCCESS = 0;

	int OP_JUNIOR = 1;
	int OP_MEDIOR = 2;
	int OP_SENIOR = 3;
	int OP_FULL   = 4;

	Collection<String> getRequiredPermissions();

	default int getOptionalPermissionLevel() {
		return OP_MEDIOR;
	}

	default Predicate<ServerCommandSource> getRequirements() {
		int permissionLevel = getOptionalPermissionLevel();
		var requiredPermissions = getRequiredPermissions();

		return (cmdSource) -> {
			if (cmdSource.hasPermissionLevel(permissionLevel)
					    || Perms.hasAny(cmdSource, requiredPermissions)) {
				return true;
			}
			return false;
		};
	}
}