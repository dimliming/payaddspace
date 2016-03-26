package com.payadd.framework.ddl.query.criterion;

import java.util.HashMap;
import java.util.Map;

public abstract class MatchMode {
	private final String name;
	private static final Map INSTANCES = new HashMap();

	protected MatchMode(String name) {
		this.name=name;
	}
	public String toString() {
		return name;
	}

	/**
	 * Match the entire string to the pattern
	 */
	public static final MatchMode EXACT = new MatchMode("EXACT") {
		public String toMatchString(String pattern) {
			return pattern;
		}
	};

	/**
	 * Match the start of the string to the pattern
	 */
	public static final MatchMode START = new MatchMode("START") {
		public String toMatchString(String pattern) {
			return pattern + '%';
		}
	};

	/**
	 * Match the end of the string to the pattern
	 */
	public static final MatchMode END = new MatchMode("END") {
		public String toMatchString(String pattern) {
			return '%' + pattern;
		}
	};

	/**
	 * Match the pattern anywhere in the string
	 */
	public static final MatchMode ANYWHERE = new MatchMode("ANYWHERE") {
		public String toMatchString(String pattern) {
			return '%' + pattern + '%';
		}
	};

	static {
		INSTANCES.put( EXACT.name, EXACT );
		INSTANCES.put( END.name, END );
		INSTANCES.put( START.name, START );
		INSTANCES.put( ANYWHERE.name, ANYWHERE );
	}

	private Object readResolve() {
		return INSTANCES.get(name);
	}

	/**
	 * convert the pattern, by appending/prepending "%"
	 */
	public abstract String toMatchString(String pattern);
}
