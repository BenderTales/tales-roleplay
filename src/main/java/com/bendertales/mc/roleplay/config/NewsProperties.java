package com.bendertales.mc.roleplay.config;

import java.util.Objects;


public class NewsProperties {

	private int distance;
	private String format;

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void mergeFrom(NewsProperties otherProps) {
		if (this.distance <= 0) {
			this.distance = otherProps.distance;
		}

		if (this.format == null) {
			this.format = otherProps.format;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		NewsProperties that = (NewsProperties) o;
		return distance == that.distance && Objects.equals(format, that.format);
	}

	@Override
	public int hashCode() {
		return Objects.hash(distance, format);
	}
}
