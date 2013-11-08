package com.milo.parser;

public class Leaf extends Node {

	protected Double value;
	
	public Leaf (Double value)
	{
		this.value = value;
	}
	@Override
	Double value() {
		// TODO Auto-generated method stub
		return value;
	}

}
