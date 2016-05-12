package com.payadd.admin.auth.bdm.service;

import org.springframework.stereotype.Service;

import com.payadd.polymer.base.BaseService;
import com.payadd.polymer.model.bdm.Merchant;
import com.payadd.polymer.model.bdm.MerchantSecurity;

@Service("merchantSecurityService")
public class MerchantSecurityService extends BaseService{
	public int insert(MerchantSecurity entity){
		return facade.insert(entity);
	}
	public int update(MerchantSecurity entity){
		return facade.update(entity);
	}
}
