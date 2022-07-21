package com.bendertales.mc.roleplay.impl.channels;

import java.util.Collection;
import java.util.Collections;

import com.bendertales.mc.roleplay.RolePlayConstants;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import net.minecraft.util.Identifier;


public class YellChannel extends AbstractDistanceChannel {

	public static final Collection<String> PERMISSION = Collections.singleton("roleplay.channels.yell");

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
