package com.timelinekeeping.model;

import com.timelinekeeping.entity.AbstractEntity;

/**
 * Created by HienTQSE60896 on 9/17/2016.
 */
public abstract class AbstractModel {
    public abstract <T extends AbstractEntity>  void fromEntity(T entity);
}
