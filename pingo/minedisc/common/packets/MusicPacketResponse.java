package pingo.minedisc.common.packets;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MusicPacketResponse implements IMessage {

	public String playingSource;
	public int x;
	public int y;
	public int z;
	public int dimensionID;

	public MusicPacketResponse() { }
	
	public MusicPacketResponse(String source, int x, int y, int z, int dimensionID) {
		this.playingSource = source;
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimensionID = dimensionID;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		playingSource = ByteBufUtils.readUTF8String(buf);
		x = Integer.parseInt(ByteBufUtils.readUTF8String(buf));
		y = Integer.parseInt(ByteBufUtils.readUTF8String(buf));
		z = Integer.parseInt(ByteBufUtils.readUTF8String(buf));
		dimensionID = Integer.parseInt(ByteBufUtils.readUTF8String(buf));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if (this.playingSource == null) this.playingSource = "";
        ByteBufUtils.writeUTF8String(buf, playingSource);
		ByteBufUtils.writeUTF8String(buf, Integer.toString(x));
        ByteBufUtils.writeUTF8String(buf, Integer.toString(y));
        ByteBufUtils.writeUTF8String(buf, Integer.toString(z));
        ByteBufUtils.writeUTF8String(buf, Integer.toString(dimensionID));
	}

}
