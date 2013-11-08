package com.milo.parser;

abstract public class Operation {

	private String operation;
	protected String[] SYMBOLS = {"**","of","/","*","-","+"};
	
	public Operation (String operation)
	{
		this.operation = operation;		
	}
	
	abstract public Double execute(Node left, Node right);
	
}
