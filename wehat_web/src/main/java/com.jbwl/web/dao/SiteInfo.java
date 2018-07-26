package com.jbwl.web.dao;

import java.util.Date;

/**
 * @Author: jipeng
 * @Description: 站台信息
 * @Date: Created in 2018/6/27 10:03
 */
public class SiteInfo extends BaseInfoDAO {

    private Long stationID;
    private String siteName;
    private String position;
    private String joinBus;

    public SiteInfo(){ }

    public SiteInfo(Long stationID,String siteName){
        this.stationID = stationID;
        this.siteName = siteName;
    }


    public Long getStationID() {
        return stationID;
    }

    public void setStationID(Long stationID) {
        this.stationID = stationID;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJoinBus() {
        return joinBus;
    }

    public void setJoinBus(String joinBus) {
        this.joinBus = joinBus;
    }
}
