package pingo.minedisc.common;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
	
public class MusicManager {
	
	private static SoundSystem mcSndSystem;
	
	private final static String[][] acceptedTypes = new String[][] { new String[] { "audio/ogg", ".ogg" } };
	
	public static HashMap<String, int[]> playingMusics = new HashMap<String, int[]>();
	
	public static void updateSoundSystem() { // You should never copy this method. It's super dangerous and heavy.
		if (MusicManager.mcSndSystem == null) {
			try {
				SoundHandler sndHandler = Minecraft.getMinecraft().getSoundHandler();
				Field sndManagerField = sndHandler.getClass().getDeclaredField("sndManager");
				sndManagerField.setAccessible(true);
				SoundManager mcSndManager = (SoundManager) sndManagerField.get(sndHandler);
				
				Field sndSystemField = mcSndManager.getClass().getDeclaredField("sndSystem");
				sndSystemField.setAccessible(true);
				MusicManager.mcSndSystem = (SoundSystem) sndSystemField.get(mcSndManager);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String playMusic(String urlToReach, int x, int y, int z, float volume) {
		if (MineDisc.proxy.getSide() == "client") {
			MusicManager.updateSoundSystem();
			try {
				URL url = new URL(urlToReach);
				String type = url.openConnection().getContentType();
				for (String[] row : MusicManager.acceptedTypes ) {
					System.out.println("Model : " + row[0] + " comparing to " + type);
					if (type != null) {
						if (type.equals(row[0])) {
							String identifier = UUID.randomUUID().toString() + row[1];
							return MusicManager.mcSndSystem.quickStream(false, new URL(urlToReach), identifier, false, x, y, z, SoundSystemConfig.ATTENUATION_LINEAR, 16);
						}	
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/*@SideOnly(Side.CLIENT)
	public static String playMusicRaw(String urlToReach, int x, int y, int z, float volume) {
		//String source = MusicManager.mcSndSystem.quickPlay(false, new URL(urlToReach), false, x, y, z, SoundSystemConfig.ATTENUATION_LINEAR, 16);
		try {
			String source = MusicManager.mcSndSystem.quickPlay(false, new URL(urlToReach), "test.wav", false, x, y, z, SoundSystemConfig.ATTENUATION_LINEAR, 16);
			MusicManager.mcSndSystem.setVolume(source, volume);
			return source;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	public static void setVolume(String source, float volume) {
		MusicManager.mcSndSystem.setVolume(source, volume);
	}
	
	public static void stopMusic(String source) {
		try {
			MusicManager.mcSndSystem.stop(source);
		} catch (Exception e) {
			
		}
	}
	
}
