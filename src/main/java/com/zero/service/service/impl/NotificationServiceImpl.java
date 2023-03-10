package com.zero.service.service.impl;

import com.zero.service.constant.Constant;
import com.zero.service.dto.response.NotificationResponse;
import com.zero.service.dto.response.UserResponse;
import com.zero.service.entity.Notification;
import com.zero.service.entity.NotificationRead;
import com.zero.service.entity.User;
import com.zero.service.repository.NotificationReadRepository;
import com.zero.service.repository.NotificationRepository;
import com.zero.service.repository.UserRepository;
import com.zero.service.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<NotificationResponse> findByUserIdAndOrCategoryId(Long userId, Integer categoryId) {

        this.validateUserId(userId);

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

            List<NotificationRead> notificationReads = notification.getNotificationReads();
            log.info("notif read size : " + notificationReads.size());
            if(!notificationReads.isEmpty()) {
                List<NotificationRead> collect = notificationReads.stream().filter(new Predicate<NotificationRead>() {
                    @Override
                    public boolean test(NotificationRead notificationRead) {
                        return notificationRead.getUser().getId() == userId;
                    }
                }).collect(Collectors.toList());

                if(!collect.isEmpty()) {
                    notifResponse.setIsRead(collect.get(0).getIsRead());
                    log.info("user id notif read : " + collect.get(0).getUser().getId());
                } else {
                    notifResponse.setIsRead(false);
                }
            } else {
                notifResponse.setIsRead(false);
            }

            notificationResponseList.add(notifResponse);
        }

        return notificationResponseList;
    }

    @Override
    public Integer countNotificationUnRead(Long userId) {
        this.validateUserId(userId);

        List<Notification> notificationList = this.notificationRepository.findByUserId(userId);
        if(notificationList.isEmpty()){
            return 0;
        }

        int unread = 0;

        for(Notification notification : notificationList){
            List<NotificationRead> collect = notification.getNotificationReads().stream().filter(new Predicate<NotificationRead>() {
                @Override
                public boolean test(NotificationRead notificationRead) {
                    return notificationRead.getUser().getId() == userId;
                }
            }).collect(Collectors.toList());
            if(collect.isEmpty()){
                unread += 1;
            }
        }

        return unread;
    }

    private void validateUserId(Long userId){
        if(userId == null){
            throw new NullPointerException(Constant.USER_ID_CANNOT_BE_NULL);
        }

        Optional<User> userRepositoryById = this.userRepository.findById(userId);
        if(userRepositoryById.isEmpty()){
            throw new NullPointerException(Constant.USER_NOT_FOUND);
        }
    }
}
