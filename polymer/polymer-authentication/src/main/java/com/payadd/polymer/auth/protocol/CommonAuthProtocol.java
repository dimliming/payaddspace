package com.payadd.polymer.auth.protocol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.payadd.framework.common.extension.ExtensionDescription;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.auth.layer.AuthProduct;
import com.payadd.polymer.auth.layer.AuthProtocol;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.common.RawMessage;
import com.payadd.polymer.model.common.Result;

@ExtensionDescription(code = "common", name = "通用认证接口对接协议")
public class CommonAuthProtocol implements AuthProtocol {
	private AuthProduct product;

	/**
	 * 在调用这个方法前，应该在controller中将消息转成RawMessage对象，
	 */
	public AuthResult auth(DatabaseFacade facade, RawMessage msg) {
		// 1.所有报文字段校验，如果通不过，组装错误信息到Result，返回
		//1)准备好Result
		AuthResult result = new AuthResult();
		
		// 2)定义必需字段
		String[] strs = { "version", "signature", "encoding", "txn_type", "merchant_code", "order_no", "trade_time",
				"auth_type", "account_type", "account_no" };
		ArrayList<String> list = (ArrayList<String>) Arrays.asList(strs);
		Iterator<String> msgIterator = msg.allField();
		//判断是否为空报文
		if(!msgIterator.hasNext()){
			//系统应答码 110001 报文为空
		}
		while (msgIterator.hasNext()) {

			String key = msgIterator.next();
			String value = msg.getField(key);
			// 判断必需字段是否存在
			if (list.contains(key)) {
				// 如果存在则从list中删除该字段
				list.remove(key);
			}
			switch (key) {
			case "version":
				if (!("1.0.0".equals(value))) {
					//系统应答码110004 xx字段格式错误
					
				}
				break;
			case "signature":

				break;
			case "encoding":
				if("".equals(value)){
					//如果编码为空,默认为UTF-8
					msg.addField(key, "UTF-8");
				}
				break;
			case "txn_type":
				if(!"00".equals(value)){
					//系统应答码110004 xx字段格式错误
				}
				break;
			case "merchant_code":

				break;
			case "order_no":

				break;
			case "trade_time":

				break;
			case "auth_type":

				break;
			case "account_type":

				break;
			case "account_no":

				break;
			case "cert_type":

				break;
			case "cert_no":

				break;
			case "custom_name":

				break;
			case "phone":

				break;

			default:
				break;
			}

		}
		// 2.验证签名，验证不通过就组装Result，返回
		// 3.组装MerchantMessage，报文类型设置为1，并保存到数据库中(需要保存后就直接commit)
		// 4.组装Trade，调用product.auth
		// 5.如果product返回成功，组装成功报文，放到Result中
		// 6.如果product返回失败，组装失败报文，放到result中
		// 8.更新MerchantMessage的反馈报文字段
		// 7.返回Result
		return null;
	}

	public AuthResult enquiry(DatabaseFacade facade, RawMessage msg) {
		// 1.所有报文字段校验，如果通不过，组装错误信息到Result，返回
		// 2.验证签名，验证不通过就组装Result，返回
		// 3.组装MerchantMessage，报文类型设置为2，并保存到数据库中(需要保存后就直接commit)
		// 4.调用product。enquiry
		// 5.根据返回的结果，组装反馈报文，放到Result中，
		// 6.更新MerchantMessage的反馈报文字段
		// 7.返回Result
		return null;
	}

	public static void main(String[] args) {

	}

}
