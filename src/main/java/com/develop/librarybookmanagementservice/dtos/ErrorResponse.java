package com.develop.librarybookmanagementservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(String code, String description, String traceId, List<ValidationErrors> errors) {
    @Builder
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public record ValidationErrors(String field, String description, String rejectedValue) {
    }
}
