package uk.gov.hmcts.reform.em.orchestrator.service.ccdcallbackhandler;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class CcdCallbackResponseDto {

    public CcdCallbackResponseDto() {
    }

    public CcdCallbackResponseDto(JsonNode data) {
        setData(data);
    }

    private JsonNode data;

    private List<String> errors;

    private List<String> warnings;

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
}