package pingo.minedisc.common.packets;

import pingo.minedisc.common.TileEntityCDPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MusicPacketResponseHandler implements IMessageHandler<MusicPacketResponse, IMessage> {

	public MusicPacketResponseHandler() { }
	
	@Override
	public IMessage onMessage(MusicPacketResponse message, MessageContext ctx) {
		TileEntity te = MinecraftServer.getServer().worldServers[message.dimensionID].getTileEntity(message.x, message.y, message.z);
		if (te instanceof TileEntityCDPlayer) ((TileEntityCDPlayer) te).playingSource = message.playingSource;
		return null;
	}
	
}
