package es.santander.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import com.indra.cilantrum.device.config.DeviceConfig;
import com.indra.cilantrum.device.manager.DeviceConfigurator;
import com.indra.cilantrum.framework.config.BuildConfig;
import com.indra.cilantrum.framework.config.CilantrumConfiguration;
import com.indra.cilantrum.framework.main.Main;
import com.indra.cilantrum.report.results.CilantrumResults;

/**
 * Clase main del proyecto de pruebas. Esta clase sera la usada para la
 * ejecucion del proyecto de pruebas funcionales a traves de un Job de Jenkins y
 * esta configurada para ello. Para las ejecuciones en local usense las clase
 * InitChrome, InitFirefox o InitIE; o bien creese otra a parte.
 * 
 * @author mmagallanesa
 *
 */
public class Init {
	// Se define la ruta de la cofiguracion de construccion
	public static String buildPath = "resources/build.properties";

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		// Se carga dicha configuracion
		CilantrumConfiguration.load(buildPath);

		// Se define la configuracion de build y de dispositivo
		BuildConfig buildconfig = CilantrumConfiguration.getBuildConfig();

		String[] devices = buildconfig.getProperty("device.default").split(";");

		for (String aux : devices) {

			DeviceConfig deviceConfig = CilantrumConfiguration.getDeviceConfig(aux);

			// Se imprime por pantalla toda la configuracion del proyecto
			System.out.printf("%-50s %-50s\n", "BUILD CONFIG:", "DEVICE CONFIG:");
			System.out.printf("%-50s %-50s\n", "	Tags: " + buildconfig.getProperty("testplan.tags"),
					"	Driver type: " + deviceConfig.getProperty("driver.type"));
			System.out.printf("%-50s %-50s\n", "	Priority: " + buildconfig.getProperty("testplan.priority"),
					"	Browser tag name: " + deviceConfig.getProperty("driver.capabilities.applicationName"));
			System.out.printf("%-50s %-50s\n", "	Evironment:" + buildconfig.getProperty("exec.environment"),
					"	Headless mode: " + deviceConfig.getProperty("driver.capabilities.headless"));
			System.out.printf("%-50s %-50s\n", "	Parallel mode:" + buildconfig.getProperty("execution.parallel"),
					"	Browser name: " + deviceConfig.getProperty("driver.capabilities.browserName"));
			System.out.printf("%-50s %-50s\n", "	ALM report:" + buildconfig.getProperty("alm.report"),
					"	Browser version: " + deviceConfig.getProperty("driver.capabilities.version"));
			System.out.printf("%-50s %-50s\n", "	ALM domain:" + buildconfig.getProperty("alm.domain"),
					"	Browser platform: " + deviceConfig.getProperty("driver.capabilities.platform"));
			System.out.printf("%-50s %-50s\n", "	ALM project:" + buildconfig.getProperty("alm.project"),
					"	Remote URL: " + deviceConfig.getProperty("driver.url"));

			// Se identifican los dispositivos usados para la prueba
			String deviceDefault = deviceConfig.getProperty("driver.capabilities.browserName").replace("\"", "");

			// Se cargan las opciones correspondientes al dispositivo usado()
			if (deviceDefault.equals("chrome")) {
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);

				ChromeOptions options = new ChromeOptions();
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				// options.addArguments("--headless");
				options.setExperimentalOption("prefs", chromePrefs);
				DeviceConfigurator.newOptions("chrome", options);

			} else if (deviceDefault.equals("firefox")) {
				// FIREFOX CAPABILITIES
				FirefoxOptions options = new FirefoxOptions();
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				// options.addArguments("--headless");

				DeviceConfigurator.newOptions("firefox", options);
			} else if (deviceDefault.equals("internet explorer")) {
				
				InternetExplorerOptions ieCapabilities = new InternetExplorerOptions();
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				// Solucionan la perdida de foco de iexplorer
				ieCapabilities.setCapability("nativeEvents", true);    
				ieCapabilities.setCapability("requireWindowFocus", false);
				ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
				ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
				ieCapabilities.setCapability("disable-popup-blocking", true);
				ieCapabilities.setCapability("enablePersistentHover", true);
				// Soluciona los problemas de cargas de página cuando aparece el dialogo de descarga de iexplorer
				ieCapabilities.setCapability("pageLoadStrategy", "eager");

				DeviceConfigurator.newOptions("iexplorer", ieCapabilities);
			} else {
				System.out.println("¡¡¡Default device not foud!!!");
			}

		}

		CilantrumConfiguration.dispose();

		// Se llama a CilantrumMain para generar el árbol de pruebas e iniciar la
		// ejecución.
		Main.launchBuild(buildPath);

		// Se genera el fichero result.jSon con los resultados de pruebas
		System.out.println("Generated jSon");
		String json = CilantrumResults.getExecutionResultsAsJSon();
		new FileWriter(new File("results.json")).append(json).close();
	}
	
	
	
//	/**
//	 * @author x270098
//	 * 
//	 * Realiza el envio de correo con el reporte y la plantilla
//	 * con los resultados de la ejecucion.
//	 * @throws IOException 
//	 * 
//	 */
//	public static void sendmail() throws IOException {
//		
//		Properties props = new Properties();
//		try {
//			props.load(new FileInputStream(buildPath));
//		} catch (Exception e) {
//			
//		}
//		
//		// 1. Generamos el envio del E-mail.
//        EmailsCilantrum em = new EmailsCilantrum(
//        		props.getProperty("mail.host"),
//        		25,
//        		props.getProperty("mail.user"),
//        		props.getProperty("mail.pwd"),
//        		props.getProperty("mail.ejecucionesmot.to"),
//        		"Ejecuciones MOT");
//        
//        // 2. Adjuntamos el reporte
//        em.attachFile(new File("LastGlobalExecution.html"));
//        
//        // 3. Adjuntamos la plantilla modificada al correo.
//        em.setEmailBody(new File("test-output/email.html"));
//        
//        // 4. Enviamos el correo.
//        em.send();
//	}

	

}
