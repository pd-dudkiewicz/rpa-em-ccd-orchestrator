package uk.gov.hmcts.reform.em.orchestrator.functional.actions;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.springframework.http.MediaType;
import uk.gov.hmcts.reform.em.orchestrator.testutil.Env;

import static uk.gov.hmcts.reform.em.orchestrator.functional.TestSuiteInit.testUtil;

public class BundleActions {

    @Step("Create new Bundle")
    public Response createNewBundle(Object object){
        return testUtil.authRequest()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(object)
                .request("POST", Env.getTestUrl() + "/api/new-bundle");
    }

    @Step("Clone a Bundle")
    public Response cloneBundle(String bundleJson){
        return testUtil.authRequest()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(bundleJson)
                .request("POST", Env.getTestUrl() + "/api/clone-ccd-bundles");
    }

    @Step("Stitch a Bundle")
    public Response stitchBundle(String bundleJson){
        return testUtil.authRequest()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(bundleJson)
                .request("POST", Env.getTestUrl() + "/api/stitch-ccd-bundles");
    }
}
