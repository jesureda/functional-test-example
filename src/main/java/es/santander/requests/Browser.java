package es.santander.requests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;

import com.indra.cilantrum.encr.Accesser;
import com.indra.cilantrum.framework.api.TestData;
import com.indra.cilantrum.webservices.ReqResponse;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.indra.cilantrum.messages.MessageLibrary;

import io.restassured.http.ContentType;

public class Browser {
	// Variables.
	public static Pattern pattern;
	public static String result;
	public static ArrayList<String> arrayFieldFormat = new ArrayList<String>();
	public static ArrayList<String> resultArray = new ArrayList<String>();
	static ContentType contentType = null;
	public static String[] fieldExitValue = null;
	public static String[] arrayResponseFieldValidation = null;
	public static String[] arrayResponseHeaderValidation = null;
	static ReqResponse response = null;
	static String[] header = null;
	static Map<String, String> resultLaunchTC = null;
	static String url = "";
	static String statusCode = "";
	static String contentTypeLaunchTC = "";
	static String bodyRequest = "";
	static String key = "";
	static Boolean isDependency = false;
	static LinkedList<String> testCaseDependency = null;
	static boolean isPATCH = false;
	public static Map<String,String> map = new HashMap<String,String>();
	// Private constructor is necessary (Sonar).
	public Browser() {
	}

	/**
	 * This method is in charge of showing in the report the parameters that are
	 * being introduced in the request.
	 * 
	 * @param headerField
	 * @param headerValue
	 * @throws Exception
	 */
	public static void reportParamsHeader(ArrayList<String> headerField, ArrayList<String> headerValue)
			throws Exception {
		try {
			// Get report msg.
			String reportMsg = "Datos/ficheros usados en la petición:";

			reportMsg += "<div class=\"relative-container\" style=\"position:relative\"><span class=\"plus-stacktrace\" style=\"left: 7em;top:-30px\" data-content=\"+\"></span><p><u><b>Info: </b></u></p>"
					+ "<div class=\"hide\" style=\"font-size: 0.85rem; margin: 5px 15px; display: none;\">";

			// We mount the report message header.
			for (int i = 0; i < headerField.size(); i++) {
				// Adding date.
				reportMsg += "<p><b> - " + headerField.get(i) + " : </b><font color='Teal'>" + headerValue.get(i)
						+ "</font></p>";
			}

			reportMsg += "</div></div>";

			// Report log.
			MessageLibrary.log(reportMsg);

		} catch (Exception e) {
			throw new Exception(" no se puede reportar los datos de entrada: Detalle del error: <font color='red'>" + e
					+ "</font>");
		}
	}

	/**
	 * This method is in charge of checking/validating that the returned response.
	 * 
	 * @param partsVerify
	 * @param response
	 * @throws Exception
	 */
	public static void validateResult(String[] partsVerify, ReqResponse response) throws Exception {
		// Variables.
		String field = "";
		String value = "";

		try {
			// Extract headers.
			for (int i = 0; i < partsVerify.length; i++) {
				String[] valueVerify = partsVerify[i].split(Final.EQUALS, 2);

				// Extract data.
				field = valueVerify[0];
				value = valueVerify[1];

				// Extract data.
				result = getDataString(field, response).replaceAll("[\\p{Ps}\\p{Pe}]", "");

				// Step - Title.
				MessageLibrary.log("Se va a validar que la respuesta retorne el campo: '" + field
						+ "' y que el valor retornado sea igual o contenga la siguiente cadena: '" + value + "'");

				// First it matches results, if this is missing it tries to check if it contains
				// the string.
				// Validate data.
				if (!Pattern.matches(value, result)) {
					if (!result.contains(value)) {
						// New exception.
						throw new Exception();
					}
				}

				// Result - Title.
				MessageLibrary.log("Se ha validado el campo: <font color='Teal'>" + field
						+ "</font> con el valor: <font color='Teal'>" + result + "</font> correctamente");
			}

		} catch (Exception e) {
			// New exception.
			throw new Exception(
					" al validar la respuesta se espera que contenga el campo: '" + field + "' con el valor: " + value
							+ ", esta retornando: <font color='red'>" + result + "</font>. Error:" + e);
		}
	}

