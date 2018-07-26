package com.jbwl.web.controller.response;

import java.io.Serializable;

/**
 * @Author: jipeng
 * @Description: 公交路线的输出对象
 * @Date: Created in 2018/6/27 7:25
 */
public class BusLineResponse implements Serializable {
    private static final long serialVersionUID  = 1L;

    private String lineId;

    private String lineName;

    private String content;

    private String roLineId;//反方向的线路ID

    private String distanceNum;//距离站点还有几站


    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRoLineId() {
        return roLineId;
    }

    public void setRoLineId(String roLineId) {
        this.roLineId = roLineId;
    }

    public String getDistanceNum() {
        return distanceNum;
    }

    public void setDistanceNum(String distanceNum) {
        this.distanceNum = distanceNum;
    }
}
