package com.zero.service.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.FilterJoinTable;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "m_notification")
@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "detail", nullable = false)
    private String detail;

    @Column(name = "create_at", nullable = false)
    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private NotificationCategory category;

//    @OneToMany(mappedBy = "notification", fetch = FetchType.LAZY)
//    @Where(clause = "user_id = 112")
    @OneToMany
    @JoinColumn(name = "notification_id")
    private List<NotificationRead> notificationReads;
}
