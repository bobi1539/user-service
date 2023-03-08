package com.zero.service.repository;

import com.zero.service.entity.NotificationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationCategoryRepository extends JpaRepository<NotificationCategory, Integer> {
}
