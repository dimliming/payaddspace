package com.payadd.polymer.auth.protocol;

import com.payadd.framework.common.extension.ExtensionDescription;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.auth.layer.AuthProduct;
import com.payadd.polymer.auth.layer.AuthProtocol;
import com.payadd.polymer.model.common.RawMessage;
import com.payadd.polymer.model.common.Result;

@ExtensionDescription(code="common",name="通用认证接口对接协议")
public class CommonAuthProtocol implements AuthProtocol {
	private AuthProduct product;
	
	/**
	 * 在调用这个方法前，应该在controller中将消息转成RawMessage对象，
	 * */
	public Result auth(DatabaseFacade facade,RawMessage msg) {
		//1.所有报文字段校验，如果通不过，组装错误信息到Result，返回
		//2.验证签名，验证不通过就组装Result，返回
		//3.组装MerchantMessage，报文类型设置为1，并保存到数据库中(需要保存后就直接commit)
		//4.组装Trade，调用product.auth
		//5.如果product返回成功，组装成功报文，放到Result中
		//6.如果product返回失败，组装失败报文，放到result中
		//8.更新MerchantMessage的反馈报文字段
		//7.返回Result
		return null;
	}

	public Result enquiry(DatabaseFacade facade,RawMessage msg) {
		//1.所有报文字段校验，如果通不过，组装错误信息到Result，返回
		//2.验证签名，验证不通过就组装Result，返回
		//3.组装MerchantMessage，报文类型设置为2，并保存到数据库中(需要保存后就直接commit)
		//4.调用product。enquiry
		//5.根据返回的结果，组装反馈报文，放到Result中，
		//6.更新MerchantMessage的反馈报文字段
		//7.返回Result
		return null;
	}

}
