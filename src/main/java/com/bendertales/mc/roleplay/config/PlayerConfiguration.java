package com.bendertales.mc.roleplay.config;

import java.util.List;

import com.bendertales.mc.roleplay.impl.vo.RolePlayException;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;


public class PlayerConfiguration {

	private final List<CharacterProperties> characters = new ObjectArrayList<>();
	private int selectedCharacterIndex = 0;

	public List<CharacterProperties> getCharacters() {
		return characters;
	}

	public void selectCharacterIndex(int characterIndex) throws RolePlayException {
		checkIndex(characterIndex);
		this.selectedCharacterIndex = characterIndex;
	}

	public boolean hasCharacter() {
		return !characters.isEmpty();
	}

	public CharacterProperties getSelectedCharacter() {
		return characters.get(selectedCharacterIndex);
	}

	public void renameCharacter(int characterIndex, String newName) throws RolePlayException {
		checkIndex(characterIndex);
		var character = characters.get(characterIndex);
		character.setName(newName);
	}

	private void checkIndex(int characterIndex) throws RolePlayException {
		if (characterIndex < 0 || characterIndex > characters.size()-1) {
			throw new RolePlayException("There is no character for this index");
		}
	}

	public void removeCharacter(int characterIndex) throws RolePlayException {
		checkIndex(characterIndex);
		characters.remove(characterIndex);
	}
}