	/**
	 * This method is responsible for extracting and validating the contentType of
	 * the response.
	 * 
	 * @param requestContenType
	 * @param response
	 * @throws Exception
	 */
	public static void reportContentType(String requestContenType, ReqResponse response) throws Exception {
		// Variables.
		String resultadoContentType;

		// Validate result StatusCode.
		try {
			// Step - Title.
			MessageLibrary.log("Se va a validar que la respuesta tenga ContentType: '" + requestContenType + "'");

			// Extract contentType.
			String[] value = response.extract().contentType().split(Final.SEMICOLON);

			// Validation.
			if (requestContenType.contains(value[0])) {
				// Validations.
				resultadoContentType = response.extract().contentType();

			} else {
				// New exception.
				throw new Exception();
			}

			// Result - Title.
			MessageLibrary.log("Se ha validado el campo ContentType con el valor: <font color='Teal'>"
					+ resultadoContentType + "</font> correctamente");

		} catch (Exception e) {
			// New exception.
			throw new Exception("al validar los campos ContentType se esperaba: " + requestContenType
					+ ", está retornando: <font color='red'>" + response.extract().contentType() + "</font>");
		}
	}

	/**
	 * This method is responsible for extracting and validating the statusCode of
	 * the response.
	 * 
	 * @param statusCode
	 * @param response
	 * @throws Exception
	 */
	public static void reportStatusCode(int statusCode, ReqResponse response) throws Exception {
		// Variables.
		String resultadoStatusCode = "";

		// Validate result StatusCode.
		try {
			// Step - Title.
			MessageLibrary.log("Se va a validar que la respuesta tenga StatusCode: " + statusCode);

			if (response.extract().statusCode() == statusCode) {
				// Validations.
				resultadoStatusCode = response.validations().statusCode(statusCode).evaluate().extract().statusLine();
			} else {
				// New exception.
				throw new Exception();
			}

			// Result - Title.
			MessageLibrary.log("Se ha validado el campo StatusCode con el valor: <font color='Teal'>"
					+ resultadoStatusCode + "</font> correctamente");

		} catch (Exception e) {
			// New exception.
			throw new Exception("al validar los campos StatusCode se esperaba: " + statusCode
					+ ", está retornando: <font color='red'>" + response.extract().statusCode() + "</font>");
		}
	}

