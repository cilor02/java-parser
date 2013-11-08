package com.milo.parser;

import java.util.Map;

public class KeyLeaf extends Node {

	protected Double value;
	private String key;
	private Map<String, Node> componentLeafs; 
	

	
	public KeyLeaf (String key)
	{
		this.key = key;
	}
	@Override
	Double value() {
		// TODO Auto-generated method stub
		return componentLeafs.get(key).value();
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Map<String, Node> getComponentLeafs() {
		return componentLeafs;
	}
	public void setComponentLeafs(Map<String, Node> componentLeafs) {
		this.componentLeafs = componentLeafs;
	}

}
