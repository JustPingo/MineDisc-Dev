package pingo.minedisc.common.packets;

import java.util.Map.Entry;

import pingo.minedisc.common.MusicManager;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MusicPacketHandler implements IMessageHandler<MusicPacket, IMessage> {

	public MusicPacketHandler() { }
	
	@Override
	public IMessage onMessage(MusicPacket message, MessageContext ctx) {
        System.out.println(message.action);
		if (message.action.equals("play")) {
			for(Entry<String, int[]> entry : MusicManager.playingMusics.entrySet()) {
			    int[] value = entry.getValue();
			    if (value[0] == message.x && value[1] == message.y && value[2] == message.z) {
					MusicManager.stopMusic(entry.getKey());
			    }
			}
			System.out.println("Play!");
			MusicManager.playingMusics.put(MusicManager.playMusic(message.url, message.x, message.y, message.z, 1), new int[] { message.x, message.y, message.z });
		} else if (message.action.equals("stop")) {
			for(Entry<String, int[]> entry : MusicManager.playingMusics.entrySet()) {
			    int[] value = entry.getValue();
			    if (value[0] == message.x && value[1] == message.y && value[2] == message.z) {
					MusicManager.stopMusic(entry.getKey());
			    }
			}
		}
		return null;
	}
	
}
