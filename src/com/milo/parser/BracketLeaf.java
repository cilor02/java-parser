package com.milo.parser;

import java.util.Map;

public class BracketLeaf extends Node {

	protected Double value;
	private String key;
	private Map<String, Node> componentLeafs; 
	

	@Override
	Double value() {
		// TODO Auto-generated method stub
		return componentLeafs.get(key).value();
	}

}
