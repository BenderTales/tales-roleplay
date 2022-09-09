package fr.bendertales.mc.roleplay.commands.nodes.root;

import java.util.List;

import fr.bendertales.mc.roleplay.impl.RolePlayManager;
import fr.bendertales.mc.roleplay.commands.nodes.character.*;
import fr.bendertales.mc.talesservercommon.commands.AbstractIntermediaryCommandNode;


public class NodeCharacter extends AbstractIntermediaryCommandNode {

	protected NodeCharacter(RolePlayManager rpManager) {
		super(List.of(
			new NodeCreate(rpManager),
			new NodeDelete(rpManager),
			new NodeRename(rpManager),
			new NodeList(rpManager),
			new NodeInfo(rpManager),
			new NodeSelect(rpManager),
			new NodeReadability(rpManager),
			new NodeVisibility(rpManager),
			new NodePerceptionCheck(rpManager)
		));
	}

	@Override
	protected String getName() {
		return "character";
	}
}
