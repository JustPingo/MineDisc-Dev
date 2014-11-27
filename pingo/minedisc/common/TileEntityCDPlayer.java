package pingo.minedisc.common;

import pingo.minedisc.common.packets.MusicPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCDPlayer extends TileEntity {
	
	private String currentMusicURL = "";
	private boolean wasRunningMusic = false;
	
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {

        this.readFromNBT(pkt.func_148857_g());
        
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);

    }
	
	public Packet getDescriptionPacket() {

        NBTTagCompound nbttagcompound = new NBTTagCompound();

        this.writeToNBT(nbttagcompound);

        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 3, nbttagcompound);

    }
	
	public void readFromNBT(NBTTagCompound nbttag) {
        super.readFromNBT(nbttag);
        
        if (nbttag.hasKey("currentMusicURL")) this.currentMusicURL = nbttag.getString("currentMusicURL");
    }

    public void writeToNBT(NBTTagCompound nbttag) {
        super.writeToNBT(nbttag);
        
        nbttag.setString("currentMusicURL", this.currentMusicURL);
    }
    
    public void onBlockActivated(EntityPlayer player) {
    	ItemStack itemInUse = player.getHeldItem();
	    if (itemInUse != null) {
    		if (itemInUse.getItem() == MineDisc.wifiCD) {
	    		if (itemInUse.stackTagCompound != null) {
	    			if (itemInUse.stackTagCompound.hasKey("musicURL")) {
	    				this.currentMusicURL = itemInUse.stackTagCompound.getString("musicURL");
	    			}
	    		}
	    	}
    	}
    }

	public void onNeighborBlockChange() {
		if (!worldObj.isRemote && worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && currentMusicURL != "") {
			if (!wasRunningMusic) {
				MineDisc.network.sendToDimension(new MusicPacket(currentMusicURL, xCoord, yCoord, zCoord, worldObj.provider.dimensionId, "play"), worldObj.provider.dimensionId);
				wasRunningMusic = true;
			}
		} else {
			if (wasRunningMusic) {
				MineDisc.network.sendToDimension(new MusicPacket(currentMusicURL, xCoord, yCoord, zCoord, worldObj.provider.dimensionId, "stop"), worldObj.provider.dimensionId);
				wasRunningMusic = false;
			}
		}
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, MineDisc.CDPlayer);
	}
	
}
