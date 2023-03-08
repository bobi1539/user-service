package com.zero.service.dto.response;

import com.zero.service.entity.NotificationCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Integer id;
    private String title;
    private String detail;
    private Date createAt;
    private UserResponse userResponse;
    private NotificationCategory category;
}
