package com.milo.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.omg.CORBA.CharSeqHolder;

public class Tokeniser {
	
	private Character latestChar;
	private List<String> tokens;
	private String phrase;
	private int pos;
	
	private StringBuilder currentToken = new StringBuilder(); 

	public Tokeniser(String phrase)
	{
		this.phrase = phrase;
		System.out.print("phrase : " + phrase);

	}
	
	public List<String> tokenise()
	{
		 latestChar = this.getNext();

		tokens = new ArrayList<String>();
		while (latestChar != null)
		{
		switch (latestChar)
			{
			case '+':
			case '-':
			case '/':
			case '*':
			  createOpToken();
			break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				extractNumber();
				break;
			case ' ':
				latestChar = this.getNext();
				break;
			case '_':
				extractVariable();
				break;
			default:
				if(Character.isLetter(latestChar)|| latestChar.equals('_'))
				{
					extractVariable();
				}
				break;
			}
		}
        return this.tokens;
	}

	private Character getNext()
	{
		if( phrase == null || phrase.length() == 0 || phrase.length()<=pos)
		{
			return null;
		}
		Character ch = phrase.charAt(pos);
		pos++;
		return ch;
	}
	
	private void extractVariable()
	{
	
		this.currentToken.append(latestChar);
		 latestChar = getNext();
	  while (latestChar !=null && latestChar !='.' && (latestChar.isDigit(latestChar)||latestChar.isLetter(latestChar)||latestChar =='_') )
	  {
		  this.currentToken.append(latestChar);
		  latestChar = getNext();
	  }
		tokens.add(currentToken.toString());
		currentToken.setLength(0);
	}
	
	private void createOpToken()
	{
		this.currentToken.append(latestChar);

		if(latestChar == '*')
		{
			latestChar = getNext();
			if(latestChar != null && latestChar == '*')
			{
				 this.currentToken.append(latestChar);
				 latestChar = getNext();
			}
		}
		else
		{
			latestChar = getNext();
		}
		tokens.add(currentToken.toString());
		currentToken.setLength(0);
	}

	private void extractNumber()
	{
		 this.currentToken.append(latestChar);
		 latestChar = getNext();
	  while (latestChar !=null && latestChar !='.' && latestChar.isDigit(latestChar))
	  {
		  this.currentToken.append(latestChar);
		  latestChar = getNext();
	  }
	  
	  if(latestChar !=null && latestChar == '.')
	  {
		  this.currentToken.append(latestChar);
		  latestChar = getNext();
		  
		  while ( latestChar != null && latestChar.isDigit(latestChar))
		  {			  
			  this.currentToken.append(latestChar);
			  latestChar = getNext();
		  }
	  }
		tokens.add(currentToken.toString());
		currentToken.setLength(0);
	}
	
	public static void main(String[] args)
	{
		Tokeniser tokeniser = new Tokeniser("436.7 + 6*5**2 + 10.5 / 7.556-a+cd/x");
        List<String>tokens =  tokeniser.tokenise();
        for(String token : tokens)
        {
        	System.out.println(token);
        }
	}
}

