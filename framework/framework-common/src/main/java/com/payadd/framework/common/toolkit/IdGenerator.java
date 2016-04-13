package com.payadd.framework.common.toolkit;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.payadd.framework.common.exception.SystemException;

public class IdGenerator {
//	private static Map<String,IdMetadata> idCenterMap = new HashMap<String,IdMetadata>();
	private static Map<String,String> sequenceMap = new HashMap<String,String>();
	private static String MACHINE_ID = null;
	
	public static Long nextLongSequence(Class c) throws SystemException {
		synchronized(c){
			String key = c.getName();
			
			// 获取当前 ID sequence日期和时间戳
			String sequence = getTimeInMillis(key);
			// 返回ID
			return new Long( sequence);
		}
	}

	public static String getDaySequence(Class c){
		synchronized(c){
			return getTimeInMillis(c.getName());
		}
	}
	
	private static String getTimeInMillis(String key) {
		// 获取当前系统时间毫秒数
		long now = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		// 获取当前日期
		String currDate = sdf.format(new Date(now));
		// 获取当天日期0点0分0秒时间点
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		// 获取当前时间到当天0点0分0秒时间点的毫秒数
		Long currentMill = now - cal.getTimeInMillis();
		// 获取sequenceMap中当前bean最后一次获取的ID sequence
		String lastSeq = sequenceMap.get(key);
		if (lastSeq != null && !lastSeq.trim().isEmpty()) {
			// 解析ID sequence中的日期
			String lastDate = lastSeq.substring(0, 8);
			// 解析ID sequence中的时间
			Long previousMill = Long.parseLong(lastSeq.substring(8));
			// 如果是当天再次获取，则获取上一次的sequence值+1，若不是同一天，则重新计数
			if (lastDate.equals(currDate)) {
				if (previousMill >= currentMill){
					currentMill = previousMill + 1;
				}
			}
		}
		// 将最新获取的ID sequence保存到对应的bean键值中
		sequenceMap.put(key, currDate + String.valueOf(currentMill));
		
		String currentMillStr = seqToStr(currentMill);
		// 获取当前服务器MachineId
		String machineId = getMachineId();
		//将machineId插入currentMillStr中
		currentMillStr = currentMillStr.substring(0,5)+machineId+currentMillStr.substring(5,8);
					
		return currDate + currentMillStr;
	}
	//8位序列号
	private static String seqToStr(long timeInMillis) {
		String seq = String.valueOf(timeInMillis);
		int length = seq.length();
		for (int i = length; i < 8; i++) {
			seq = "0" + seq;
		}
		return seq;
	}
	

	private static String getMachineId() throws SystemException {
		// 修改获取本机IP字符串为读取配置文件配置
		if (MACHINE_ID!=null)return MACHINE_ID;
		String ip;
		try {
			InetAddress addr = InetAddress.getLocalHost(); 
			ip = addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new SystemException("获取服务器IP异常", e);
		}
		String[] ips = ip.split("\\.");
		int decIp = Integer.parseInt(ips[ips.length - 1]);
		if(decIp<10){
			MACHINE_ID = "00"+decIp;
		}else if (decIp<100){
			MACHINE_ID = "0"+decIp;
		}else{
			MACHINE_ID = ""+decIp;
		}
		return MACHINE_ID;
	}
	public static void main(String[] args){
		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		System.out.println(format.format(today));
		
		Long l = new Long("3218329121000");
		System.out.println(l.toString());
	}
}
