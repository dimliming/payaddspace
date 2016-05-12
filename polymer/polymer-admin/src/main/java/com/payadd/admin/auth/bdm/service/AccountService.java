package com.payadd.admin.auth.bdm.service;

import org.springframework.stereotype.Service;

import com.payadd.polymer.base.BaseService;
import com.payadd.polymer.model.acc.Account;

@Service("accountService")
public class AccountService extends BaseService{
	public int insert(Account entity){
		return facade.insert(entity);
	}
	public int update(Account entity){
		return facade.update(entity);
	}
}
