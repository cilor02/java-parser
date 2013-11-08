package com.milo.parser;

import java.text.NumberFormat;

public class NumberNodeFactory extends Node {

	protected Double value;
	
    Node createNode(String strNumber)
    {
    	Double dblNumber = Double.parseDouble(strNumber);
        if(Math.ceil(dblNumber) == Math.floor(dblNumber))
        {
        	
        }
        else
        {
        	
        }

    }
	@Override
	Double value() {
		// TODO Auto-generated method stub
		return value;
	}

}
