package com.milo.parser;

 public class XorOperation extends BooleanOperation {

	 public Boolean execute(Boolean left, Boolean right)
	 {
		 return (left || right) && (left != right) ;
	 }
	
}
