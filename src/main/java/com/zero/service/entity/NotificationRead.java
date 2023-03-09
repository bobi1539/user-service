package com.zero.service.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_notification_read")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class NotificationRead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "notification_id", nullable = false)
//    private Notification notification;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
