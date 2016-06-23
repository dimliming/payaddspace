package com.payadd.admin.auth.bdm.service;

import org.springframework.stereotype.Service;

import com.payadd.polymer.base.BaseService;
import com.payadd.polymer.model.bdm.Merchant;
import com.payadd.polymer.model.com.Product;

@Service("productService")
public class ProductService extends BaseService{
	public int insert(Product entity){
		return facade.insert(entity);
	}
	public int update(Product entity){
		return facade.update(entity);
	}
}
