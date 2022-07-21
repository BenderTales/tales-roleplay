package com.bendertales.mc.roleplay.data;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;


public class Newsstand {

	private int id;
	private ServerWorld serverWorld;
	private BlockPos location;
	private int shoutDistance;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ServerWorld getServerWorld() {
		return serverWorld;
	}

	public void setServerWorld(ServerWorld serverWorld) {
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
