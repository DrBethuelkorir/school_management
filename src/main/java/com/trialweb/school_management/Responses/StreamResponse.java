package com.trialweb.school_management.Responses;

import com.trialweb.school_management.Dtos.StreamDto;

import lombok.Data;

import java.util.List;

@Data
public class StreamResponse {
    private int statusCode;
    private String message;
    private StreamDto streamDto;
    private List<StreamDto> streamsDto;
}