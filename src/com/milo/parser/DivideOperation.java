package com.milo.parser;

public class DivideOperation extends Operation{

	
	public DivideOperation (String operation)
	{
		super(operation);		
	}
	
	public Double execute(Node left, Node right)
	{
		return left.value() / right.value();
	}
	
}
