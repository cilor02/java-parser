package com.milo.parser;

public class MultiplyOperation extends Operation{

	private String operation;
	
	public MultiplyOperation (String operation)
	{
		super(operation);		
	}
	
	public Double execute(Node left, Node right)
	{
		return left.value() * right.value();
	}
	
}
