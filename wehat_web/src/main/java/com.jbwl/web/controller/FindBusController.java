package com.jbwl.web.controller;

import com.jbwl.web.controller.response.BusLineResponse;
import com.jbwl.web.controller.response.LineInfoResponse;
import com.jbwl.web.dao.SiteInfo;

import okhttp3.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jipeng
 * @Description:
 * @Date: Created in 2018/6/25 9:30
 */
@RestController
@RequestMapping("/wechat/bus")
public class FindBusController  {
    private static final Logger logger = LoggerFactory.getLogger(FindBusController.class);

    private static final String bus_base_url = "http://bus.2500.tv/";
    private static final String bus_line_url = bus_base_url + "line.php?line=";
    private static final String bus_line_info_url = bus_base_url + "lineInfo.php?lineID=%s&roLine=";
    private static final String bus_line_station_url = bus_base_url + "station.php?station=%s";
    private static final String bus_line_station_list_url = bus_base_url + "stationList.php?stationID=%s&name=%s";

    private static final String bus_line_status_list_url = bus_base_url + "api_line_status.php";


    private static final String find_first_str = "=";
    private static final String find_last_str = "&";
    private static final String line_status_param = "{\"name\": %s }";

    /**
     * 线路搜索
     * @param line
     * @return
     */
    @RequestMapping(value="line/{line}")
    public Object line(@PathVariable("line")String line){
        List<BusLineResponse> busLineResponses = new ArrayList<>();
        Document doc = null;
        BusLineResponse tempResponse;
        try {
            doc = Jsoup.connect(bus_line_url + line).timeout(3000).get();
            //获取class为istationList的所有节点
            Elements elements = doc.getElementsByClass("istationList");
            if(elements == null || elements.isEmpty()){
                return busLineResponses;
            }

            //循环节点
            for(Element node : elements){
                Elements node_p = node.getElementsByTag("p");
                //判断是否有P节点，没有未图标节点，不处理
                if(node_p == null || node_p.isEmpty()){
                    continue;
                }
                tempResponse = new BusLineResponse();
                //获取到跳转地址
                String href =  node.attr("href");
                String lineId = null;
                try{
                    int start = href.indexOf(find_first_str) + 1;
                    int end = href.indexOf(find_last_str);
                    if(end < 0){
                        lineId = href.substring(start);
                    }else{
                        lineId = href.substring( start,end);
                    }
                }catch (Exception e){
                    logger.error("报错的url =>{},Error =>{} ",href,ExceptionUtils.getStackTrace(e));
                }

                tempResponse.setLineId(lineId);
                //循环找出名称和始发站
                for(Element e : node_p){
                    Elements node_b = e.getElementsByTag("b");
                    //没有b标签是内容
                    if(node_b == null || node_b.isEmpty()){
                       tempResponse.setContent(e.html());
                    }else{
                        tempResponse.setLineName(node_b.get(0).html());
                    }
                }
                busLineResponses.add(tempResponse);
            }
            tempResponse = null;
        } catch (Exception e) {
            logger.error("线路信息异常，line => {}, Error => {}", line, ExceptionUtils.getStackTrace(e));
        }
        return busLineResponses;
    }

    /**
     * 线路信息
     * @param lineId
     * @return
     */
    @RequestMapping(value = "lineInfo/{lineId}")
    public Object lineInfo(@PathVariable("lineId")Long lineId){
        Document doc = null;
        LineInfoResponse response = new LineInfoResponse();
        try {
            doc = Jsoup.connect(String.format(bus_line_info_url,lineId)).timeout(3000).get();
            logger.info("title : {}" , doc.title());

            Element docTitle = doc.selectFirst("dl.fix");
            docTitle.selectFirst("p > b").html();
            String lineName = docTitle.selectFirst("p > b").html();
            response.setLineName(lineName);

            Elements p = docTitle.getElementsByTag("p");
            String pStr = p.get(1).toString();
            pStr = pStr.substring(pStr.indexOf("</span>") + 7);
            String startTime = pStr.substring(0,pStr.indexOf("<span>")).trim();
            response.setStartTime(startTime);
            pStr = pStr.substring(pStr.indexOf("</span>")+ 7);
            String endTime = pStr.substring(0,pStr.indexOf("<label")).trim();
            response.setEndTime(endTime);

            Elements dls =  doc.select("dl.ldItem");

            SiteInfo site;
            for(Element dl : dls){
                Element dlpb = dl.selectFirst("p > b");
                if(dlpb == null){
                    continue;
                }
                site = new SiteInfo();
                Element dlpba = dlpb.selectFirst("a");
                if(dlpba == null){
                    site.setSiteName(dlpb.html());
                }else{
                    site.setSiteName(dlpba.html());
                    String href =  dlpba.attr("href");
                    site.setStationID(Long.valueOf(href.substring(href.indexOf("stationID=") + 10)));
                }
                response.addSite(site);
            }
        } catch (IOException e) {
            logger.error("线路信息异常，lineId => {}, Error => {}", lineId, ExceptionUtils.getStackTrace(e));
        }
        return response;
    }

