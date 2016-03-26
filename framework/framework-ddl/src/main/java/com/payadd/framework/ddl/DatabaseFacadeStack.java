package com.payadd.framework.ddl;

import java.util.Stack;

public class DatabaseFacadeStack {
	private static ThreadLocal<Stack<DatabaseFacade>> stackThreadLocal  = new ThreadLocal<Stack<DatabaseFacade>>();
	
	public static boolean empty(){
		Stack<DatabaseFacade> stack = stackThreadLocal.get();
		if (stack==null||stack.isEmpty())return true;
		else return false;
	}
	public static DatabaseFacade peek(){
		Stack<DatabaseFacade> stack = stackThreadLocal.get();
		if (stack==null||stack.isEmpty())return null;
		System.out.println("current stack size="+stack.size());
		return stack.peek();
	}
	
	public static DatabaseFacade pop(){
		Stack<DatabaseFacade> stack = stackThreadLocal.get();
		if (stack==null||stack.isEmpty())return null;
		System.out.println("current stack size="+stack.size());
		return stack.pop();
	}
	public static DatabaseFacade push(DatabaseFacade facade){
		Stack<DatabaseFacade> stack = stackThreadLocal.get();
		if (stack==null||stack.isEmpty()){
			stack = new Stack<DatabaseFacade>();
			stackThreadLocal.set(stack);
		}
		return stack.push(facade);
	}
}
