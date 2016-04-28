package com.qunar.flight.autofile.api;

import com.qunar.flight.autofile.pojo.AutofileRequest;

/**
 * Created by zhouxi.zhou on 2016/3/12.
 */
public interface AutofileService {
    /**
     *
     * @param autofileRequest
     *
     * @return
     */
    String autofile(AutofileRequest autofileRequest);
}

