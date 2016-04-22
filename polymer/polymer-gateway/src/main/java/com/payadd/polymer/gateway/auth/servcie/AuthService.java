package com.payadd.polymer.gateway.auth.servcie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.payadd.framework.common.exception.SystemException;
import com.payadd.polymer.auth.layer.AuthProtocol;
import com.payadd.polymer.base.BaseService;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.common.RawMessage;

@Service("authService")
public class AuthService extends BaseService{
	private AuthProtocol protocol;
	
	public String auth(RawMessage msg){
		AuthResult result = protocol.auth(facade, msg);
		
		return result.getReturnMsg();
	}
	
	public String enquiry(RawMessage msg){
		AuthResult result = protocol.enquiry(facade, msg);
		return result.getReturnMsg();
	}
	
	public static void main(String[] args){
		/*Field[] fields = AuthService.class.getDeclaredFields();
		for (int i=0;i<fields.length;i++){
			Field field = fields[i];
			Class<?>[] implInterfaces = field.getType().getInterfaces();
			System.out.println(implInterfaces[0].getName());
		}*/
		try {
			SimpleDateFormat formate = new SimpleDateFormat("yyyyMMdd");
			Date startDate = formate.parse("20150302");
			Date endDate = formate.parse("20150302");
			
			Date today = new Date(System.currentTimeMillis());
			
			int seq = 1;
			while (endDate.before(today)){
				Calendar c = Calendar.getInstance();
				c.setTime(endDate);
				if (c.get(Calendar.DAY_OF_WEEK)==1){
					startDate = endDate;
				}else if (c.get(Calendar.DAY_OF_WEEK)==7){
					System.out.println(seq+" "+formate.format(startDate)+"--"+formate.format(endDate));
					seq = seq+1;
				}
				c.add(Calendar.DAY_OF_YEAR, 1);
				endDate = c.getTime();
				
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
