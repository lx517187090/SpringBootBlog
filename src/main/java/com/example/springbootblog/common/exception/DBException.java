package com.example.springbootblog.common.exception;

@SuppressWarnings("serial")
public class DBException extends Exception {
	
	public DBException(String msg)
	 {
	  super(msg);
	 }
}
