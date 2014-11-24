package pingo.minedisc.common;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
	
public class MusicManager {
	
	private static SoundSystem mcSndSystem;
	
	private static Map<String, String> loadedMusics = new HashMap<String, String>();

	private final static String[][] acceptedTypes = new String[][] { new String[] { "audio/x-wav", ".wav" } };
	
	@SideOnly(Side.CLIENT)
	public static void updateSoundSystem() { // You should never copy this method. It's super dangerous and heavy.
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
	
	@SideOnly(Side.CLIENT)
	public static void loadMusic(String urlToReach) {
		try {
			MusicManager.unloadMusic(urlToReach);
			URL url = new URL(urlToReach);
			String type = url.openConnection().getContentType();
			for (String[] row : MusicManager.acceptedTypes ) {
				System.out.println("Model : " + row[0] + " comparing to " + type);
				if (type != null) {
					if (type.equals(row[0])) {
						String identifier = UUID.randomUUID().toString() + row[1];
						System.out.println("Music loaded as " + identifier);
						MusicManager.mcSndSystem.loadSound(url, identifier);
						MusicManager.loadedMusics.put(urlToReach, identifier);
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isLoaded(String urlToReach) {
		for (Map.Entry<String, String> entry : MusicManager.loadedMusics.entrySet()) {
			if (entry.getKey().equals(urlToReach)) return true;
		}
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public static void unloadMusic(String urlToReach) {
		for (Iterator<Map.Entry<String, String>> it = MusicManager.loadedMusics.entrySet().iterator(); it.hasNext();) {
		    Map.Entry<String, String> entry = it.next();
		    if (entry.getKey().equals(urlToReach)) { MusicManager.mcSndSystem.unloadSound(entry.getValue()); it.remove(); break; }
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static String playMusic(String urlToReach, int x, int y, int z, float volume) {
		for (Map.Entry<String, String> entry : MusicManager.loadedMusics.entrySet()) {
			if (entry.getKey().equals(urlToReach)) {
				System.out.println("Loading music " + entry.getValue());
				String source = MusicManager.mcSndSystem.quickPlay(false, entry.getValue(), false, x, y, z, SoundSystemConfig.ATTENUATION_LINEAR, 16);
				MusicManager.mcSndSystem.setVolume(source, volume);
				return source;
			}
		}
		return null;
	}
	
	@SideOnly(Side.CLIENT)
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
	}
	
	@SideOnly(Side.CLIENT)
	public static void setVolume(String source, float volume) {
		MusicManager.mcSndSystem.setVolume(source, volume);
	}
	
	@SideOnly(Side.CLIENT)
	public static void stopMusic(String source) {
		MusicManager.mcSndSystem.stop(source);
	}
	
}