	/**
	 * This method is in charge of extracting the value of a data array (key [],
	 * response), returning a map with key and value.
	 * 
	 * @param fieldExitValue
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getDataArray(String[] fieldExitValue, ReqResponse response) throws Exception {
		// Variables.
		int i = 0;

		// Map.
		//Map<String, String> map = new HashMap<String, String>();

		try {
			// Create map (key,value).
			for (i = 0; i < fieldExitValue.length; i++) {
				// Step - Title.
				MessageLibrary.log(
						"Se va a extraer los datos del campo: <font color='Teal'>" + fieldExitValue[i] + "</font>");

				// We separate the data extraction when it is token or when it is not.
				if (fieldExitValue[i].equals(Final.METHODRESULT)) {
					// Extract the value of the indicated field and we format it.
					resultArray.add(
							response.extract().path(fieldExitValue[i]).toString().replaceFirst(Final.NULLVALUE, ""));
				} else {
					// Extract the value of the indicated field and we format it.
					String parse = response.extract().path(fieldExitValue[i].toString()).toString();

					// Format value.
					if (parse.isEmpty()) {
						parse = Final.NULLVALUE;
					}

					// Add result.
					resultArray.add(parse);
				}

				// Add the result to the map.
				map.put(fieldExitValue[i], resultArray.get(i));

				// Result - Title.
				MessageLibrary.log("Se ha extraído el campo: <font color='Teal'>" + fieldExitValue[i]
						+ "</font> con el valor: <font color='Teal'>" + resultArray.get(i) + "</font> correctamente");

				// Clear array.
				resultArray.clear();
			}

		} catch (Exception e) {
			// New exception.
			throw new Exception("al extraer los datos del campo: " + fieldExitValue[i] + " Error: <font color='red'>"
					+ e + "</font>");
		}
		return map;
	}

	/**
	 * his method is in charge of extracting the data once per iteration (key,
	 * value).
	 * 
	 * @param fieldExitValue
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String getDataString(String fieldExitValue, ReqResponse response) throws Exception {
		try {
			// Step - Title.
			MessageLibrary.log("Se va a extraer los datos del campo: <font color='Teal'>" + fieldExitValue + "</font>");

			// Extract the value of the indicated field and we format it.
			String parse = response.extract().path(fieldExitValue).toString();

			// Format value.
			if (parse.isEmpty()) {
				parse = Final.NULLVALUE;
			}

			// Add result.
			result = parse;

			// Result - Title.
			MessageLibrary.log("Se ha extraído el campo: <font color='Teal'>" + fieldExitValue
					+ "</font> con el valor: <font color='Teal'>" + parse + "</font> correctamente");

		} catch (Exception e) {
			// New exception.
			throw new Exception(
					"al extraer los datos del campo: " + fieldExitValue + " Error: <font color='red'>" + e + "</font>");
		}
		return result;
	}

	/**
	 * This method is responsible for extracting the data from the indicated
	 * headers.
	 * 
	 * @param fieldExitValue
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String getHeaderString(String fieldExitValue, ReqResponse response) throws Exception {
		try {
			// Step - Title.
			MessageLibrary.log("Se va a extraer la cabecera: <font color='Teal'>" + fieldExitValue + "</font>");

			// Extract the value of the indicated field and we format it.
			String parse = response.extract().header(fieldExitValue).toString().split(Final.SEMICOLON)[0];

			// Format value.
			if (parse.isEmpty()) {
				parse = Final.NULLVALUE;
			}

			// Add result.
			result = parse;

			// Result - Title.
			MessageLibrary.log("Se ha extraído la cabecera: <font color='Teal'>" + fieldExitValue
					+ "</font> con el valor: <font color='Teal'>" + parse + "</font> correctamente");

		} catch (Exception e) {
			// New exception.
			throw new Exception(
					"al extraer la cabecera: " + fieldExitValue + " Error: <font color='red'>" + e + "</font>");
		}
		return result;
	}

	/**
	 * This method is responsible for selecting the contentType.
	 * 
	 * @param key
	 * @return
	 */
	public static ContentType setContentType(String key) {
		switch (key.replaceAll("application/", "")) {
		case Final.JSON:
		case Final.JAVASCRIPT:
		case Final.TEXT_JAVASCRIPT:
			contentType = ContentType.JSON;

			break;
		case Final.CONTENTTYPE_ALL:
			contentType = ContentType.ANY;

			break;
		case Final.OCTET_STREAM:
			contentType = ContentType.BINARY;

			break;
		case Final.TEXT_HTML:
			contentType = ContentType.HTML;

			break;
		case Final.TEXT_PLAIN:
			contentType = ContentType.TEXT;

			break;
		case Final.X_WWW_FORM_URLENCODED:
			contentType = ContentType.URLENC;

			break;
		case Final.XML:
		case Final.TEXT_XML:
		case Final.XHTML_XML:
			contentType = ContentType.XML;

			break;
		default:
			break;
		}
		return contentType;
	}

