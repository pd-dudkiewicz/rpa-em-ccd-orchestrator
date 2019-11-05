package uk.gov.hmcts.reform.em.orchestrator.smoke;

import net.serenitybdd.rest.SerenityRest;
import org.junit.Test;
import uk.gov.hmcts.reform.em.orchestrator.testutil.Env;

public class SmokeTest {

    @Test
    public void testHealthEndpoint() {

        SerenityRest.useRelaxedHTTPSValidation();

        SerenityRest.given()
            .request("GET", Env.getTestUrl() + "/health")
            .then()
            .statusCode(200);


    }
}
