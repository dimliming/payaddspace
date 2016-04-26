package com.payadd.admin.auth.bdm.service;

import org.springframework.stereotype.Service;

import com.payadd.polymer.base.BaseService;
import com.payadd.polymer.model.bdm.Merchant;

@Service("merchantService")
public class MerchantService extends BaseService{
	public int insert(Merchant entity){
		return facade.insert(entity);
	}
	public int update(Merchant entity){
		return facade.update(entity);
	}
}
