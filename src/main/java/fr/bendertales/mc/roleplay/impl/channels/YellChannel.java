package fr.bendertales.mc.roleplay.impl.channels;

import java.util.List;

import fr.bendertales.mc.roleplay.RolePlayConstants;
import fr.bendertales.mc.roleplay.impl.RolePlayManager;
import net.minecraft.util.Identifier;


public class YellChannel extends AbstractDistanceChannel {

	public static final List<String> PERMISSION = List.of("roleplay.channels.yell");

	private final RolePlayManager rolePlayManager;

	public YellChannel(RolePlayManager rolePlayManager) {
		super(PERMISSION);
		this.rolePlayManager = rolePlayManager;
	}

	@Override
	public String getDefaultFormat() {
		return "%CHARACTER%: %RP_MESSAGE%";
	}

	@Override
	public String getPrefixSelector() {
		return null;
	}

	@Override
	public Identifier getId() {
		return RolePlayConstants.Ids.Channels.YELL;
	}

	@Override
	protected int getDistance() {
		return rolePlayManager.getYellDistance();
	}
}
