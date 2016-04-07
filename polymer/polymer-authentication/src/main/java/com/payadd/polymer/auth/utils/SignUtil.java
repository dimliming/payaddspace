package com.payadd.polymer.auth.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;

public class SignUtil {
	public static String signData(Map<String, String> contentData,String signKey) {
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

		String signData = coverMap2String(submitFromData) + "&signkey=" + signKey;
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
