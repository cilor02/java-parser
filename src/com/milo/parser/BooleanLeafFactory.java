package com.milo.parser;

import java.util.Map;

public class BooleanLeafFactory{

	protected Double value;
	
	
	
    public static Node createBooleanStatement(String strNumber)
    {
    	if(!isNumeric(strNumber))
    	{
    		return new VarNode(strNumber, refs);
    	}
    	else
    	{
    	
    	Double dblNumber = Double.parseDouble(strNumber);
        	return new Leaf(dblNumber);
        }

    }
    
    
    public static boolean isNumeric(String str)
    {
      return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
    
   

}
