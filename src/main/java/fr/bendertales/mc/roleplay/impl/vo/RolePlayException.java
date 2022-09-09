package fr.bendertales.mc.roleplay.impl.vo;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;


public class RolePlayException extends Exception {

	public RolePlayException(String message) {
		super(message);
	}

	public RolePlayException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandSyntaxException asCommandException() {
		var msg = Text.literal(getMessage()).formatted(Formatting.RED);
		return new SimpleCommandExceptionType(msg).create();
	}
}
