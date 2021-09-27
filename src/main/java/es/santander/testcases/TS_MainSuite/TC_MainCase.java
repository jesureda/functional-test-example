package es.santander.testcases.TS_MainSuite;

import com.indra.cilantrum.framework.api.test.SimpleTestCase;
import com.indra.cilantrum.messages.MessageLibrary;
import com.indra.cilantrum.report.utils.ReportUtils;

import es.santander.requests.Browser;
import es.santander.requests.Final;

public class TC_MainCase extends SimpleTestCase {

	@Override
	public void preExecute() {
	}

	@Override
	public void execute() {
		try {
			// Launch TestCase.
			Browser.launchTC(data);

		} catch (Exception e) {
			//Browser.endTestCaseError(e);
			// Result error - Title.
			String result = e.toString().replace(Final.ERROR_JAVA_LANG_EXCEPTION, "");
			// Result - End.
			ReportUtils.reportError(result, false, "Request Error", result);
		}
	}

	@Override
	public void postExecute() {
		MessageLibrary.log("<b>Fin de la ejecucion</b>");
	}
}
