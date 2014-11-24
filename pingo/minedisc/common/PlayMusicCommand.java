package pingo.minedisc.common;

import java.util.ArrayList;
import java.util.List;

import pingo.minedisc.common.packets.MusicPacket;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;

public class PlayMusicCommand implements ICommand {

	private final List<String> aliases;
	
	public PlayMusicCommand() 
    { 
        aliases = new ArrayList<String>(); 
        aliases.add("playmusic"); 
    } 
	
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "playmusic";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "playmusic <url>";
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
		
		ChunkCoordinates coord = sender.getPlayerCoordinates();
		
		MineDisc.network.sendToDimension(new MusicPacket(args[0], coord.posX, coord.posY, coord.posZ, sender.getEntityWorld().provider.dimensionId, null, "play"), sender.getEntityWorld().provider.dimensionId);
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
