package com.trialweb.school_management.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trialweb.school_management.Dtos.StreamDto;

import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StreamResponse {
    private int statusCode;
    private String message;
    private StreamDto streamDto;
    private List<StreamDto> streamsDto;
}