
package es.santander.main;

import java.io.IOException;

import com.indra.cilantrum.framework.main.Main;

public class Init_local {

	public static String buildPath = "resources/build_local.properties";

	public static void main(String[] args) throws IOException {

		// START
		Main.launchBuild(buildPath);

	}

}