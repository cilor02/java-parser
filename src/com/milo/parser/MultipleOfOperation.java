package com.milo.parser;

 public class MultipleOfOperation extends Operation{

	 MultipleOfOperation (String operation)
	{
		super(operation);
	}
	 
	public Double execute(Node left, Node right)
	{
		return (left.value() % right.value() ==0) ? 1.0 : 0.0;
	}
	
}
