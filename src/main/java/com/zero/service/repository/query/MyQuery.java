package com.zero.service.repository.query;

public class MyQuery {
    public static final String NOTIFICATION_FIND_BY_USER_ID =   "SELECT * FROM m_notification " +
                                                                "WHERE (user_id = :userId OR user_id IS NULL) " +
                                                                "ORDER BY create_at DESC";

    public static final String NOTIFICATION_FIND_BY_USER_ID_AND_CATEGORY_ID =   "SELECT * FROM m_notification " +
                                                                                "WHERE (user_id = :userId OR user_id IS NULL) " +
                                                                                "AND category_id = :categoryId" +
                                                                                "ORDER BY create_at DESC";
}
