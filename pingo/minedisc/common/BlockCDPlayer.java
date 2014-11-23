package pingo.minedisc.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCDPlayer extends BlockContainer {

	protected BlockCDPlayer(Material material) {
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		return new TileEntityCDPlayer();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityCDPlayer) ((TileEntityCDPlayer) te).onBlockActivated(player);
		return true;
	}
	
	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityCDPlayer) ((TileEntityCDPlayer) te).onNeighborBlockChange();
	}

}
