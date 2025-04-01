package com.github.backend.controller.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BasePageResponse<T> extends BaseResponse<List<T>> {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
}
