package com.milo.parser;

 public class OrOperation extends BooleanOperation {

	 public Boolean execute(Boolean left, Boolean right)
	 {
		 return left || right;
	 }
	
}
