package com.payadd.admin.auth.bdm.service;

import org.springframework.stereotype.Service;

import com.payadd.polymer.base.BaseService;
import com.payadd.polymer.model.acc.Recharge;

@Service("rechargeService")
public class RechargeService extends BaseService{
	public int insert(Recharge entity){
		return facade.insert(entity);
	}
	public int update(Recharge entity){
		return facade.update(entity);
	}

}
