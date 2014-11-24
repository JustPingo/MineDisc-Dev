package pingo.minedisc.common.packets;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MusicPacket implements IMessage {
	
	public String url;
	public int x;
	public int y;
	public int z;
	public int dimensionID;
	public String playingSource;
	public String action;
	
	public MusicPacket() { }
	
	public MusicPacket(String url, int x, int y, int z, int dimensionID, String playingSource, String action) {
		this.url = url;
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimensionID = dimensionID;
		this.playingSource = playingSource;
		this.action = action;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		url = ByteBufUtils.readUTF8String(buf);		
		x = Integer.parseInt(ByteBufUtils.readUTF8String(buf));
		y = Integer.parseInt(ByteBufUtils.readUTF8String(buf));
		z = Integer.parseInt(ByteBufUtils.readUTF8String(buf));
		dimensionID = Integer.parseInt(ByteBufUtils.readUTF8String(buf));
		playingSource = ByteBufUtils.readUTF8String(buf);
		action = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
        if (this.url == null) this.url = "";
        if (this.playingSource == null) this.playingSource = "";
        System.out.println(this.action);
        if (this.action == null) this.action = "";
        ByteBufUtils.writeUTF8String(buf, url);
        ByteBufUtils.writeUTF8String(buf, Integer.toString(x));
        ByteBufUtils.writeUTF8String(buf, Integer.toString(y));
        ByteBufUtils.writeUTF8String(buf, Integer.toString(z));
        ByteBufUtils.writeUTF8String(buf, Integer.toString(dimensionID));
        ByteBufUtils.writeUTF8String(buf, playingSource);
        ByteBufUtils.writeUTF8String(buf, action);
	}
	
}
