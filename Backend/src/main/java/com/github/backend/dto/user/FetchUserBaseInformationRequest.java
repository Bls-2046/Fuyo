package com.github.backend.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FetchUserBaseInformationRequest {
    private String username;
}
