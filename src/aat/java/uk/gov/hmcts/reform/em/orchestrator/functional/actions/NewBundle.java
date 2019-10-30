package uk.gov.hmcts.reform.em.orchestrator.functional.actions;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.springframework.http.MediaType;
import uk.gov.hmcts.reform.em.orchestrator.testutil.Env;

import static uk.gov.hmcts.reform.em.orchestrator.functional.TestSuiteInit.testUtil;

public class NewBundle {

    @Step("Perform New Bundle Post call with File {0}")
    public Response postRequestWithPayload(JsonNode fileName){
        return testUtil.authRequest()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(fileName)
                .request("POST", Env.getTestUrl() + "/api/new-bundle");
    }

    @Step("Perform New Bundle Post call with String payload{0}")
    public Response postRequestWithPayload(String payload){
        return testUtil.authRequest()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(payload)
                .request("POST", Env.getTestUrl() + "/api/new-bundle");
    }
}
