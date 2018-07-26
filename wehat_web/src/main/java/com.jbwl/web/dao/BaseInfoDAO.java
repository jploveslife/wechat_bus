package com.jbwl.web.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: jipeng
 * @Description:
 * @Date: Created in 2018/6/28 7:36
 */
public class BaseInfoDAO implements Serializable {
    private static final long serialVersionUID = -1;

    private long id;

    private Date createTime;
    private Date udpateTime;
    private Byte deleteFlag;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUdpateTime() {
        return udpateTime;
    }

    public void setUdpateTime(Date udpateTime) {
        this.udpateTime = udpateTime;
    }

    public Byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Byte deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
