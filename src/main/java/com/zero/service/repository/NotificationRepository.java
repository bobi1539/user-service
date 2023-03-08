package com.zero.service.repository;

import com.zero.service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.zero.service.repository.query.MyQuery;


import javax.websocket.server.PathParam;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query(value = MyQuery.NOTIFICATION_FIND_BY_USER_ID, nativeQuery = true)
    List<Notification> findByUserId(@PathParam("userId") Long userId);

    @Query(value = "", nativeQuery = true)
    List<Notification> findByUserIdAndCategoryId(
            @PathParam("userId") Long userId, @PathParam("categoryId") Integer categoryId
    );
}
