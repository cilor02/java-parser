package com.milo.parser;

public class PowerOperation extends Operation{

	
	public PowerOperation (String operation)
	{
		super(operation);		
	}
	
	public Double execute(Node left, Node right)
	{
		return  Math.pow(left.value() , right.value());
	}
	
}
