package com.mcfadden.kitepro.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mcfadden.kitepro.KitePro;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 1080;
		config.width = 1920;
		config.fullscreen = true;
		config.forceExit = true;

		new LwjglApplication(new KitePro(), config);
	}
}
