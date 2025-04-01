package com.github.dto.tabletime;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FetchTabletimeRequest {
    private String username;
}