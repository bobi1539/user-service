package com.zero.service.controller;

import com.zero.service.dto.response.NotificationResponse;
import com.zero.service.dto.response.ResponseWithData;
import com.zero.service.entity.Notification;
import com.zero.service.repository.NotificationRepository;
import com.zero.service.service.NotificationService;
import com.zero.service.service.impl.NotificationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notification")
@Slf4j
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository repository;

    @GetMapping
    public ResponseEntity<?> notificationListByUserIdAndOrCategoryId(
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) Integer categoryId
    ){
        List<NotificationResponse> notificationResponseList = this.notificationService.findByUserIdAndOrCategoryId(
                userId, categoryId
        );
        return ResponseEntity.ok(
                new ResponseWithData<List<NotificationResponse>>(
                        HttpStatus.OK.value(), "Notification List", notificationResponseList
                )
        );
    }

    @GetMapping("/read")
    public ResponseEntity<?> read(){
        log.info("start");
        Long id = 112L;
        List<Notification> read = this.repository.findByUserId(id);

        log.info("end");
        return ResponseEntity.ok(read);
    }
}
