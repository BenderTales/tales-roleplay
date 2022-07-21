package com.bendertales.mc.roleplay.config;

import java.util.Objects;


public class ModProperties {

	private RollProperties rollDice;
	private NewsProperties news;
	private MeProperties me;
	private ChannelsProperties channels;
	private String comprehensionKey = "BENDER";

	public RollProperties getRollDice() {
		return rollDice;
	}

	public void setRollDice(RollProperties rollDice) {
		this.rollDice = rollDice;
	}

	public NewsProperties getNews() {
		return news;
	}

	public void setNews(NewsProperties news) {
		this.news = news;
	}

	public MeProperties getMe() {
		return me;
	}

	public void setMe(MeProperties me) {
		this.me = me;
	}

	public ChannelsProperties getChannels() {
		return channels;
	}

	public void setChannels(ChannelsProperties channels) {
		this.channels = channels;
	}

	public String getComprehensionKey() {
		return comprehensionKey;
	}

	public void setComprehensionKey(String comprehensionKey) {
		this.comprehensionKey = comprehensionKey;
	}

	public void mergeFrom(ModProperties otherConfig) {
		if (this.rollDice == null) {
			this.rollDice = otherConfig.rollDice;
		}
		else {
			this.rollDice.mergeFrom(otherConfig.rollDice);
		}

		if (this.me == null) {
			this.me = otherConfig.me;
		}
		else {
			this.me.mergeFrom(otherConfig.me);
		}

		if (this.news == null) {
			this.news = otherConfig.news;
		}
		else {
			this.news.mergeFrom(otherConfig.news);
		}

		if (this.channels == null) {
			this.channels = otherConfig.channels;
		}
		else {
			this.channels.mergeFrom(otherConfig.channels);
		}

		if (this.comprehensionKey == null) {
			this.comprehensionKey = otherConfig.comprehensionKey;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		ModProperties that = (ModProperties) o;
		return Objects.equals(rollDice, that.rollDice)
		       && Objects.equals(news, that.news)
		       && Objects.equals(me, that.me)
		       && Objects.equals(comprehensionKey, that.comprehensionKey);
	}

	@Override
	public int hashCode() {
		return Objects.hash(rollDice, news, me, comprehensionKey);
	}
}
