package com.milo.parser;

public class OperationFactory{

	public static BooleanOperation createBooleanOperation (String symbol)
	{
		if(symbol.equals("and"))
		{
			return new AndOperation();
		}
		
		if(symbol.equals("or"))
		{
			return new OrOperation();
		}
		
		if(symbol.equals("xor"))
		{
			return new XorOperation();
		}
		return null;
	}
	
	public static Operation createOperation(String symbol)
	{
		
		if(symbol.equals("+"))
		{
			return new SumOperation(symbol);
		}
		if(symbol.equals("-"))
		{
			return new MinusOperation(symbol);
		}
		if(symbol.equals("*"))
		{
			return new MultiplyOperation(symbol);
		}
		if(symbol.equals("/"))
		{
			return new DivideOperation(symbol);
		}
		if(symbol.equals("**"))
		{
			return new PowerOperation(symbol);
		}
		
		if(symbol.equals("gt"))
		{
			return new GreaterThanOperation(symbol);
		}
		
		if(symbol.equals("lt"))
		{
			return new LessThanOperation(symbol);
		}
		if(symbol.equals("=") || symbol.equals("eq"))
		{
			return new EqualOperation(symbol);
		}
		if(symbol.equals("factorOf"))
		{
			return new FactorOfOperation(symbol);
		}
		
		if(symbol.equals("multipleOf"))
		{
			return new MultipleOfOperation(symbol);
		}
		return null;
		
	}
	
}
