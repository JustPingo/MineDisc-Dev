package pingo.minedisc.common;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemWifiCD extends Item {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if (stack.stackTagCompound != null) {
			if (stack.stackTagCompound.hasKey("musicURL")) {
				String url = stack.stackTagCompound.getString("musicURL");
				list.add(url.substring(url.lastIndexOf("/")));
			}
		}
	}
	
}
