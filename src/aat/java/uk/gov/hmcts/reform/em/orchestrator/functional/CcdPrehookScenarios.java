package uk.gov.hmcts.reform.em.orchestrator.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.gov.hmcts.reform.em.orchestrator.functional.actions.BundleActions;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static uk.gov.hmcts.reform.em.orchestrator.functional.TestSuiteInit.testUtil;

@RunWith(SerenityRunner.class)
@Ignore
public class CcdPrehookScenarios {

    private final ObjectMapper mapper = new ObjectMapper();
    private final File jsonFile = new File(ClassLoader.getSystemResource("prehook-case.json").getPath());

    @Steps
    private BundleActions bundleActions;

    @BeforeClass
    public static void setup() throws Exception {
        testUtil.getCcdHelper().importCcdDefinitionFile();
    }

    @Test
    public void testPostBundleStitch() {
        Response response = bundleActions.createNewBundle(jsonFile);

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("New Bundle", response.getBody().jsonPath().getString("data.caseBundles[0].value.title"));
    }

    @Test
    public void testEndToEnd() throws IOException {
        HashMap caseData = bundleActions.createNewBundle(jsonFile)
            .getBody()
            .jsonPath()
            .get("data");

        // pretend the user has modified some fields
        String uploadedDocUri = testUtil.uploadDocument();
        String caseJson = mapper.writeValueAsString(caseData)
            .replace("New Bundle", "Bundle title")
            .replace("hasCoversheets\":\"Yes", "hasCoversheets\":\"No")
            .replace("http://dm-store:8080/documents/05647df3-094c-45a3-b667-2a6f1bf3d088", uploadedDocUri);

        String request = String.format("{ \"case_details\":{ \"case_data\": %s } } }", caseJson);

        Response response = bundleActions.stitchBundle(request);

        JsonPath path = response.getBody().jsonPath();
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("Bundle title", path.getString("data.caseBundles[0].value.title"));
        Assert.assertEquals("No", path.getString("data.caseBundles[0].value.hasCoversheets"));
        Assert.assertNotNull(path.getString("data.caseBundles[0].value.stitchedDocument.document_url"));

    }
}
