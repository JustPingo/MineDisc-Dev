package pingo.minedisc.common;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

public class SetStackCommand implements ICommand {

	private final List<String> aliases;
	
	public SetStackCommand() 
    { 
        aliases = new ArrayList<String>(); 
        aliases.add("setstack"); 
    } 
	
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "setstack";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "setstack <url>";
	}

	@Override
	public List<String> getCommandAliases() {
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.addChatMessage(new ChatComponentText("Invalid arguments."));
			return;
	    }
		
		sender.addChatMessage(new ChatComponentText(args[0]));
		
		ItemStack stack = sender.getEntityWorld().getPlayerEntityByName(sender.getCommandSenderName()).getHeldItem();
		if (stack != null) {
			if (stack.getItem() == MineDisc.wifiCD) {
				if (stack.stackTagCompound == null) stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setString("musicURL", args[0]);
			}
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		return true;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}

}
