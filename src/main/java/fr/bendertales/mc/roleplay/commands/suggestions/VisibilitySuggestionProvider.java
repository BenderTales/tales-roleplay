package fr.bendertales.mc.roleplay.commands.suggestions;

import java.util.concurrent.CompletableFuture;

import fr.bendertales.mc.roleplay.data.CharacterVisibilityMode;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;


public class VisibilitySuggestionProvider implements SuggestionProvider<ServerCommandSource> {
	@Override
	public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context,
	                                                     SuggestionsBuilder builder) throws CommandSyntaxException {
		return CommandSource.suggestMatching(CharacterVisibilityMode.getNames(), builder);
	}
}
