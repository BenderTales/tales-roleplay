package fr.bendertales.mc.roleplay.commands.nodes.root;

import java.util.List;

import fr.bendertales.mc.roleplay.commands.nodes.rp.NodeCrypt;
import fr.bendertales.mc.roleplay.commands.nodes.rp.NodeReload;
import fr.bendertales.mc.roleplay.impl.RolePlayManager;
import fr.bendertales.mc.talesservercommon.commands.AbstractIntermediaryCommandNode;


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