	/**
	 * This method is responsible for reporting the error / exception that it shows.
	 * 
	 * @param e
	 */
	public static void endTestCaseError(Exception e) {
		// Result error - Title.
		String result = e.toString().replace(Final.ERROR_JAVA_LANG_EXCEPTION, "");
		MessageLibrary.log("Error en la ejecucion del testCase: " + result);

		// Result - End.
		MessageLibrary.log("<b><font color='Red'>Fin de la ejecucion</font></b>");
	}

	/**
	 * This method is responsible for extracting or validating the selected data.
	 * 
	 * @param data
	 * @param isDependency
	 * @param testCaseDependency
	 * @param i
	 * @param result2
	 * @param response03
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> extractOrValidateResult(TestData data, boolean isDependency,
			List<String> testCaseDependency, int i, Map<String, String> result2, ReqResponse response03)
			throws Exception {
		try {
			// In case it is a dependency TC, enter and extract the values of the
			// TC-dependency.
			if (isDependency) {
				String exitValue = data.get(Final.TESTSUITE, testCaseDependency.get(i), Final.EXIT_VALUE);
				String fieldValidationValue = data.get(Final.TESTSUITE, testCaseDependency.get(i),
						Final.RESPONSE_FIELD_VALIDATION);
				String headerValidationValue = data.get(Final.TESTSUITE, testCaseDependency.get(i),
						Final.RESPONSE_HEADER_VALIDATION);

				// Extract value.
				if (!exitValue.isEmpty()) {
					// Extract fields from the response.
					fieldExitValue = exitValue.split(Final.SEMICOLON);

					// Extract data from JSON response.
					result2 = getDataArray(fieldExitValue, response03);
					
					
					
					
//					result3.forEach((k, v) ->
//					result2.merge(k, v, (v1, v2) ->
//					        {throw new AssertionError("duplicate values for key: "+k);}));
//						
						
				}

				// Validate result.
				if (!fieldValidationValue.isEmpty()) {
					// Extract the fields to validate from the excel.
					arrayResponseFieldValidation = fieldValidationValue.split(Final.SEMICOLON);

					// Extract data and Validate.
					validateResult(arrayResponseFieldValidation, response03);
				}

				// Validate header response.
				if (!headerValidationValue.isEmpty()) {
					// Extract the fields to validate from the excel.
					arrayResponseHeaderValidation = headerValidationValue.split(Final.SEMICOLON);

					// Extract header and Validate.
					validateHeaderResult(arrayResponseFieldValidation, response03);
				}

			} else {
				// Extract value.
				if (!data.get(Final.EXIT_VALUE).isEmpty()) {
					// Extract fields from the response.
					fieldExitValue = data.get(Final.EXIT_VALUE).split(Final.SEMICOLON);

					// Extract data from JSON response.
					result2 = getDataArray(fieldExitValue, response03);
				}

				// Validate result response.
				if (!data.get(Final.RESPONSE_FIELD_VALIDATION).isEmpty()) {
					// Extract the fields to validate from the excel.
					arrayResponseFieldValidation = data.get(Final.RESPONSE_FIELD_VALIDATION).split(Final.SEMICOLON);

					// Validate data.
					validateResult(arrayResponseFieldValidation, response03);
				}

				// Validate header response.
				if (!data.get(Final.RESPONSE_HEADER_VALIDATION).isEmpty()) {
					// Extract the fields to validate from the excel.
					arrayResponseHeaderValidation = data.get(Final.RESPONSE_HEADER_VALIDATION).split(Final.SEMICOLON);

					// Extract header and Validate.
					validateHeaderResult(arrayResponseHeaderValidation, response03);
				}
			}
		} catch (Exception e) {
			throw new Exception(
					" se ha producido un error a la hora de extraer/validar los datos: Detalle del error: " + e);
		}
		return result2;
	}

	/**
	 * This method is responsible for extracting and validating the desired headers.
	 * 
	 * @param partsVerify
	 * @param response03
	 * @throws Exception
	 */
	private static void validateHeaderResult(String[] partsVerify, ReqResponse response03) throws Exception {
		// Extract the headers from the response and validate. Indicated in column
		// 'RESPONSE_HEADER_VALIDATION'.

		// Variables.
		String field = "";
		String value = "";

		try {
			// Extract headers response.
			for (int i = 0; i < partsVerify.length; i++) {
				String[] valueVerify = partsVerify[i].split(Final.EQUALS, 2);

				// Extract data.
				field = valueVerify[0];
				value = valueVerify[1];

				// Extract header.
				result = getHeaderString(field, response);

				// Step - Title.
				MessageLibrary.log("Se va a validar que la respuesta contenga la cabecera: '" + field
						+ "' con el valor: '" + value + "'");

				// Validate data.
				if (!Pattern.matches(value, result)) {
					// New exception.
					throw new Exception();
				}

			}

			// Result - Title.
			MessageLibrary.log("Se ha validado la cabecera: <font color='Teal'>" + field
					+ "</font> con el valor: <font color='Teal'>" + result + "</font> correctamente");

		} catch (Exception e) {
			// New exception.
			throw new Exception(
					" al validar la respuesta se espera que contenga la cabecera: '" + field + "' con el valor: "
							+ value + ", esta retornando: <font color='red'>" + result + "</font>. Error:" + e);
		}
	}

