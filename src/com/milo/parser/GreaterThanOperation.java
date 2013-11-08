package com.milo.parser;

 public class GreaterThanOperation extends Operation{

	GreaterThanOperation (String operation)
	{
		super(operation);
	}
	 
	public Double execute(Node left, Node right)
	{
		return (left.value() > right.value()) ? 1.0 : 0.0;
	}
	
}
