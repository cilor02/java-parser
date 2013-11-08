package com.milo.parser;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.omg.CORBA.CharSeqHolder;

public class Parser {
	
	private String expression;
	private final static String COMPOSITE_VAR_PREFIX = "com.milo.var";
	private int compositeVarCount;
	private Map<String, String> compositeVariables = new LinkedHashMap <String, String>();

	public Parser(String expression)
	{
		this.expression = expression;
	}
	
	public void reduceBrackets()
	{
		int posLBracket = 0;
		int posRBracket = 0;
		
		int cursor = 0;
		do{
		for(char token : expression.toCharArray())
		{
			cursor++;
			if(token == '(')
			{
				posLBracket = cursor;
			}
			if(token == ')')
			{
				posRBracket = cursor;
				if(posLBracket > 0)
				{
                  String exp = expression.substring(posLBracket - 1, posRBracket )+":"+(posLBracket - 1) +":"+(posRBracket);
                  //System.out.println(exp);
				storeExpression(exp);
				}
				posLBracket = 0;
				posRBracket = 0;
			}
		}
		reduceExpression();
	 System.out.println(expression);
		cursor = 0;
		} while(expression.indexOf("(") != -1);
	}

	private void reduceExpression(){
		Set<String>keySet = this.compositeVariables.keySet();
		Object[] objArray = keySet.toArray();
		String reducedExpression;
		for(int i = objArray.length - 1; i>-1;i-- ){
			String varName = (String)objArray[i];
			String exp = this.compositeVariables.get(varName);
			String[] sections = exp.split(":");
			int startSubExp = Integer.valueOf(sections [1]);
			int endSubExp = Integer.valueOf(sections [2]);
			System.out.println(varName + " = " + sections[0]);

			expression = new StringBuilder(expression).replace(startSubExp, endSubExp, varName).toString();
		}
		compositeVariables.clear();
	}
	private void storeExpression(String exp){
		compositeVarCount++;
		StringBuilder compositeVariableName = new StringBuilder();
		compositeVariableName.append(this.COMPOSITE_VAR_PREFIX).append(".var").append(compositeVarCount);
		compositeVariables.put(compositeVariableName.toString(), exp);
		
		
	}
	
	public static void main(String[] args)
	{
		Parser parser = new Parser("(a+b)*((c-d)/e)*(f+g)+f/((2-y)*6*(u+v))");
		parser.reduceBrackets();
	}
}

