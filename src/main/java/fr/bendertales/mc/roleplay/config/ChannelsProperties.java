package fr.bendertales.mc.roleplay.config;

public class ChannelsProperties {

	private ChannelProperties say;
	private ChannelProperties yell;
	private ChannelProperties whisper;

	public ChannelProperties getSay() {
		return say;
	}

	public void setSay(ChannelProperties say) {
		this.say = say;
	}

	public ChannelProperties getYell() {
		return yell;
	}

	public void setYell(ChannelProperties yell) {
		this.yell = yell;
	}

	public ChannelProperties getWhisper() {
		return whisper;
	}

	public void setWhisper(ChannelProperties whisper) {
		this.whisper = whisper;
	}

	public void mergeFrom(ChannelsProperties otherProps) {
		if (this.say == null) {
			this.say = otherProps.say;
		}
		else {
			this.say.mergeFrom(otherProps.say);
		}

		if (this.yell == null) {
			this.yell = otherProps.yell;
		}
		else {
			this.yell.mergeFrom(otherProps.yell);
		}

		if (this.whisper == null) {
			this.whisper = otherProps.whisper;
		}
		else {
			this.whisper.mergeFrom(otherProps.whisper);
		}
	}
}
