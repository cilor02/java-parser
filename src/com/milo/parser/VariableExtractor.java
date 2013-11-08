package com.milo.parser;

import java.util.Map;
/*
 * extract variables from whatever source the questions are being formatted in XML etc
 * 
 * Leave implementation up to class to allow flexibility. So no input parameters all that we require is the 
 * Map of variables and their values
 * 
 */
public interface VariableExtractor
{
	 Map<String, String> extractVariables();
	
}
