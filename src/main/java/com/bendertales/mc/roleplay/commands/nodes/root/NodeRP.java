package com.bendertales.mc.roleplay.commands.nodes.root;

import java.util.List;

import com.bendertales.mc.roleplay.commands.nodes.rp.NodeCrypt;
import com.bendertales.mc.roleplay.commands.nodes.rp.NodeReload;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.bendertales.mc.talesservercommon.commands.AbstractIntermediaryCommandNode;


public class NodeRP extends AbstractIntermediaryCommandNode {

	public NodeRP(RolePlayManager rpManager) {
		super(List.of(
			new NodeCharacter(rpManager),
			new NodeReload(rpManager),
			new NodeCrypt(rpManager)
		));
	}

	@Override
	protected String getName() {
		return "rp";
	}
}
