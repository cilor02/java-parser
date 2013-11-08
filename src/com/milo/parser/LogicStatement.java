package com.milo.parser;

public class LogicStatement extends BooleanStatement 
{
private BooleanOperation operation;
private BooleanStatement left;
private BooleanStatement right;

public LogicStatement ()
{
	
}

public BooleanOperation getOperation() 
{
		return operation;
	}

	public void setOperation(BooleanOperation operation) 
	{
		this.operation = operation;
	}

	public BooleanStatement getLeft() 
	{
		return left;
	}

	public void setLeft(BooleanStatement left) 
	{
		this.left = left;
	}

	public BooleanStatement getRight() 
	{
		return right;
	}

	public void setRight(BooleanStatement right) 
	{
		this.right = right;
	}



public Boolean state()
{
	return operation.execute(left.state(),right.state());
}
	
}
