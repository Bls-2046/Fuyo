package com.github.fuyo.model;

import com.github.dto.dormitory.FetchDormitoryRequest;
import com.github.dto.dormitory.FetchDormitoryResponse;
import com.github.fuyo.entity.DormitoryEntity;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.utils.https.Https;

public class DormitoryModel {
    public DormitoryModel() {}

    public static void fetchDormitory(String username) {
        String url = "http://localhost:8080/api/fetch/dormitory";
        FetchDormitoryRequest fetchDormitoryRequest = new FetchDormitoryRequest();

        try {
            fetchDormitoryRequest.setUsername(username);

            FetchDormitoryResponse fetchDormitoryResponse = Https.post(url, fetchDormitoryRequest, null, FetchDormitoryResponse.class);

            DormitoryEntity dormitoryEntity = new DormitoryEntity();

            dormitoryEntity.setDormNo(fetchDormitoryResponse.getDormitory().getDormNo());
            dormitoryEntity.setWaterFee(fetchDormitoryResponse.getDormitory().getWaterFee());
            dormitoryEntity.setElectricityFee(fetchDormitoryResponse.getDormitory().getElectricityFee());

            synchronized (UserEntity.getUserInformation()) {
                UserEntity.getUserInformation().setDormitory(dormitoryEntity);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
