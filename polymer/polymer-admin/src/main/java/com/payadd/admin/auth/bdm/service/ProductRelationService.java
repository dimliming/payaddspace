package com.payadd.admin.auth.bdm.service;

import org.springframework.stereotype.Service;

import com.payadd.polymer.base.BaseService;
import com.payadd.polymer.model.bdm.Merchant;
import com.payadd.polymer.model.bdm.ProductRelation;
import com.payadd.polymer.model.com.Product;

@Service("productRelationService")
public class ProductRelationService extends BaseService{
	public int insert(ProductRelation entity){
		return facade.insert(entity);
	}
	public int update(ProductRelation entity){
		return facade.update(entity);
	}
}
