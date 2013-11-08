package com.milo.parser;

public class OperandPair extends Node {

	protected Node left;
	protected Node right;
	protected Operation operation;
	
	
	@Override
	Double value() {
		// TODO Auto-generated method stub
		return operation.execute(left, right);
	}



	public Node getLeft() {
		return left;
	}



	public void setLeft(Node left) {
		this.left = left;
	}



	public Node getRight() {
		return right;
	}



	public void setRight(Node right) {
		this.right = right;
	}



	public Operation getOperation() {
		return operation;
	}



	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	

}
