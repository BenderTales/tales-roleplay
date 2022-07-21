package com.bendertales.mc.roleplay.impl.channels;

import java.util.Collection;
import java.util.Collections;

import com.bendertales.mc.roleplay.RolePlayConstants;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import net.minecraft.util.Identifier;


public class SayChannel extends AbstractDistanceChannel {

	public static final Collection<String> PERMISSION = Collections.singleton("roleplay.channels.say");

	private final RolePlayManager rolePlayManager;

	public SayChannel(RolePlayManager rolePlayManager) {
		super(PERMISSION);
		this.rolePlayManager = rolePlayManager;
	}

	@Override
	public String getDefaultFormat() {
		return "%CHARACTER%: %RP_MESSAGE%";
	}

	@Override
	protected int getDistance() {
		return rolePlayManager.getSayDistance();
	}

	@Override
	public String getPrefixSelector() {
		return ":";
	}

	@Override
	public Identifier getId() {
		return RolePlayConstants.Ids.Channels.SAY;
	}
}
