package com.milo.parser;

public class MinusOperation extends Operation{

	
	public MinusOperation (String operation)
	{
		super (operation);
	}
	
	public Double execute(Node left, Node right)
	{
		return left.value() - right.value();
	}
	
}
