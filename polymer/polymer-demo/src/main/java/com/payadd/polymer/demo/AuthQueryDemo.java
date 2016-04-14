package com.payadd.polymer.demo;


import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 验证订单查询
 * Created by qjj on 15-11-24.
 */
public class AuthQueryDemo {

    //每个商户分配md5盐值
    static String signKey="68f1ba2603894da1b946b2541947b78c";
    //分配商户号
    static String merid="310012171000001";

    public static void main(String[] args) throws Exception {

        Map<String, String> contentData = new TreeMap<String, String>();
        contentData.put("version", "1.0.0");
        contentData.put("encoding", "UTF-8");

        //01 认证订单查询
        contentData.put("txn_type", "01");
        contentData.put("merId", merid);
        //商户原交易订单号
        contentData.put("merchant_code", "310012171000001");
        //商户原交易订单时间
        contentData.put("order_no", "20151217203100");
       String sign= signData(contentData);
        contentData.put("signature",sign);
      HttpClient client = new HttpClient("http://127.0.0.1:8080/auth.htm",6000,60000);
        client.send(contentData,"utf-8");
       String respons=    client.getResult();
        System.out.println(URLDecoder.decode(respons,"utf-8"));
    }

    public static String signData(Map<String, String> contentData) {
        Map.Entry<String, String> obj = null;
        Map<String, String> submitFromData = new TreeMap<String, String>();
        for (Iterator<?> it = contentData.entrySet().iterator(); it.hasNext();) {
            obj = (Map.Entry<String, String>) it.next();
            String value = obj.getValue();
            if (!Strings.isNullOrEmpty(value)) {
                // 对value值进行去除前后空处理
                submitFromData.put(obj.getKey(), value.trim());
            }
        }

      String signData = coverMap2String(submitFromData)+"&signkey="+signKey;
       String sign = Md5Utils.md5(signData.getBytes(Charsets.UTF_8));
        return sign;
    }
    public static String coverMap2String(Map<String, String> data) {
        TreeMap tree = new TreeMap();
        Iterator it = data.entrySet().iterator();

        StringBuffer sf1 = new StringBuffer();

        while(it.hasNext()) {
            Map.Entry en = (Map.Entry)it.next();
            sf1.append(en.getKey() + "=" + en.getValue() + "&");
        }

        return sf1.substring(0, sf1.length() - 1);
    }
}
