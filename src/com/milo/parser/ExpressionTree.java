package com.milo.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressionTree {
	
	
	private Map<String, Node> compositeLeafs;
	private static final Integer POWER = 10;
	private static final Integer OF = 9;
	private static final Integer DIVISION = 8;
	private static final Integer MULTIPLICATION = 7;
	private String prefix = "com.milo";
	private static final String  FACTOR_PREFIX = ".factor";
	private static final String  TERM_PREFIX = ".term";

	private static final String[] operators = {"**","of","/","*"};
	

	public ExpressionTree()
	{
		compositeLeafs = new HashMap<String, Node>();
	}
	
	
	public ExpressionTree(Map<String, Node> symbolandNode)
	{
		compositeLeafs = symbolandNode;
	}
	
	Node buildTree(String expression, String prefix)
	{
		if(prefix != null)
		{
		    this.prefix = prefix;
		}
		
		Tokeniser tokeniser = new Tokeniser(expression);
		List<String> tokens = tokeniser.tokenise();
		Integer varCount = 0;
		Integer termCount=0;
		
		for(String operator : operators )
		{
			varCount = buildFactors(tokens, varCount, operator);
		}
		return buildTerms(tokens, varCount);
	}

	private Integer buildFactors(List<String> tokens, Integer varCount,
			String operator) {
		int idx;
		while((idx = tokens.indexOf(operator)) > -1)
		{
			String varName = prefix + FACTOR_PREFIX + varCount++;
			//KeyLeaf keyLeaf = new KeyLeaf(varName);
			OperandPair oPair = new OperandPair();
			
			String leftTkn  = tokens.get(idx - 1);
			String opTkn    = tokens.get(idx);
			String rightTkn = tokens.get(idx + 1);
			
			System.out.println(leftTkn + opTkn + rightTkn + " = " + varName);
			
			if (compositeLeafs.containsKey(leftTkn))
			{
				oPair.setLeft(compositeLeafs.get(leftTkn));
			}
			else
			{
				oPair.setLeft(LeafFactory.createNode (leftTkn));
			}
			
			
			if (compositeLeafs.containsKey(rightTkn))
			{
				oPair.setRight(compositeLeafs.get(rightTkn));
			}
			else
			{
				oPair.setRight(LeafFactory.createNode (rightTkn));
			}
			
			
			oPair.setOperation(operator);
			compositeLeafs.put(varName, oPair);
			tokens.add(idx - 1, varName);
			tokens.remove(leftTkn);
			tokens.remove(opTkn);
			tokens.remove(rightTkn);
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
	
	private Node buildTerms(List<String> tokens, Integer varCount) {
		int idx;
		while((idx = this.findFirstPlusorMinus(tokens)) > -1)
		{
						
			String varName = prefix + TERM_PREFIX + varCount++;
			//KeyLeaf keyLeaf = new KeyLeaf(varName);
			OperandPair oPair = new OperandPair();
			
			String leftTkn  = tokens.get(idx - 1);
			String opTkn    = tokens.get(idx);
			String rightTkn = tokens.get(idx + 1);
			
			System.out.println(leftTkn + opTkn + rightTkn + " = " + varName);
			
			if (compositeLeafs.containsKey(leftTkn))
			{
				oPair.setLeft(compositeLeafs.get(leftTkn));
			}
			else
			{
				oPair.setLeft(LeafFactory.createNode (leftTkn));
			}
			
			
			if (compositeLeafs.containsKey(rightTkn))
			{
				oPair.setRight(compositeLeafs.get(rightTkn));
			}
			else
			{
				oPair.setRight(LeafFactory.createNode (rightTkn));
			}
			
			
			oPair.setOperation(opTkn);
			compositeLeafs.put(varName, oPair);
			tokens.add(idx - 1, varName);
			tokens.remove(leftTkn);
			tokens.remove(opTkn);
			tokens.remove(rightTkn);
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
	
   public static void main(String[] args)
   {
	   ExpressionTree tree = new ExpressionTree();
	   tree.buildTree("a**7+6/b+c*d+e**f+5-2+3**y", null);
   }


public Map<String, Node> getCompositeLeafs() {
	return compositeLeafs;
}

}
