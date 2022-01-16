package com.bendertales.mc.roleplay;

import net.minecraft.util.Identifier;


public class RolePlayConstants {

	public static final String MODID = "tales-roleplay";

	public static final class Ids {

		public static final class Channels {
			public static final Identifier SAY = new Identifier("rp", "say");
			public static final Identifier YELL = new Identifier("rp", "yell");
			public static final Identifier WHISPER = new Identifier("rp", "whisper");
		}

		public static final class Placeholders {
			public static final Identifier CHARACTER = new Identifier("rp", "character");
			public static final Identifier RP_MESSAGE = new Identifier("rp", "message");
		}
	}
}
