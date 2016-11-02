package com.timelinekeeping.repository;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.entity.EmotionContentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lethanhtan on 9/19/16.
 */

@Repository
public interface EmotionContentRepo extends JpaRepository<EmotionContentEntity, Long> {

    @Query("SELECT e FROM EmotionContentEntity  e WHERE e.emotionFirst = :first AND (:second is null  or e.emotionSecond =:second  ) AND (:third is null or e.emotionThird =:third ) AND (e.status <> 0) order by e.vote")
    public Page<EmotionContentEntity> getEmotionContent(@Param("first") EEmotion first,
                                                        @Param("second") EEmotion second,
                                                        @Param("third") EEmotion third,
                                                        Pageable pageable);
    @Modifying
    @Transactional
    @Query(value = "update emotion_content set vote = vote +1 where id  = ?1", nativeQuery = true)
    public void updateVote(Long contentId);
}
