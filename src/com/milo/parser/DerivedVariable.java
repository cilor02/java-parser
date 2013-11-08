package com.milo.parser;

import java.util.HashMap;
import java.util.Map;

public class DerivedVariable {

	private Map<String, Double> values;
		private String derivation;
	
	public DerivedVariable( Map<String, Double> values)
	{
		this.values = values;
	}
	
	public Double derive (String expression )
	{
		BracketExpressionTree tree = new BracketExpressionTree(expression, values);
		tree.reduceBrackets();
		return tree.evaluate();
	}
	
	public void addVariable ( String varName, Double value)
	{
		values.put(varName, value);
	}
	
	public void deriveNewVariable(String varName, String expression)
	{
		addVariable(varName, derive(expression));
	}
	
	public static void main (String args[])
	{
		Map<String, Double> values = new HashMap<String, Double>();
		values.put("a", 1.);
		values.put("b", 2.);
		values.put("c", 3.);
		values.put("d", 4.);
		
		DerivedVariable derivedVar = new DerivedVariable(values);
		derivedVar.deriveNewVariable("e", "a*b**c+4");
		
		System.out.println(values.get("e"));

		
		
	}
	
}
