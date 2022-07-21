package com.bendertales.mc.roleplay.impl.channels;

import java.util.Collection;
import java.util.function.Predicate;

import com.bendertales.mc.chatapi.api.MessageVisibility;
import com.bendertales.mc.chatapi.api.ModChannelImplementationsProvider;
import com.bendertales.mc.chatapi.api.RecipientFilter;
import com.bendertales.mc.roleplay.impl.helper.Perms;
import net.minecraft.server.network.ServerPlayerEntity;


public abstract class  AbstractDistanceChannel implements ModChannelImplementationsProvider {

	private final Collection<String> requiredPermissions;

	public AbstractDistanceChannel(Collection<String> requiredPermissions) {
		this.requiredPermissions = requiredPermissions;
	}

	@Override
	public Predicate<ServerPlayerEntity> getSenderFilter() {
		return (player) -> Perms.isOp(player) || Perms.hasAny(player, requiredPermissions);
	}

	@Override
	public RecipientFilter getRecipientsFilter() {
		return (sender, recipient, options) -> {
			if (sender.equals(recipient)) {
				return MessageVisibility.SHOW;
			}
			if (Perms.isOp(recipient) || Perms.hasAny(recipient, requiredPermissions)) {
				if (sender.getWorld().equals(recipient.getWorld())
						    && sender.getBlockPos().isWithinDistance(recipient.getBlockPos(), getDistance())) {
					return MessageVisibility.SHOW;
				}
				if (options.isRecipientSocialSpy()) {
					return MessageVisibility.SOCIAL_SPY;
				}
			}
			return MessageVisibility.HIDE;
		};
	}

	protected abstract int getDistance();
}
