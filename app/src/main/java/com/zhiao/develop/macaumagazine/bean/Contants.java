package com.zhiao.develop.macaumagazine.bean;

import com.zhiao.develop.macaumagazine.R;

/**
 * author：Administrator on 2017/4/13 11:34
 * company: xxxx
 * email：1032324589@qq.com
 */

public class Contants {

    public static final String APP_CACAHE_DIRNAME = "/webcache";

    public static final String URL = "http://m.macaomagazine.net/api/";

    public static final String NEWS = URL+"article.php";

    public static final String DETAILS = URL+"article_detail.php?";
    public static final String TEXT = "text";

    public static int categoryName[] = {R.string.lastest,R.string.angola,R.string.brazil,R.string.caboverde,R.string.china,R.string.guineabissau,R.string.macau,R.string.mozambique,R.string.portugal,R.string.saotomeandprincipe,R.string.timorleste};
    public static int imageUrl[] = {0,R.drawable.angela,R.drawable.baxi,R.drawable.fodejiao,R.drawable.china,R.drawable.jineiyabishao,R.drawable.macau,R.drawable.moshanbike,R.drawable.putaoya,R.drawable.shengdemei,R.drawable.dongdiwen};
    public static String[] tags = {"0","20","21","22","23","24","28","19","25","26","27"};

    public static final String LOADMORE = "load";
    public static final String REFREASH = "refreash";
    public static final String pageSize = "100";
}
