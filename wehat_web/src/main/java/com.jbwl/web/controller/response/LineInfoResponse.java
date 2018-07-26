package com.jbwl.web.controller.response;

import com.jbwl.web.dao.SiteInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jipeng
 * @Description:
 * @Date: Created in 2018/6/27 9:58
 */
public class LineInfoResponse implements Serializable {
    private static final long serialVersionUID = -1;

    private String lineName;
    private String startTime;
    private String endTime;

    List<SiteInfo> sites = new ArrayList<>();

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

    public List<SiteInfo> getSites() {
        return sites;
    }

    public void setSites(List<SiteInfo> sites) {
        this.sites = sites;
    }

    public void addSite(SiteInfo site){
        this.sites.add(site);
    }

    public void addSite(long code,String siteName){
        sites.add(new SiteInfo(code,siteName));
    }


}
