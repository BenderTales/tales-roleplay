package fr.bendertales.mc.roleplay.impl.helper;

import org.apache.commons.lang3.StringUtils;


public class Cypher {
	// This is an actually known cypher but since I know some people (:eyes:) are going to come here,
	// I don't name it, so you have to search yourself

	private final String key;

	public Cypher(String key) {
		this.key = normalize(key);
	}

	private String normalize(String text) {
		return StringUtils.stripAccents(text.trim().toUpperCase());
	}

	public String encrypt(String toEncrypt) {
		String text = normalize(toEncrypt);
		var result = new StringBuilder(text.length());

		for (int i = 0, j = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c < 'A' || c > 'Z') {
				if (c == ' ') {
					result.append(c);
				}
				continue;
			}
			result.append((char)((c + key.charAt(j) - 2 * 'A') % 26 + 'A'));
			j = ++j % key.length();
		}

		return result.toString();
	}

	public String decrypt(String toDecrypt) {
		String text = normalize(toDecrypt);
		var result = new StringBuilder(text.length());

		for (int i = 0, j = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c < 'A' || c > 'Z') {
				if (c == ' ') {
					result.append(c);
				}
				continue;
			}
			result.append((char)((c - key.charAt(j) + 26) % 26 + 'A'));
			j = ++j % key.length();
		}

		return result.toString();
	}
}
