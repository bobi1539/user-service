package com.zero.service.repository.query;

public class MyQuery {
//    public static final String NOTIFICATION_FIND_BY_USER_ID =   "SELECT * FROM m_notification mn " +
//                                                                "LEFT JOIN t_notification_read tnr ON tnr.notification_id = mn.id " +
//                                                                "WHERE (mn.user_id = :userId OR mn.user_id IS NULL) " +
//                                                                "AND (tnr.user_id = :userId OR tnr.user_id IS NULL) " +
//                                                                "ORDER BY mn.create_at DESC";
    public static final String NOTIFICATION_FIND_BY_USER_ID = "select * from m_notification where (user_id = :userId or user_id is null) order by create_at desc";

    public static final String NOTIFICATION_FIND_BY_USER_ID_AND_CATEGORY_ID =   "SELECT * FROM m_notification " +
                                                                                "WHERE (user_id = :userId OR user_id IS NULL) " +
                                                                                "AND category_id = :categoryId " +
                                                                                "ORDER BY create_at DESC";
}
