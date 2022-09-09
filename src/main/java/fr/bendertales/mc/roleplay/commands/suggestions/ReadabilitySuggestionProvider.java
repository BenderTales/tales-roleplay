package fr.bendertales.mc.roleplay.commands.suggestions;

import java.util.concurrent.CompletableFuture;

import fr.bendertales.mc.roleplay.data.CharacterReadabilityMode;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;


public class ReadabilitySuggestionProvider implements SuggestionProvider<ServerCommandSource> {

	@Override
	public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context,
	                                                     SuggestionsBuilder builder) throws CommandSyntaxException {
		return CommandSource.suggestMatching(CharacterReadabilityMode.getNames(), builder);
	}
}
