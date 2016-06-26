package com.payadd.merchant.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.payadd.framework.common.toolkit.JsonUtil;
import com.payadd.framework.ddl.query.PaginationQuery;
import com.payadd.framework.ddl.query.SimpleQuery;
import com.payadd.polymer.base.BaseController;
import com.payadd.polymer.model.bdm.MerchantUser;
import com.payadd.polymer.model.bdm.ProductRelation;
import com.payadd.polymer.model.com.Product;

@Controller("productController")
@RequestMapping("/product")
public class ProductController extends BaseController {
	@RequestMapping(value = "list")
	public void list(HttpServletRequest request, HttpServletResponse response, Model model, Product entity) {
		String currentPage = request.getParameter("currentPage");
		int pageNum = 1;
		if (currentPage != null) {
			pageNum = Integer.valueOf(currentPage).intValue();
		}
		PaginationQuery pq = new PaginationQuery(facade, entity);
		pq.enableLike();
		pq.setCurrentPage(pageNum);
		List list = pq.list();

		StringBuffer respMsg = new StringBuffer();
		respMsg.append("{");
		respMsg.append("\"status\":\"000000\",\"message\":\"ok\"");
		respMsg.append(",\"currentPage\":\"").append(pq.getCurrentPage()).append("\"");
		respMsg.append(",\"totalPage\":\"").append(pq.getTotalPage()).append("\"");
		respMsg.append(",\"totalRecord\":\"").append(pq.getTotalRecord()).append("\"");
		respMsg.append(",\"list\":").append(JsonUtil.toJson(list));
		respMsg.append("}");
		try {
			String msgStr = respMsg.toString();
			System.out.println(msgStr);
			response.setHeader("Content-Type", "text/html;charset=utf-8");
			response.getWriter().write(msgStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "apply")
	public void apply(HttpServletRequest request, HttpServletResponse response, Model model) {
		StringBuffer respMsg ;
		String productCode = request.getParameter("productCode");
		SimpleQuery sq = new SimpleQuery(facade, Product.class);
		sq.eq("productCode", productCode);
		Product product = (Product) sq.uniqueResult();
		MerchantUser user = getCurrentUser(request);
		ProductRelation pRelation = new ProductRelation();
		pRelation.setMerchantCode(user.getMerchantCode());
		pRelation.setProductCode(product.getProductCode());
		pRelation.setProductName(product.getProductName());
		pRelation.setFee(product.getCommonFee());
		pRelation.setCreateTime(new Timestamp(System.currentTimeMillis()));
		pRelation.setStatus("0");
		// 判断是否已经申请
		SimpleQuery sqRelation = new SimpleQuery(facade, ProductRelation.class);
		sqRelation.eq("productCode", productCode);
		sqRelation.eq("merchantCode", user.getMerchantCode());
		ProductRelation relation = (ProductRelation) sqRelation.uniqueResult();
		if (relation == null) {
			facade.insert(pRelation);
			respMsg = new StringBuffer();
			respMsg.append("{");
			respMsg.append("\"status\":\"000000\",\"message\":\"ok\"");
			respMsg.append("}");
		}else{
			respMsg = new StringBuffer();
			respMsg.append("{");
			respMsg.append("\"status\":\"100001\",\"message\":\"您已经申请了改产品\"");
			respMsg.append("}");
		}
		try {
			String msgStr = respMsg.toString();
			System.out.println(msgStr);
			response.setHeader("Content-Type", "text/html;charset=utf-8");
			response.getWriter().write(msgStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private MerchantUser getCurrentUser(HttpServletRequest request) {
		MerchantUser user = (MerchantUser) request.getSession().getAttribute(LOGIN_USER);
		return user;
	}

}
