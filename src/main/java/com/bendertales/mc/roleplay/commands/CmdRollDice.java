package com.bendertales.mc.roleplay.commands;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.apache.commons.lang3.StringUtils;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class CmdRollDice implements ModCommand {

	private static final Pattern DICE_PATTERN = Pattern.compile("(\\d+)d(?:(\\d+)-)?(\\d+)");

	private final RolePlayManager rolePlayManager;

	public CmdRollDice(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	@Override
	public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess,
	                     CommandManager.RegistrationEnvironment environment) {
		dispatcher.register(
			literal("rolldice")
				.requires(getRequirements())
				.executes(this::runBaseDice)
				.then(literal("--global")
			        .then(argument("dices", StringArgumentType.greedyString())
		                .executes(this::rollGlobal)))
				.then(argument("dices", StringArgumentType.greedyString())
			        .executes(this))
		);
	}

	@Override
	public Collection<String> getRequiredPermissions() {
		return List.of("roleplay.commands.rolldice");
	}

	public int runBaseDice(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var player = cmdSource.getPlayer();
		return rollDices(player, false, List.of(new Dice(1, 1, 6)));
	}

	public int rollGlobal(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var player = cmdSource.getPlayer();
		var dicesInput = context.getArgument("dices", String.class);
		var dices = parseDicesInput(dicesInput);
		return rollDices(player, true, dices);
	}

	@Override
	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var player = cmdSource.getPlayer();
		var dicesInput = context.getArgument("dices", String.class);
		var dices = parseDicesInput(dicesInput);
		return rollDices(player, false, dices);
	}

	private int rollDices(ServerPlayerEntity player, boolean global, Collection<Dice> dices)
	throws CommandSyntaxException {
		if (dices.size() > 5) {
			var msg = Text.of("You threw too many dices");
			throw new CommandSyntaxException(new SimpleCommandExceptionType(msg), msg);
		}

		var displayer = global ? broadcast(player) : showNearby(player);

		for (Dice dice : dices) {
			var rolls = roll(dice);
			var msg = formatRoll(player, dice, rolls);
			displayer.accept(Text.of(msg));
		}

		return dices.size();
	}

	private String formatRoll(ServerPlayerEntity player, Dice dice, IntArrayList rolls) {
		StringBuilder builder = new StringBuilder(48);

		var sum = rolls.intStream().sum();
		builder.append(player.getEntityName()).append(" rolled ").append(dice).append(" and got ").append(sum);
		if (dice.throwsCount > 1) {
			builder.append("(");
			for (int i = 0; i < rolls.size(); ++i) {
				int roll = rolls.getInt(i);
				if (i != 0) {
					builder.append("+");
				}
				builder.append(roll);
			}
			builder.append(")");
		}


		return builder.toString();
	}

	private Consumer<Text> broadcast(ServerPlayerEntity player) {
		var server = player.getServer();
		assert server != null;
		var playerManager = server.getPlayerManager();

		return (text) -> playerManager.broadcast(text, false);
	}

	private Consumer<Text> showNearby(ServerPlayerEntity player) {
		var world = player.getWorld();
		var players = world.getPlayers().stream()
		                   .filter(p -> p.getBlockPos().isWithinDistance(player.getBlockPos(), rolePlayManager.getDiceDistance()))
		                   .toList();

		return (text) -> players.forEach(p -> p.sendMessage(text, false));
	}

	private Collection<Dice> parseDicesInput(String dicesInput) {
		return Arrays.stream(dicesInput.split(" "))
		        .filter(StringUtils::isNotBlank)
		        .map(this::parseDiceInput)
                .filter(Objects::nonNull)
                .toList();
	}

	private IntArrayList roll(Dice dice) {
		var results = new IntArrayList(dice.throwsCount);

		var random = new Random();
		for (int i = 0 ; i < dice.throwsCount ; i++) {
			int result = random.nextInt(dice.min, dice.max+1);
			results.add(result);
		}

		return results;
	}

	private Dice parseDiceInput(String diceInput) {
		var matcher = DICE_PATTERN.matcher(diceInput);
		if (matcher.find()) {
			int throwsCount = Integer.parseInt(matcher.group(1));
			int max = Integer.parseInt(matcher.group(3));
			var optMin = matcher.group(2);
			int min = optMin == null ? 1 : Integer.parseInt(optMin);
			return new Dice(throwsCount, min, max);
		}
		return null;
	}

	private record Dice(int throwsCount, int min, int max){
		public String toString() {
			return throwsCount + "d" + min + "-" + max;
		}
	}
}
