package es.santander.requests;

public final class Final {
	// 0.
	public static final int ZERO = 0;

	// 1 - 5.
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;

	// 6 - 10.
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;

	// 11 - 15.
	public static final int ELEVEN = 11;
	public static final int TWELVE = 12;
	public static final int THIRTEEN = 13;
	public static final int FOURTEEN = 14;
	public static final int FIFTEEN = 15;

	// 16 - 20.
	public static final int SIXTEEN = 16;
	public static final int SEVENTEEN = 17;
	public static final int EIGHTEEN = 18;
	public static final int NINETEEN = 19;
	public static final int TWENTY = 20;

	// 21 - 25.
	public static final int TWENTY_ONE = 21;
	public static final int TWENTY_TWO = 22;
	public static final int TWENTY_THREE = 23;
	public static final int TWENTY_FOUR = 24;
	public static final int TWENTY_FIVE = 25;

	// 26 - 30.
	public static final int TWENTY_SIX = 26;
	public static final int TWENTY_SEVEN = 27;
	public static final int TWENTY_EIGHT = 28;
	public static final int TWENTY_NINE = 29;
	public static final int THIRTY = 30;

	// 31 - 35.
	public static final int THIRTY_ONE = 31;
	public static final int THIRTY_TWO = 32;
	public static final int THIRTY_THREE = 33;
	public static final int THIRTY_FOUR = 34;
	public static final int THIRTY_FIVE = 35;

	// 36 - 40.
	public static final int THIRTY_SIX = 36;
	public static final int THIRTY_SEVEN = 37;
	public static final int THIRTY_EIGHT = 38;
	public static final int THIRTY_NINE = 39;
	public static final int FORTY = 40;

	// 41 - 45.
	public static final int FORTY_ONE = 41;
	public static final int FORTY_TWO = 42;
	public static final int FORTY_THREE = 43;
	public static final int FORTY_FOUR = 44;
	public static final int FORTY_FIVE = 45;

	// +50.
	public static final int FIFTY_TWO = 52;
	public static final int SIXTY = 60;

	// +100.
	public static final int TWO_HUNDRED = 200;
	public static final int FOUR_HUNDRED = 400;
	public static final int FOUR_HUNDRED_ONE = 401;
	public static final int FOUR_HUNDRED_THREE = 403;
	public static final int FOUR_HUNDRED_FOUR = 404;
	public static final int FOUR_HUNDRED_FIFTEEN = 415;
	public static final int FIVE_HUNDRED = 500;

	// Other variables.
	public static final String COMMA = ",";
	public static final String SEMICOLON = ";";
	public static final String NULLVALUE = "null";
	public static final String INVALIDTOKEN = "INVALIDTOKEN";
	public static final String INVALIDDATA = "INVALIDDATA";
	public static final String DIFFERENTDATA = "DIFFERENTDATA";
	public static final String EQUALS = "=";
	public static final String UTILITY_CLASS = "Utility class";

	// Other variables.
	public static final String ENDPOINTTOKEN = "ENDPOINTTOKEN";
	public static final String CONTENTTYPE = "Content-Type";
	public static final String ENDPOINT = "ENDPOINT";
	public static final String USERTOKEN = "USERTOKEN";
	public static final String PASSTOKEN = "PASSTOKEN";
	public static final String STATUSCODE = "STATUSCODE";
	public static final String ACTION = "ACTION";
	public static final String VALUE = "VALUE";
	public static final String EXIT_VALUE = "EXIT_VALUE";
	public static final String URL = "URL";
	public static final String HEADERS = "HEADERS";
	public static final String DEPENDENCY = "DEPENDENCY";
	public static final String REQUEST = "REQUEST";
	public static final String RESPONSE_STATUSCODE = "RESPONSE_STATUSCODE";
	public static final String RESPONSE_CONTENT_TYPE = "RESPONSE_CONTENT_TYPE";
	public static final String RESPONSE_FIELD_VALIDATION = "RESPONSE_FIELD_VALIDATION";
	public static final String RESPONSE_HEADER_VALIDATION = "RESPONSE_HEADER_VALIDATION";
	public static final String TESTSUITE = "TS_MainSuite";
	public static final String POST = "POST";
	public static final String POST_FILE = "POSTFILE";
	public static final String POST_BAJA = "POST_BAJA";
	public static final String GET = "GET";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";
	public static final String OPTIONS = "OPTIONS";
	public static final String FILE_PATH = "FILE_PATH";
	public static final String PATCH = "PATCH";
	public static final String METHODRESULT = "methodResult";

	// ContentType.
	public static final String JSON = "json";
	public static final String JAVASCRIPT = "javascript";
	public static final String TEXT_JAVASCRIPT = "text/javascript";
	public static final String CONTENTTYPE_ALL = "*/*";
	public static final String OCTET_STREAM = "octet-stream";
	public static final String TEXT_HTML = "text/html";
	public static final String TEXT_PLAIN = "text/plain";
	public static final String X_WWW_FORM_URLENCODED = "x-www-form-urlencoded";
	public static final String XML = "xml";
	public static final String TEXT_XML = "text/xml";
	public static final String XHTML_XML = "xhtml+xml";

	// Error.
	public static final String ERROR_JAVA_LANG_EXCEPTION = "java.lang.Exception: ";

	public static final String PASSWORD = "PASSWORD";
	public static final String password = "{password}";

	// Private constructor is necessary (Sonar).
	private Final() {
		throw new IllegalStateException("Utility class");
	}
}