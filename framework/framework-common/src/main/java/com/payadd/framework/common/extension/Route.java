package com.payadd.framework.common.extension;

public @interface Route {
	String code();
	String name();
	Class target();
}
