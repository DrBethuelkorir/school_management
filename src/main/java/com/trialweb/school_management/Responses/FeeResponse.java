package com.trialweb.school_management.Responses;

import com.trialweb.school_management.Dtos.FeeDto;
import lombok.Data;

import java.util.List;

@Data
public class FeeResponse {
    private int statusCode;
    private String message;
    private FeeDto feeDto;
    private List<FeeDto> feeDtoList;
}