	/**
	 * This method is responsible for launching the action indicated in the tcData.
	 * 
	 * @param key
	 * @param url
	 * @param header
	 * @param statusCode
	 * @param contentType2
	 * @param bodyRequest
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static ReqResponse launchAction(String key, String url, String[] header, String statusCode,
			String contentType2, String bodyRequest, ReqResponse response) throws Exception {
		// Select the action to perform.
		switch (key) {
		case Final.POST:
			// Post.
			response = new Post().executePost(url, header, statusCode, contentType2, bodyRequest);

			break;
		case Final.GET:
			// Get.
			response = new Get().executeGet(url, header, statusCode, contentType2);

			break;
		case Final.PUT:
			// Put.
			response = new Put().executePut(url, header, statusCode, contentType2, bodyRequest);

			break;
		case Final.DELETE:
			// Delete.
			response = new Delete().executeDelete(url, header, statusCode, contentType2);

			break;
		case Final.PATCH:
			// Patch.
			response = new Patch().executePatch(url, header, statusCode, contentType2, bodyRequest);
			isPATCH = true;

			break;
		default:
			// New exception.
			throw new Exception(
					" <font color='red'> no se ha seleccionado ninguno de las acciones disponibles consulte el archivo tcdata y seleccione la accion a realizar </font>");
		}
		return response;
	}

	/**
	 * This method is in charge of executing the selected TC and its dependencies
	 * (if it had them).
	 * 
	 * data -> option TestData.
	 */
	public static void launchTC(TestData data) throws Exception {
		// It is checked whether or not the 'dependency' field is empty.
		if (!data.get(Final.DEPENDENCY).isEmpty()) {
			// Extract dependencies.
			testCaseDependency = new LinkedList<String>(
					Arrays.asList(data.get(Final.DEPENDENCY).split(Final.SEMICOLON)));
			testCaseDependency.add(data.get(Final.ACTION).toString());

			isDependency = true;
		} else {
			testCaseDependency = new LinkedList<String>(Arrays.asList(data.get(Final.ACTION).toString()));

			isDependency = false;
		}

		// Extract the values of the dependencies.
		for (int i = 0; i < testCaseDependency.size(); i++) {
			// Check if the loop is in the last position or not.
			if (i < testCaseDependency.size() - 1) {
				isDependency = true;
			} else {
				isDependency = false;
			}

			try {
				// The data is extracted according to whether it comes from a dependency test or
				// from itself test running.
				if (isDependency) {
					// Variables.
					url = data.get(Final.TESTSUITE, testCaseDependency.get(i), Final.URL);
					header = data.get(Final.TESTSUITE, testCaseDependency.get(i), Final.HEADERS).split(Final.SEMICOLON);
					statusCode = data.get(Final.TESTSUITE, testCaseDependency.get(i), Final.RESPONSE_STATUSCODE)
							.replaceAll("\\.0", "");
					contentTypeLaunchTC = data.get(Final.TESTSUITE, testCaseDependency.get(i),
							Final.RESPONSE_CONTENT_TYPE);
					key = data.get(Final.TESTSUITE, testCaseDependency.get(i), Final.ACTION);

					// Check whether or not you have a body to extract.
					if (!data.get(Final.TESTSUITE, testCaseDependency.get(i), Final.REQUEST).isEmpty()) {
						bodyRequest = data.get(Final.TESTSUITE, testCaseDependency.get(i), Final.REQUEST);
						if (!data.get(Final.TESTSUITE, testCaseDependency.get(i), Final.PASSWORD).isEmpty()) {
							String clave = data.get(Final.TESTSUITE, testCaseDependency.get(i), Final.PASSWORD);
							bodyRequest = bodyRequest.replace(Final.password,
									Accesser.read(clave.substring(clave.indexOf("{"), clave.indexOf("}"))));
						}

					} else {
						// Reset body.
						bodyRequest = "";
					}

				} else {
					// Variables.
					url = data.get(Final.URL);
					header = data.get(Final.HEADERS).split(Final.SEMICOLON);
					statusCode = data.get(Final.RESPONSE_STATUSCODE).replaceAll("\\.0", "");
					contentTypeLaunchTC = data.get(Final.RESPONSE_CONTENT_TYPE);
					key = testCaseDependency.get(i);

					// Check whether or not you have a body to extract.
					// bodyRequest=!data.get(Final.REQUEST).isEmpty()?data.get(Final.REQUEST):"";
					if (!data.get(Final.REQUEST).isEmpty()) {
						bodyRequest = data.get(Final.REQUEST);
					} else {
						// Reset body.
						bodyRequest = "";
					}
				}
			} catch (Exception e) {
				// New exception.
				throw new Exception(
						" al extraer los datos del tcdata. Mensaje del error: <font color='red'>" + e + "</font>");
			}

			try {
				// Format values.
				// Replaces the dynamic variables entered in the tcdata, by the variables.
				// obtained when extracting the data from post or get.
				if (MapUtils.isNotEmpty(resultLaunchTC) && resultLaunchTC.size() > 0) {
					// It extracts for each record of the map, the key and the value.
					for (Map.Entry<String, String> entry : resultLaunchTC.entrySet()) {
						// Extract and replace dynamic values.
						url = url.replaceAll("\\$\\{" + entry.getKey() + "\\}", entry.getValue());
						statusCode = statusCode.replaceAll("\\$\\{" + entry.getKey() + "\\}", entry.getValue())
								.replaceAll("\\.0", "");
						contentTypeLaunchTC = contentTypeLaunchTC.replaceAll("\\$\\{" + entry.getKey() + "\\}",
								entry.getValue());
						bodyRequest = bodyRequest.replaceAll("\\$\\{" + entry.getKey() + "\\}", entry.getValue());

						// Format Headers.
						for (int j = 0; j < header.length; j++) {
							header[j] = header[j].replaceAll("\\$\\{" + entry.getKey() + "\\}", entry.getValue());
						}
					}
				}
			} catch (Exception e) {
				// New exception.
				throw new Exception(
						" al reemplazar los datos extraidos por las variables dinamicas indicadas. Mensaje del error: <font color='red'>"
								+ e + "</font>");
			}

			// Select and execute the action indicated in the 'Action' column.
			response = launchAction(key, url, header, statusCode, contentTypeLaunchTC, bodyRequest, response);

			// Extract or validate the selected data.
			resultLaunchTC = extractOrValidateResult(data, isDependency, testCaseDependency, i, resultLaunchTC,
					response);
		}
	}
}