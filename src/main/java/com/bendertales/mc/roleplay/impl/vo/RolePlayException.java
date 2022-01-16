package com.bendertales.mc.roleplay.impl.vo;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;


public class RolePlayException extends Exception {

	public RolePlayException(String message) {
		super(message);
	}

	public RolePlayException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandSyntaxException asCommandException() {
		var msg = new LiteralText(getMessage()).formatted(Formatting.RED);
		return new CommandSyntaxException(new SimpleCommandExceptionType(msg), msg);
	}
}
