package com.zhiao.develop.macaumagazine.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ymn on 2017/4/13.
 */
public class News  implements Serializable{

    /**
     * states : 0
     * totrecord : 5
     * totpage : 1
     * content : [{"aID":"14810","atitle":"中国路桥年底铺设莫桑比克Maputo-Catembe大桥桥板","adesc":"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n","themeImg":"http://m.macaomagazine.net/","pubdate":"2017-04-12"},{"aID":"14811","atitle":"中国企业向莫桑比克当局提交房地产项目申请","adesc":"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n","themeImg":"http://m.macaomagazine.net/","pubdate":"2017-04-12"},{"aID":"14214","atitle":"澳门特首冀\u201c一带一路\u201d与澳门商貿合作服务平台有更多协作","adesc":"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n","themeImg":"http://m.macaomagazine.net/","pubdate":"2017-04-11"},{"aID":"14218","atitle":"巴西和中国在圣保罗商讨技术创新和互联网","adesc":"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n","themeImg":"http://m.macaomagazine.net/","pubdate":"2017-04-11"},{"aID":"14207","atitle":"中国浙江省电力建设正洽购巴西一水电站","adesc":"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n","themeImg":"http://m.macaomagazine.net/","pubdate":"2017-04-10"}]
     */

    private int states;
    private int totrecord;
    private int totpage;
    /**
     * aID : 14810
     * atitle : 中国路桥年底铺设莫桑比克Maputo-Catembe大桥桥板
     * adesc :






























     * themeImg : http://m.macaomagazine.net/
     * pubdate : 2017-04-12
     */

    private List<ContentBean> content;

    public static News objectFromData(String str) {

        return new Gson().fromJson(str, News.class);
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public int getTotrecord() {
        return totrecord;
    }

    public void setTotrecord(int totrecord) {
        this.totrecord = totrecord;
    }

    public int getTotpage() {
        return totpage;
    }

    public void setTotpage(int totpage) {
        this.totpage = totpage;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean implements Serializable{
        private String aID;
        private String atitle;
        private String adesc;
        private String themeImg;
        private String pubdate;

        public String getAID() {
            return aID;
        }

        public void setAID(String aID) {
            this.aID = aID;
        }

        public String getAtitle() {
            return atitle;
        }

        public void setAtitle(String atitle) {
            this.atitle = atitle;
        }

        public String getAdesc() {
            return adesc;
        }

        public void setAdesc(String adesc) {
            this.adesc = adesc;
        }

        public String getThemeImg() {
            return themeImg;
        }

        public void setThemeImg(String themeImg) {
            this.themeImg = themeImg;
        }

        public String getPubdate() {
            return pubdate;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }
    }
}
