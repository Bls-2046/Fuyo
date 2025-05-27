package com.github.backend.service;

import com.github.dto.dormitory.FetchDormitoryRequest;
import com.github.dto.dormitory.FetchDormitoryResponse;

public interface DormitoryService {

    FetchDormitoryResponse.Dormitory fetchDormitory(FetchDormitoryRequest fetchDormitoryRequest);
}
