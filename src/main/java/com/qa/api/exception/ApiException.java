package com.qa.api.exception;

public class ApiException extends RuntimeException{
	
	public  ApiException(String msg)// constructor of current class
	{
		super(msg);//calling constructor of parent class i.e RuntimeException
		
	}

}
