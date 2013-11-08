package com.milo.parser;

 public class EqualOperation extends Operation{

	EqualOperation (String operation)
	{
		super(operation);
	}
	 
	public Double execute(Node left, Node right)
	{
		return (left.value().equals(right.value())) ? 1.0 : 0.0;
	}
	
}
