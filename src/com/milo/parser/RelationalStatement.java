package com.milo.parser;

import java.util.Map;

public class RelationalStatement extends BooleanStatement
{
	String exp1;
	String exp2;
	Operation operation;
	private Map<String, Double> values;

public RelationalStatement(String exp1, String exp2, Operation operation, Map<String, Double> values) {
		super();
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.operation = operation;
		this.values = values;
	}

Boolean state()
{
	BracketExpressionTree tree2 = new BracketExpressionTree(exp2,values);
	tree2.reduceBrackets();
	
	BracketExpressionTree tree1 = new BracketExpressionTree(exp1,values);
	tree1.reduceBrackets();
	
	return operation.execute(tree1.getRoot(), tree2.getRoot()) == 1.0;
	//tree1.evaluate()
}
	
}
