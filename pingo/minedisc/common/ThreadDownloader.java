package pingo.minedisc.common;

import net.minecraft.tileentity.TileEntity;

public class ThreadDownloader extends Thread {
	
	private String musicURL;
	private TileEntity displayer;
	
	public ThreadDownloader(String musicURL, TileEntity displayer) {
		this.musicURL = musicURL;
		this.displayer = displayer;
	}
	
	public void run() {
		MusicManager.updatePlaying(this.musicURL, this.displayer);
	}
	
}
