package com.github.dto.dormitory;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FetchDormitoryRequest {
    private String username;
}
