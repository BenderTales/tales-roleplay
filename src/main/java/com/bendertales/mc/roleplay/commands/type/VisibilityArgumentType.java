package com.bendertales.mc.roleplay.commands.type;

import java.util.concurrent.CompletableFuture;

import com.bendertales.mc.roleplay.data.CharacterVisibilityMode;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;


public class VisibilityArgumentType implements ArgumentType<CharacterVisibilityMode> {

	@Override
	public CharacterVisibilityMode parse(StringReader reader) throws CommandSyntaxException {
		var string = reader.readUnquotedString();
		var mode = CharacterVisibilityMode.byName(string);
		if (mode == null) {
			var msg = Text.literal("Invalid visibility").formatted(Formatting.RED);
			throw new CommandSyntaxException(new SimpleCommandExceptionType(msg), msg);
		}
		return mode;
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
		return CommandSource.suggestMatching(CharacterVisibilityMode.getNames(), builder);
	}

}
