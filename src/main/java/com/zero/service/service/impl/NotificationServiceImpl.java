package com.zero.service.service.impl;

import com.zero.service.constant.Constant;
import com.zero.service.dto.response.NotificationResponse;
import com.zero.service.dto.response.UserResponse;
import com.zero.service.entity.Notification;
import com.zero.service.repository.NotificationRepository;
import com.zero.service.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<NotificationResponse> findByUserIdAndOrCategoryId(Long userId, Integer categoryId) {
        if(userId == null){
            throw new NullPointerException(Constant.USER_ID_CANNOT_BE_NULL);
        }

        List<Notification> notificationList = new ArrayList<>();
        if(categoryId == null){
            notificationList = this.notificationRepository.findByUserId(userId);
        } else {
            notificationList = this.notificationRepository.findByUserIdAndCategoryId(userId, categoryId);
        }

        if(notificationList.isEmpty()){
            throw new NullPointerException(Constant.NOTIFICATION_NOT_FOUND);
        }

        List<NotificationResponse> notificationResponseList = new ArrayList<>();

        for(Notification notification : notificationList){
            UserResponse userResponse = new UserResponse();

            if(notification.getUser() != null) {
                userResponse.setId(notification.getUser().getId());
                userResponse.setUsername(notification.getUser().getUsername());
            }

            NotificationResponse notifResponse = new NotificationResponse();
            notifResponse.setId(notification.getId());
            notifResponse.setTitle(notification.getTitle());
            notifResponse.setDetail(notification.getDetail());
            notifResponse.setCreateAt(notification.getCreateAt());
            notifResponse.setUserResponse(userResponse);
            notifResponse.setCategory(notification.getCategory());

            notificationResponseList.add(notifResponse);
        }

        return notificationResponseList;
    }
}
