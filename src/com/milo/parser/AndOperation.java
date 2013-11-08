package com.milo.parser;

public class AndOperation extends BooleanOperation {

    public Boolean execute(Boolean left, Boolean right)
	{
		return left && right;
	}
	
}
