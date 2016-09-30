package com.timelinekeeping.repository;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lethanhtan on 9/29/16.
 */

@Repository
public interface MessageRepo extends JpaRepository<MessageEntity, Long> {

    @Query(value = "SELECT * " +
            "FROM message a " +
            "WHERE a.age_of_face >= ?1 and a.age_of_face <= ?2 and a.gender = ?3 and a.emotion = ?4 " +
            "GROUP BY a.message",
            nativeQuery = true)
    List<MessageEntity> getListMessage(@Param("fromAge") Double fromAge,
                                       @Param("toAge") Double toAge,
                                       @Param("gender") int gender,
                                       @Param("emotion") int emotion);

}