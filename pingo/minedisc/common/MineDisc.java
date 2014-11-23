package pingo.minedisc.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = MineDisc.MODID, version = MineDisc.VERSION, name = MineDisc.NAME)
public class MineDisc
{
    public static final String MODID = "minedisc";
    public static final String NAME = "MineDisc";
    public static final String VERSION = "0.1.0";
    
    @Instance(MineDisc.MODID)
	public static MineDisc instance;
    
    @SidedProxy(clientSide = "pingo.minedisc.client.ClientProxy", serverSide = "pingo.minedisc.common.CommonProxy")
	public static CommonProxy proxy;
    
    public static Block CDPlayer;
    
    public static Item wifiCD;

    public static CreativeTabs minediscCT = new CreativeTabs("tabName") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Item.getItemFromBlock(MineDisc.CDPlayer);
        }
    };
        
	private void blockRegister() {
		CDPlayer = new BlockCDPlayer(Material.iron).setBlockName("CDPlayer").setHardness(3.0F).setCreativeTab(minediscCT);
		
		GameRegistry.registerBlock(CDPlayer, "cd_player");
	}
	
	private void itemRegister() {
		wifiCD = new ItemWifiCD().setUnlocalizedName("WiFiCD").setTextureName(MineDisc.MODID + ":wificd").setCreativeTab(minediscCT);
		
		GameRegistry.registerItem(wifiCD, "wifi_cd");
	}
	
	private void tileEntitiesRegister() {
		GameRegistry.registerTileEntity(TileEntityCDPlayer.class, "TileEntityCDPlayer");
	}
	
	private void craftingRegister() {
		
	}
	
	private void entityRegister() {
		
	}
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	itemRegister();
		blockRegister();
		tileEntitiesRegister();
		entityRegister();
    }

	@EventHandler
    public void init(FMLInitializationEvent event)
    {
    	craftingRegister();
    	proxy.registerRender();
    }
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event){
		event.registerServerCommand(new SetStackCommand());
	}

	@EventHandler
    public void postInit(FMLPostInitializationEvent event) {

	}
	
	@SuppressWarnings("unchecked")
	public void registerEntity(@SuppressWarnings("rawtypes") Class entity, String name) {
		int entityID = EntityRegistry.findGlobalUniqueEntityId();
		
		EntityRegistry.registerGlobalEntityID(entity, name, entityID);
		EntityRegistry.registerModEntity(entity, name, entityID, instance, 64, 1, true);
	}

}
