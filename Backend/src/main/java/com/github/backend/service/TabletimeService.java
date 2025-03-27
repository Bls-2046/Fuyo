package com.github.backend.service;

import com.github.backend.dto.tabletime.FetchTabletimeRequest;
import com.github.backend.dto.tabletime.FetchTabletimeResponse;

import java.util.List;

public interface TabletimeService {
    /**
     * 获取用户当天课表信息
     * @param username 用户名
     * @return List<TabletimeResponse.Tabletime>
     */
    List<FetchTabletimeResponse.Tabletime> fetchTabletime(FetchTabletimeRequest fetchTabletimeRequest);
}
