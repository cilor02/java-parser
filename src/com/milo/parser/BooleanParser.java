package com.milo.parser;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class BooleanParser {
    private Map<String,BooleanStatement> truthTable;
    private String expression;
    private final static String COMPOSITE_VAR_PREFIX = "com_milo_var";
    private final static String BOOLEAN_TERM_PREFIX = "com_milo_boolean";
	private static final String[] operators = {"and","or","xor"};
	private String boolVarName;
    protected Map<String, Double> values;

    
    private int compositeVarCount;
    private Map<String, String> compositeVariables = new LinkedHashMap <String, String>();

    public BooleanParser(String expression)
    {
        this.expression = expression;
        truthTable = new HashMap<String, BooleanStatement>();
        values = new HashMap<String, Double>();
    }
    
	public void addVariable(String ref, Double value)
	{
		values.put(ref, value);
	}
    
    private BooleanStatement evaluateBoolExpression (String boolExpression)
    {
        if(truthTable.containsKey(boolExpression))
        {
            return truthTable.get(boolExpression);
        }
                
        return evaluateRelation(boolExpression);
    }
    
    private BooleanStatement evaluateRelation(String exp)
    {
        String[] relationalParts = exp.split("#");
        
        Operation relationalOperation = OperationFactory.createOperation(relationalParts[1]);
        
        RelationalStatement logic = new RelationalStatement(relationalParts[0],relationalParts[2],relationalOperation, values);
        return logic;
    }
    
    public void reduceRelationalBrackets()
    {
        
        int posLBracket = 0;
        int posRBracket = 0;
        
        int cursor = 0;

        for(char token : expression.toCharArray())
        {
            cursor++;
            if(token == '[')
            {
                posLBracket = cursor;
            }
            if(token == ']')
            {
                System.out.println(expression);

                posRBracket = cursor;
                if(posLBracket > 0)
                {
                  String exp = expression.substring(posLBracket - 1, posRBracket )+":"+(posLBracket - 1) +":"+(posRBracket);
                  //System.out.println(exp);
                  
                  assignNextvariableName();
                  
                  BooleanStatement relationalStatement = evaluateRelation(expression.substring(posLBracket, posRBracket -1 ));
                  truthTable.put(boolVarName, relationalStatement);
                  
                storeExpression(exp);
                }
                posLBracket = 0;
                posRBracket = 0;
            }
        }
        reduceExpression();
     System.out.println(expression);
        cursor = 0;
    }
    
    public BooleanStatement reduceBrackets()
    {
        int posLBracket = 0;
        int posRBracket = 0;
        
        int cursor = 0;
        do{
        for(char token : expression.toCharArray())
        {
            cursor++;
            if(token == '[')
            {
                posLBracket = cursor;
            }
            if(token == ']')
            {
                posRBracket = cursor;
                if(posLBracket > 0)
                {
                  String exp = expression.substring(posLBracket - 1, posRBracket )+":"+(posLBracket - 1) +":"+(posRBracket);
                  //System.out.println(exp);
                 //BooleanStatement evaluatedExpression = evaluateBoolExpression(expression.substring(posLBracket, posRBracket -1 ));
                 buildTree(expression.substring(posLBracket, posRBracket -1 ));

                 //this.buildLogicTerms(tokens, operator)
               //System.out.println(evaluatedExpression);
                storeExpression(exp);
                }
                posLBracket = 0;
                posRBracket = 0;
            }
        }
        reduceExpression();
     System.out.println(expression);
        cursor = 0;
        } while(expression.indexOf("[") != -1);
        return buildTree(expression);
    }

    
    /*
     * substitutions to implement bottom up parsing
     * 
     */
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
        
        compositeVariables.put(boolVarName, exp);        
    }

	private void assignNextvariableName() {
		compositeVarCount++;
        StringBuilder compositeVariableName = new StringBuilder();
        compositeVariableName.append(this.COMPOSITE_VAR_PREFIX).append(compositeVarCount);
        boolVarName = compositeVariableName.toString();
	}
    
	BooleanStatement buildTree(String expr)
	{
		Tokeniser tokeniser = new Tokeniser(expr);
		List<String> tokens = tokeniser.tokenise();
		for(String tk : tokens)
		{
		System.out.print("tk : " + tk);
		}
		
		//Integer varCount = 0;
		Integer termCount=0;
		
		for(String operator : operators )
		{
			buildLogicTerms(tokens,operator);
		}
		return truthTable.get(tokens.get(0));
	}
    
    private void buildLogicTerms(List<String> tokens,
			String operator) {
		int idx;
		while((idx = tokens.indexOf(operator)) > -1)
		{			
            assignNextvariableName();

		    //String varName = BOOLEAN_TERM_PREFIX + compositeVarCount++;
			//KeyLeaf keyLeaf = new KeyLeaf(varName);
			LogicStatement logicStatement = new LogicStatement();
			
			String leftTkn  = tokens.get(idx - 1);
			String opTkn    = tokens.get(idx);
			String rightTkn = tokens.get(idx + 1);
			
//			System.out.print( "left " + leftTkn);
//			System.out.print(" op " + opTkn);
//			System.out.print(" right " + rightTkn);
//			System.out.println();


			
			if (truthTable.containsKey(leftTkn))
			{
				logicStatement.setLeft(truthTable.get(leftTkn));
			};
			
			
			if (truthTable.containsKey(rightTkn))
			{
				logicStatement.setRight(truthTable.get(rightTkn));
			};
			
			
			logicStatement.setOperation(OperationFactory.createBooleanOperation( opTkn));
			truthTable.put(boolVarName, logicStatement);
			// used index rather than object refernce to delete token as the wrong token but with the same value was 
			//being deleted. Index allowed us to be precise in deletion. do it backwards! so as not to mess up the positions
			tokens.remove(idx + 1);
			tokens.remove(idx);
			tokens.remove(idx - 1);
			tokens.add(idx - 1, boolVarName);
			//replace composite nodes in statement
			for(String token : tokens)
			{
				System.out.print(token);
				System.out.print(" ");					
			}
			System.out.println();
			
		}
	}
    
    
    public static void main(String[] args)
    {
    	String logicString = new String("[4+a #factorOf# a+b] and [10 + c #multipleOf# 2 + 3]");
    	
        BooleanParser booleanParser = new BooleanParser(logicString);
        booleanParser.addVariable("a", 6.0);
        booleanParser.addVariable("b", 14.0);
        booleanParser.addVariable("c", 5.0);
        
        booleanParser.reduceRelationalBrackets();
        BooleanStatement logic = booleanParser.reduceBrackets();
        System.out.println(logic.state());
    }
}

 