    /**
     * 站点搜索
     * @param station
     * @return
     */
    @RequestMapping(value = "station/{station}")
    public Object station(@PathVariable("station")String station){
        Document doc = null;
        List<SiteInfo> siteInfos = new ArrayList<>();
        try {
            doc = Jsoup.connect(String.format(bus_line_station_url,station)).timeout(3000).get();
            Elements dls = doc.select("dl.line");
            SiteInfo site;
            for(Element dl : dls){
                Element a = dl.selectFirst("a");
                if(a == null){
                    continue;
                }
                site = new SiteInfo();
                String href = a.attr("href");
                String[] var1 = href.substring(href.indexOf("?")+1).split("&");
                String stationID = var1[0].split("=")[1];
                String name = var1[1].split("=")[1];

                site.setStationID(Long.valueOf(stationID));
                site.setSiteName(name);

                Elements ps = a.select("dt > p");
                site.setPosition(ps.get(1).html());
                site.setJoinBus(ps.get(2).html());

                siteInfos.add(site);
            }
        } catch (IOException e) {
            logger.error("站点搜索异常，station => {}, Error => {}", station, ExceptionUtils.getStackTrace(e));
        }
        return siteInfos;
    }

    /**
     * 查询站台经过的公交车信息
     * @param stationID
     * @param name
     * @return
     */
    @RequestMapping(value = "stationlist/{stationID}/{name}")
    public Object stationInfo(@PathVariable("stationID")Long stationID,@PathVariable("name")String name ){
        List<BusLineResponse> busLineResponses = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.connect(String.format(bus_line_station_list_url, stationID.toString(),name)).timeout(3000).get();
            Elements dls = doc.select("dl.stationList");
            BusLineResponse line;
            for(Element dl : dls){
                line = new BusLineResponse();
                Element a = dl.selectFirst("a.istationList");

                String href = a.attr("href");
                href = href.substring(href.indexOf("?")+1);
                String[] params = href.split("&");
                String[] param;
                for(String item : params){
                    param = item.split("=");
                    if("lineID".equals(param[0])){
                        line.setLineId(param[1]);
                    }else if("roLine".equals(param[0])){
                        line.setRoLineId(param[1]);
                    }else if("num".equals(param[0])){
                        line.setDistanceNum(param[1]);
                    }
                }

                Elements ps = dl.select("dd > p");

                for(Element p : ps){
                    Element b = p.selectFirst("b");
                    if(b == null){
                        line.setContent(p.html());
                        continue;
                    }
                    line.setLineName(b.html());
                }

                busLineResponses.add(line);
            }
        }catch (Exception e){
            logger.error("查询站台经过的公交车信息异常，stationID => {},name => {}, Error => {}", stationID, name, ExceptionUtils.getStackTrace(e));
        }
        return busLineResponses;
    }



    @RequestMapping(value = "lineStatus/{lineId}")
    public Object lineStatus(@PathVariable("lineId")Long lineId){
        try {
            String postBody = String.format(line_status_param,lineId);
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addPart(
                    Headers.of("Content-Disposition", "form-data; name=\"lineID\""),
                    RequestBody.create(null, lineId.toString())).
                    addFormDataPart("lineID", lineId.toString())
                    .build();

            Request request = new Request.Builder()
                    .url(bus_line_status_list_url)
                    .addHeader("Charset","UTF-8")
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                logger.error(MessageFormat.format("获取公交状态异常,参数为:{0}", postBody));
            }

            String body = response.body().string();
            String[] character = body.split("\\\\u");
            String val1 = character[0];
            for(int i=1;i<character.length;i++){
                String code = character[i];
                val1 += String.valueOf((char) Integer.parseInt(code.substring(0,4),16));
                if(code.length()>4){
                    val1 += code.substring(4,code.length());
                }
            }
            return val1;
        }catch (Exception e){
            logger.error("获取公交状态异常，lineId => {}, Error => {}",lineId, ExceptionUtils.getStackTrace(e));
            return null;
        }
    }
}
