package com.milo.parser;

import java.util.Map;

public class VarNode extends Node {

    protected String ref;
    protected Map<String, Double> values;
    
     public VarNode(String ref, Map values)
     {
    	 this.values = values;
    	 this.ref = ref;
     }
	@Override
	Double value() {
		// TODO Auto-generated method stub
		return values.get(ref);
	}
	

}
