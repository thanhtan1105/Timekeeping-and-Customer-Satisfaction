package com.timelinekeeping.entity;

import com.timelinekeeping.model.AbstractModel;

/**
 * Created by HienTQSE60896 on 9/17/2016.
 */
public abstract class AbstractEntity {
    public abstract <T extends AbstractModel>void fromModel( T model);
}
