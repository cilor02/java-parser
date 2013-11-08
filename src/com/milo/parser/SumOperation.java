package com.milo.parser;

public class SumOperation  extends Operation{

	
	public SumOperation (String operation)
	{
		super(operation);		
	}
	
	public Double execute(Node left, Node right)
	{
		return left.value() + right.value();
	}
	
}
