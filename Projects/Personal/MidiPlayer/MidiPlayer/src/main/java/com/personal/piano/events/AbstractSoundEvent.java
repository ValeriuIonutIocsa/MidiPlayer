package com.personal.piano.events;

import com.utils.string.StrUtils;

public abstract class AbstractSoundEvent implements SoundEvent {

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
