package com.bendertales.mc.roleplay.config;

public class ChannelProperties {

	private int distance;

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void mergeFrom(ChannelProperties otherProps) {
		if (this.distance <= 0) {
			this.distance = otherProps.distance;
		}
	}
}
