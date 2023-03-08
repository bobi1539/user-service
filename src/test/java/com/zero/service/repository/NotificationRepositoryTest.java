package com.zero.service.repository;

import com.zero.service.entity.Notification;
import com.zero.service.entity.NotificationCategory;
import com.zero.service.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class NotificationRepositoryTest {
    
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationCategoryRepository notificationCategoryRepository;

    @Test
    void findByUserIdSuccessTest() {

        User user = this.userSave();
        NotificationCategory category1 = this.categorySave1();
        NotificationCategory category2 = this.categorySave2();
        Notification notification1 = this.notificationSaveWithUser(user, category1);
        Notification notification2 = this.notificationSaveNoUser(category2);

        try {
            List<Notification> notificationList = notificationRepository.findByUserId(user.getId());

            assertNotNull(notificationList);
            assertTrue(notificationList.size() > 0);

            for (Notification notification : notificationList) {
                log.info("notif : " + notification.getTitle());
                if (notification.getUser() != null) {
                    log.info("user : " + notification.getUser().getUsername());
                }
                if (notification.getCategory() != null){
                    log.info("category : " + notification.getCategory().getName());
                }
            }
        } catch (Exception e){
            log.error("Error : " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.notificationRepository.delete(notification1);
            this.notificationRepository.delete(notification2);
            this.userRepository.delete(user);
            this.notificationCategoryRepository.delete(category1);
            this.notificationCategoryRepository.delete(category2);
        }
    }

    @Test
    void findByUserIdAndCategoryId() {
        // save user
        User user = this.userSave();

        // save category
        NotificationCategory category = new NotificationCategory();
        category.setName("info test");
        NotificationCategory categorySave = this.notificationCategoryRepository.save(category);

        // save notif
        Notification notification = new Notification();
        notification.setTitle("notif test1");
        notification.setDetail("detail notif test1");
        notification.setCreateAt(new Date());
        notification.setUser(user);
        notification.setCategory(categorySave);
        Notification notificationSave = this.notificationRepository.save(notification);


        try {
            List<Notification> byUserIdAndCategoryId = this.notificationRepository.findByUserIdAndCategoryId(
                    user.getId(), categorySave.getId()
            );

            assertNotNull(byUserIdAndCategoryId);
            assertTrue(byUserIdAndCategoryId.size() > 0);

        } catch (Exception e){
            log.error("Error : " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.notificationRepository.delete(notificationSave);
            this.userRepository.delete(user);
            this.notificationCategoryRepository.delete(categorySave);
        }
    }

    User userSave(){
        User user = new User();
        user.setUsername("userTest");
        user.setPassword("userPassTest");
        User save = this.userRepository.save(user);
        return save;
    }

    Notification notificationSaveWithUser(User user, NotificationCategory category){

        Notification notif = new Notification();
        notif.setTitle("title test1");
        notif.setDetail("detail test1");
        notif.setCreateAt(new Date());
        notif.setUser(user);
        notif.setCategory(category);

        Notification save = this.notificationRepository.save(notif);
        return save;
    }

    Notification notificationSaveNoUser(NotificationCategory category){

        Notification notif = new Notification();
        notif.setTitle("title test2");
        notif.setDetail("detail test2");
        notif.setCreateAt(new Date());
        notif.setUser(null);
        notif.setCategory(category);

        Notification save = this.notificationRepository.save(notif);
        return save;
    }

    NotificationCategory categorySave1(){
        NotificationCategory category = new NotificationCategory();
        category.setName("info test");
        return this.notificationCategoryRepository.save(category);
    }

    NotificationCategory categorySave2(){
        NotificationCategory category = new NotificationCategory();
        category.setName("promo test");
        return this.notificationCategoryRepository.save(category);
    }
}