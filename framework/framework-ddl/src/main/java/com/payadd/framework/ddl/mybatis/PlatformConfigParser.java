package com.payadd.framework.ddl.mybatis;

import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;


public class PlatformConfigParser {
	private XPathParser parser;
	
	public PlatformConfigParser(InputStream inputStream){
		parser = new XPathParser(inputStream, true, null, new XMLMapperEntityResolver());
	}
	public Map<String,EntityMetadata> parser(){
		Map<String,EntityMetadata> map = new HashMap<String,EntityMetadata>();
		XNode root = parser.evalNode("/ibatis-mappings");
		for (XNode classNode:root.getChildren()){
			EntityMetadata em = parseClassNode(classNode);
			map.put(em.getEntityName(), em);
		}
		
		return map;
	}
	private EntityMetadata parseClassNode(XNode classNode){

		EntityMetadata em = new EntityMetadata();
		em.setClassName(classNode.getStringAttribute("name"));
		em.setTable(classNode.getStringAttribute("table"));
		em.setMapId(classNode.getStringAttribute("map-id"));
		em.setSecurityId(classNode.getStringAttribute("security-id"));
		String entityName = classNode.getStringAttribute("entity-name");
		//如果entity-name是空的，那么默认使用class的name作为entity-name
		if (entityName==null)entityName = em.getClassName();
		em.setEntityName(entityName);
		
		return em;


	}
	
}
