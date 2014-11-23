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
import paulscode.sound.SoundSystem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
	
public class MusicManager {
	
	private static SoundSystem mcSndSystem;
	
	private static Map<String, String> loadedMusics = new HashMap<String, String>();

	private final static String[][] acceptedTypes = new String[][] { new String[] { "audio/x-wav", ".wav" } };
	
	@SideOnly(Side.CLIENT)
	public static void updateSoundSystem() {
		try {
			Field sndSystemField = Minecraft.getMinecraft().getSoundHandler().getClass().getDeclaredField("sndManager").getClass().getDeclaredField("sndSystem");
			sndSystemField.setAccessible(true); // You should never use those 3 lines. That's super dangerous and heavy.
			sndSystemField.get(MusicManager.mcSndSystem);
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
				if (type == row[0]) {
					String identifier = UUID.randomUUID().toString() + row[1];
					MusicManager.mcSndSystem.loadSound(url, identifier);
					MusicManager.loadedMusics.put(urlToReach, identifier);
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
			String key = entry.getKey();
			if (key == urlToReach) return true;
		}
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public static void unloadMusic(String urlToReach) {
		for (Iterator<Map.Entry<String, String>> it = MusicManager.loadedMusics.entrySet().iterator(); it.hasNext();) {
		    Map.Entry<String, String> entry = it.next();
		    if (entry.getKey() == urlToReach) { MusicManager.mcSndSystem.unloadSound(entry.getValue()); it.remove(); break; }
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void playMusic(String urlToReach, int x, int y, int z, int volume) {
		
			MusicManager.mcSndSystem.quickPlay(false, filename, toLoop, x, y, z, attmodel, distOrRoll)
		
	}
	
}
