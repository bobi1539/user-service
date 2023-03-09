package com.zero.service.controller;

import com.zero.service.dto.response.NotificationResponse;
import com.zero.service.dto.response.ResponseWithData;
import com.zero.service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

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
}
