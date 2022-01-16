package com.bendertales.mc.roleplay.impl.channels;

import java.util.Collection;
import java.util.Collections;

import com.bendertales.mc.roleplay.RolePlayConstants;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import net.minecraft.util.Identifier;


public class WhisperChannel extends AbstractDistanceChannel {

	public static final Collection<String> PERMISSION = Collections.singleton("roleplay.channels.whisper");

	private final RolePlayManager rolePlayManager;

	public WhisperChannel(RolePlayManager rolePlayManager) {
		super(PERMISSION);
		this.rolePlayManager = rolePlayManager;
	}

	@Override
	public String getDefaultFormat() {
		return "%CHARACTER%: %MESSAGE%";
	}

	@Override
	public String getPrefixSelector() {
		return "_";
	}

	@Override
	public Identifier getId() {
		return RolePlayConstants.Ids.Channels.WHISPER;
	}

	@Override
	protected int getDistance() {
		return rolePlayManager.getWhisperDistance();
	}
}
