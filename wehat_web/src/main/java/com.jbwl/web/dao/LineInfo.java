package com.jbwl.web.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jipeng
 * @Description: 线路信息
 * @Date: Created in 2018/6/27 10:00
 */
public class LineInfo extends BaseInfoDAO {

    private Long lineId;
    private String lineName;
    private String startTime;
    private String endTime;

    List<SiteInfo> siteInfos = new ArrayList<>();


    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<SiteInfo> getSiteInfos() {
        return siteInfos;
    }

    public void setSiteInfos(List<SiteInfo> siteInfos) {
        this.siteInfos = siteInfos;
    }
}
