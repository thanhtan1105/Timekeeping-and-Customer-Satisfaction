package com.timelinekeeping.repository;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.entity.MessageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lethanhtan on 9/29/16.
 */

@Repository
public interface MessageRepo {

    @Query("SELECT * FROM MessageEntity a WHERE a.fromAge >= ?1 and a.toAge <= ?2 and a.gender = ?3 and a.emotion = ?4")
    List<MessageEntity> getListMessage(@Param("fromAge") Double fromAge,
                                       @Param("toAge") Double toAge,
                                       @Param("gender") int gender,
                                       @Param("emotion") String emotion);

}
