package com.github.backend.service.impl.Impls;

import com.github.backend.repository.mysql.DormitoryRepository;
import com.github.backend.service.DormitoryService;
import com.github.dto.dormitory.FetchDormitoryRequest;
import com.github.dto.dormitory.FetchDormitoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DormitoryServiceImpl implements DormitoryService {
    private final DormitoryRepository dormitoryRepository;

    @Autowired
    public DormitoryServiceImpl(DormitoryRepository dormitoryRepository) {
        this.dormitoryRepository = dormitoryRepository;
    }

    @Override
    public FetchDormitoryResponse.Dormitory fetchDormitory(FetchDormitoryRequest fetchDormitoryRequest) {
        String username = fetchDormitoryRequest.getUsername();
        return dormitoryRepository.findByUsername(username);
    }
}
