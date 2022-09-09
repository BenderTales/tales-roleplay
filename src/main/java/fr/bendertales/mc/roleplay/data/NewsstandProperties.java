package fr.bendertales.mc.roleplay.data;

import net.minecraft.util.math.BlockPos;


public class NewsstandProperties {

	private int         id;
	private String serverWorld;
	private BlockPos    location;
	private int         shoutDistance;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServerWorld() {
		return serverWorld;
	}

	public void setServerWorld(String serverWorld) {
		this.serverWorld = serverWorld;
	}

	public BlockPos getLocation() {
		return location;
	}

	public void setLocation(BlockPos location) {
		this.location = location;
	}

	public int getShoutDistance() {
		return shoutDistance;
	}

	public void setShoutDistance(int shoutDistance) {
		this.shoutDistance = shoutDistance;
	}
}
