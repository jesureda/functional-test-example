package es.santander.requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.indra.cilantrum.webservices.WebServices;
import com.indra.cilantrum.webservices.ReqResponse;
import com.indra.cilantrum.messages.MessageLibrary;
import com.mashape.unirest.http.HttpResponse;

import io.restassured.http.ContentType;

public class Delete {
	public static HttpResponse<String> response;
	public static String result;
	public static ReqResponse response02;
	private WebServices ws = new WebServices();
	public ContentType contentType = null;
	public ArrayList<String> fieldHeader = new ArrayList<String>();
	public ArrayList<String> valueHeader = new ArrayList<String>();

	// Map headers.
	Map<String, String> mapHeader = new HashMap<String, String>();

	public Delete() {
	}

	/**
	 * This method takes care of making a DELETE request.
	 * 
	 * @param endpoint
	 * @param headerGet
	 * @param statusCode
	 * @param requestContenType
	 * @return
	 * @throws Exception
	 */
	public ReqResponse executeDelete(String endpoint, String[] header, String statusCode, String requestContenType)
			throws Exception {

		try {
			// Step - Title.
			MessageLibrary.log("Se va a realizar un DELETE con la URL: <font color='Teal'>" + endpoint + "</font>");

			// Extract headers.
			for (int i = 0; i < header.length; i++) {
				String[] valueHeaders = header[i].split(Final.EQUALS, 2);

				// We apply the type of contentType that our request will use.
				if (valueHeaders[0].equals(Final.CONTENTTYPE)) {
					String key = valueHeaders[1].replaceAll("[\\p{Ps}\\p{Pe}]", "");

					// Extract the formatted contentType.
					contentType = Browser.setContentType(key);
				}

				// Add field (key) and value, reporter log.
				fieldHeader.add(valueHeaders[0].replaceAll("[\\p{Ps}\\p{Pe}]", ""));
				valueHeader.add(valueHeaders[1].replaceAll("[\\p{Ps}\\p{Pe}]", ""));

				// Map Headers.
				mapHeader.put(valueHeaders[0], valueHeaders[1]);
			}

			// DELETE.
			response02 = ws.createRestRequest().contentType(contentType).setHeaders(mapHeader).delete(endpoint);

			// Validate ContentType.
			Browser.reportContentType(requestContenType, response02);

			// Validate statusCode.
			Browser.reportStatusCode(Integer.parseInt(statusCode), response02);

			// Result - Title.
			MessageLibrary.log(
					"Se ha realizado y validado el DELETE con la URL: <font color='Teal'>" + endpoint + "</font>");

		} catch (Exception e) {
			// New exception.
			throw new Exception("al realizar el DELETE: <font color='red'>" + e + "</font>.");
		}
		return response02;
	}
}