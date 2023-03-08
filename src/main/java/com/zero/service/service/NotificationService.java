package com.zero.service.service;

import com.zero.service.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {

    List<NotificationResponse> findByUserIdAndOrCategoryId(Long userId, Integer categoryId);
}
