package pingo.minedisc.client;

import pingo.minedisc.common.CommonProxy;
import pingo.minedisc.common.MusicManager;

public class ClientProxy extends CommonProxy {

	@Override
	public String getSide() {
		return "client";
	}
	
	@Override
	public void registerRender() {
		
	}
	
	@Override
	public void updateSoundSystem() {
		MusicManager.updateSoundSystem();
	}
	
}
