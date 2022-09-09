package fr.bendertales.mc.roleplay.config;

import java.util.Objects;


public class MeProperties {

	private int    distance;
	private String color;

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void mergeFrom(MeProperties otherProps) {
		if (this.distance <= 0) {
			this.distance = otherProps.distance;
		}

		if (this.color == null) {
			this.color = otherProps.color;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		MeProperties that = (MeProperties) o;
		return distance == that.distance && Objects.equals(color, that.color);
	}

	@Override
	public int hashCode() {
		return Objects.hash(distance, color);
	}
}
