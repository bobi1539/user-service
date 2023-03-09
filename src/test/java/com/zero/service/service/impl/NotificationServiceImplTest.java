package com.zero.service.service.impl;

import com.zero.service.constant.Constant;
import com.zero.service.dto.response.NotificationResponse;
import com.zero.service.entity.Notification;
import com.zero.service.entity.NotificationCategory;
import com.zero.service.entity.User;
import com.zero.service.repository.NotificationCategoryRepository;
import com.zero.service.repository.NotificationRepository;
import com.zero.service.repository.UserRepository;
import com.zero.service.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class NotificationServiceImplTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationCategoryRepository categoryRepository;

    @Test
    void findByUserIdAndOrCategoryIdSuccessTest() {

        User user = this.saveUser();
        NotificationCategory categoryInfoTest = this.saveCategory("category info test");
        NotificationCategory categoryPromoTest = this.saveCategory("category promo test");
        Notification notificationWithUserId = this.saveNotificationWithUserId(user, categoryInfoTest);
        Notification notificationNoUserId = this.saveNotificationNoUserId(categoryPromoTest);

        try{

            List<NotificationResponse> allNotfication = this.notificationService.findByUserIdAndOrCategoryId(
                    user.getId(), null
            );
            assertTrue(allNotfication.size() == 2);

            List<NotificationResponse> notifCategoryInfo = this.notificationService.findByUserIdAndOrCategoryId(
                    user.getId(), categoryInfoTest.getId()
            );
            assertTrue(notifCategoryInfo.size() == 1);
            assertNotNull(notifCategoryInfo.get(0).getUserResponse());
            assertEquals(user.getId(), notifCategoryInfo.get(0).getUserResponse().getId());

            List<NotificationResponse> notifCategoryPromo = this.notificationService.findByUserIdAndOrCategoryId(
                    user.getId(), categoryPromoTest.getId()
            );
            assertTrue(notifCategoryPromo.size() == 1);
            assertNull(notifCategoryPromo.get(0).getUserResponse().getId());
            assertNull(notifCategoryPromo.get(0).getUserResponse().getUsername());

        } catch (Exception e){
            log.error("Error : " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.notificationRepository.delete(notificationWithUserId);
            this.notificationRepository.delete(notificationNoUserId);
            this.userRepository.delete(user);
            this.categoryRepository.delete(categoryInfoTest);
            this.categoryRepository.delete(categoryPromoTest);
        }
    }

    @Test
    void findByUserIdAndOrCategoryIdUserIdNullTest() {
        NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> {
            this.notificationService.findByUserIdAndOrCategoryId(null, 1);
        });
        assertEquals(Constant.USER_ID_CANNOT_BE_NULL, nullPointerException.getMessage());
    }

    @Test
    void findByUserIdAndOrCategoryIdNotFoundTest() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            this.notificationService.findByUserIdAndOrCategoryId(1L,2);
        });
        assertEquals(Constant.NOTIFICATION_NOT_FOUND, exception.getMessage());
    }

    User saveUser(){
        User user = new User();
        user.setUsername("notifUserTest");
        user.setPassword("notifUserPass");
        return this.userRepository.save(user);
    }

    NotificationCategory saveCategory(String categoryName){
        NotificationCategory category = new NotificationCategory();
        category.setName(categoryName);
        return this.categoryRepository.save(category);
    }

    Notification saveNotificationWithUserId(User user, NotificationCategory category){
        Notification notification = new Notification();
        notification.setTitle("Pembayaran Test");
        notification.setDetail("Dengan user id");
        notification.setCreateAt(new Date());
        notification.setUser(user);
        notification.setCategory(category);
        return this.notificationRepository.save(notification);
    }

    Notification saveNotificationNoUserId(NotificationCategory category){
        Notification notification = new Notification();
        notification.setTitle("Promo Test");
        notification.setDetail("Tidak dengan user id");
        notification.setCreateAt(new Date());
        notification.setUser(null);
        notification.setCategory(category);
        return this.notificationRepository.save(notification);
    }
}