package com.milo.parser;

 public class FactorOfOperation extends Operation{

	 FactorOfOperation (String operation)
	{
		super(operation);
	}
	 
	public Double execute(Node left, Node right)
	{
		return (right.value() % left.value() ==0) ? 1.0 : 0.0;
	}
	
}
