package uk.gov.hmcts.reform.em.orchestrator.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.hmcts.reform.em.orchestrator.service.ccdcallbackhandler.CcdCallbackDto;
import uk.gov.hmcts.reform.em.orchestrator.service.ccdcallbackhandler.CcdCallbackDtoCreator;
import uk.gov.hmcts.reform.em.orchestrator.service.ccdcallbackhandler.CcdCallbackHandlerService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class NewBundleController {

    private CcdCallbackHandlerService ccdCallbackHandlerService;
    private CcdCallbackDtoCreator ccdCallbackDtoCreator;

    public NewBundleController(CcdCallbackHandlerService ccdCallbackHandlerService, CcdCallbackDtoCreator ccdCallbackDtoCreator) {
        this.ccdCallbackHandlerService = ccdCallbackHandlerService;
        this.ccdCallbackDtoCreator = ccdCallbackDtoCreator;
    }

    @PostMapping(value = "/api/new-bundle",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> exampleServicePrepareNewBundle(HttpServletRequest request) throws IOException {
        CcdCallbackDto dto = ccdCallbackDtoCreator.createDto(request, "caseBundles");
        JsonNode jsonNode = ccdCallbackHandlerService.handleCddCallback(dto);
        return ResponseEntity.ok(jsonNode);
    }

}