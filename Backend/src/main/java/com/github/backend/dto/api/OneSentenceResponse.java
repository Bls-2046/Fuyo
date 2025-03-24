package com.github.backend.dto.api;

import lombok.Data;

@Data
public class OneSentenceResponse {
    private int status;
    private String sentence;
}
