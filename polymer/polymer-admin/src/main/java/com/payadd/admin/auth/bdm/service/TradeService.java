package com.payadd.admin.auth.bdm.service;

import com.payadd.polymer.base.BaseService;
import com.payadd.polymer.model.aut.Trade;

public class TradeService extends BaseService {
	public int insert(Trade entity) {
		return facade.insert(entity);
	}

	public int update(Trade entity) {
		return facade.update(entity);
	}
}
