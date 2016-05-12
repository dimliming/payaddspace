package com.payadd.admin.auth.bdm.service;

import org.springframework.stereotype.Service;

import com.payadd.polymer.base.BaseService;
import com.payadd.polymer.model.acc.AccountDetail;

@Service("accountDetailService")
public class AccountDetailService extends BaseService{
	public int insert(AccountDetail entity){
		return facade.insert(entity);
	}
	public int update(AccountDetail entity){
		return facade.update(entity);
	}
}
