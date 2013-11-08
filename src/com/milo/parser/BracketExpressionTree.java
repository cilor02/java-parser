package com.milo.parser;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BracketExpressionTree {
	
	private String expression;
	private Node root;
	private final static String COMPOSITE_BRACKET_PREFIX = "com_milo_var";
	private int varCount;
	private Map<String, String> compositeVariables = new LinkedHashMap <String, String>();
	
	private String varName;
    protected Map<String, Double> values;
	private Map<String, Node> compositeLeafs;
	private static final Integer POWER = 10;
	private static final Integer OF = 9;
	private static final Integer DIVISION = 8;
	private static final Integer MULTIPLICATION = 7;
	private static final String  FACTOR_PREFIX = "com_milo_factor";
	private static final String  TERM_PREFIX = "com_milo_term";

	private static final String[] operators = {"**","of","/","*"};
	
	public BracketExpressionTree(String expression)
	{
		this.expression = expression;
		compositeLeafs = new HashMap<String, Node>();
		values = new HashMap<String, Double>();
	}
	
	public BracketExpressionTree(String expression, Map<String, Double> values )
	{
		this.expression = expression;
		compositeLeafs = new HashMap<String, Node>();
		this.values = values;
	}
	
	public Node getRoot() {
		return root;
	}

	public void addVariable(String ref, Double value)
	{
		values.put(ref, value);
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

                  //Node rootExp = tree.buildTree(exp);
                  buildTree(expression.substring(posLBracket, posRBracket-1 ));
                  
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
		root = buildTree(expression);
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
		compositeVariables.put(varName, exp);		
	}
	

	
	Node buildTree(String expr)
	{
		Tokeniser tokeniser = new Tokeniser(expr);
		List<String> tokens = tokeniser.tokenise();
		//Integer varCount = 0;
		Integer termCount=0;
		
		for(String operator : operators )
		{
			varCount = buildFactors(tokens,operator);
		}
		return buildTerms(tokens);
	}

	private Integer buildFactors(List<String> tokens,
			String operator) {
		int idx;
		while((idx = tokens.indexOf(operator)) > -1)
		{
			

			
		    varName = FACTOR_PREFIX + varCount++;
			//KeyLeaf keyLeaf = new KeyLeaf(varName);
			OperandPair oPair = new OperandPair();
			
			String leftTkn  = tokens.get(idx - 1);
			String opTkn    = tokens.get(idx);
			String rightTkn = tokens.get(idx + 1);
			
			System.out.println(varName + " = " +  leftTkn + opTkn + rightTkn );
			
			if (compositeLeafs.containsKey(leftTkn))
			{
				oPair.setLeft(compositeLeafs.get(leftTkn));
			}
			else
			{
				oPair.setLeft(LeafFactory.createNode (leftTkn,values));
			}
			
			
			if (compositeLeafs.containsKey(rightTkn))
			{
				oPair.setRight(compositeLeafs.get(rightTkn));
			}
			else
			{
				oPair.setRight(LeafFactory.createNode (rightTkn,values));
			}
			
			
			oPair.setOperation(OperationFactory.createOperation( opTkn));
			compositeLeafs.put(varName, oPair);
			// used index rather than object refernce to delete token as the wrong token but with the same value was 
			//being deleted. Index allowed us to be precise in deletion. do it backwards! so as not to mess up the positions
			tokens.remove(idx + 1);
			tokens.remove(idx);
			tokens.remove(idx - 1);
			tokens.add(idx - 1, varName);
			//replace composite nodes in statement
			for(String token : tokens)
			{
				System.out.print(token);
				System.out.print(" ");					
			}
			System.out.println();
			
		}
		return varCount;
	}
	
	private Node buildTerms(List<String> tokens) {
		int idx;
		while((idx = this.findFirstPlusorMinus(tokens)) > -1)
		{
						
			varName = TERM_PREFIX + varCount++;
			//KeyLeaf keyLeaf = new KeyLeaf(varName);
			OperandPair oPair = new OperandPair();
			
			String leftTkn  = tokens.get(idx - 1);
			String opTkn    = tokens.get(idx);
			String rightTkn = tokens.get(idx + 1);
			
			System.out.println(varName + " = " +  leftTkn + opTkn + rightTkn );
			
			if (compositeLeafs.containsKey(leftTkn))
			{
				oPair.setLeft(compositeLeafs.get(leftTkn));
			}
			else
			{
				oPair.setLeft(LeafFactory.createNode (leftTkn,values));
			}
			
			
			if (compositeLeafs.containsKey(rightTkn))
			{
				oPair.setRight(compositeLeafs.get(rightTkn));
			}
			else
			{
				oPair.setRight(LeafFactory.createNode (rightTkn,values));
			}
			
			
			oPair.setOperation(OperationFactory.createOperation( opTkn));
			compositeLeafs.put(varName, oPair);
			// used index rather than object refernce to delete token as the wrong token but with the same value was 
			//being deleted. Index allowed us to be precise in deletion.
			tokens.remove(idx + 1); // right node
			tokens.remove(idx);  // operation
			tokens.remove(idx - 1); // left node
			tokens.add(idx - 1, varName);

			//replace composite nodes in statement
			for(String token : tokens)
			{
				System.out.print(token);
				System.out.print(" ");					
			}
			System.out.println();
			
		}
		
		return compositeLeafs.get(tokens.get(0));
	}
	
	private int findFirstPlusorMinus(List<String> tokenList)
	{
		int posPlus = tokenList.indexOf("+");
		int posMinus = tokenList.indexOf("-");
		if(posPlus == -1)
		{
			return posMinus;
		}
		
		if(posMinus == -1)
		{
			return posPlus;
		}
		
		return posPlus < posMinus ? posPlus : posMinus;
	}
	
	public Double evaluate()
	{
		return root.value();
	}
	
   public static void main(String[] args)
   {
//	   BracketExpressionTree tree = new BracketExpressionTree("(a+b)*((c-d)/e)*(f+g)+f/((2-y)*6*(u+v))");
	   BracketExpressionTree tree = new BracketExpressionTree("a*b*c*d/a");
       tree.addVariable("a", 2.0);
       tree.addVariable("b", 3.0);
       tree.addVariable("c", 4.0);
       tree.addVariable("d", 5.0);

	   tree.reduceBrackets();
	   System.out.println(tree.evaluate());
   }

}
