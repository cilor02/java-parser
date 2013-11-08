package com.milo.parser;

import java.io.File;
import java.util.Map;

import org.dom4j.Document;


public class XMLVariableExtractor implements VariableExtractor  {
	private Document xmlDocument;
	
	public XMLVariableExtractor (File inFile)
	{
		
	}
	
	public XMLVariableExtractor (String xmlString)
	{
		
	}

	@Override
	public Map<String, String> validateVariables() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected Map<String, String> extractVariable()
	{
		return null;
	}
	
	private Map<String, String> generateComposites()
	{
		return null;
	}
	

	private void loadComposites()
	{
		
	}
	
	private void loadAllRules ()
	{
		
	}
	
	
}
