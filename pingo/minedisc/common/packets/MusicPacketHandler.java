package pingo.minedisc.common.packets;

import pingo.minedisc.common.MusicManager;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MusicPacketHandler implements IMessageHandler<MusicPacket, MusicPacketResponse> {

	public MusicPacketHandler() { }
	
	@Override
	public MusicPacketResponse onMessage(MusicPacket message, MessageContext ctx) {
        System.out.println(message.action);
		if (message.action.equals("play")) {
			System.out.println("Play!");
			if (MusicManager.isLoaded(message.url)) return new MusicPacketResponse(MusicManager.playMusic(message.url, message.x, message.y, message.z, 1), message.x, message.y, message.z, message.dimensionID);
			else {
				MusicManager.loadMusic(message.url);
				return new MusicPacketResponse(MusicManager.playMusic(message.url, message.x, message.y, message.z, 1), message.x, message.y, message.z, message.dimensionID);
			}
		} else if (message.action.equals("stop")) {
			if (message.playingSource != "") MusicManager.stopMusic(message.playingSource);
			message.playingSource = "";
		}
		return null;
	}
	
}
