package com.milo.parser;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.omg.CORBA.CharSeqHolder;

public class BracketParser {
	
	private String expression;
	private final static String COMPOSITE_BRACKET_PREFIX = "com.milo.bracket";
	private int compositeVarCount;
	private Map<String, String> compositeVariables;
	private Map<String, Node> compositeLeafs;
	private ExpressionTree tree;

	public BracketParser(String expression)
	{
		this.expression = expression;
		compositeVariables = new LinkedHashMap <String, String>();
		compositeLeafs = new TreeMap<String, Node>();
		tree = new ExpressionTree(compositeLeafs);
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
                  String expMetaData = expression.substring(posLBracket - 1, posRBracket )+":"+(posLBracket - 1) +":"+(posRBracket);                
                            
          		compositeVarCount++;
        		StringBuilder compositeVariableName = new StringBuilder();
        		compositeVariableName.append(this.COMPOSITE_BRACKET_PREFIX).append(compositeVarCount);
        		
        		// store positional meta data
        		compositeVariables.put(compositeVariableName.toString(), expMetaData);
                // parse innards of bracket
        		// store 'branches' of sub expressions
        		Node rootSubExpression = tree.buildTree(expression.substring(posLBracket, posRBracket - 1 ),compositeVariableName.toString());
        		compositeLeafs.put(compositeVariableName.toString(), rootSubExpression);
                  System.out.println(expression.substring(posLBracket - 1, posRBracket ));
				  //storeExpression(exp);
				}
				posLBracket = 0;
				posRBracket = 0;
			}
		}
		reduceExpression();
	 System.out.println(expression);
		cursor = 0;
		} while(expression.indexOf("(") != -1);
		
		// parse bracketless expressions
		compositeVarCount++;
		StringBuilder finalCompositeVariableName = new StringBuilder();
		finalCompositeVariableName.append(this.COMPOSITE_BRACKET_PREFIX).append(compositeVarCount);
		tree.buildTree(expression,finalCompositeVariableName.toString());
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
		compositeVariableName.append(this.COMPOSITE_BRACKET_PREFIX).append(compositeVarCount);
		compositeVariables.put(compositeVariableName.toString(), exp);
		
		
	}
	
	public static void main(String[] args)
	{
		BracketParser bracketParser = new BracketParser("(a+b)*((c-d)/e)*(f+g)+f/((2-y)*6*(u+v))");
		bracketParser.reduceBrackets();
	}
}

