package com.hqyg.dubbo.annotation.exception;

public class DubboxException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4945182130161720390L;
	
	private String message;
    
    public DubboxException() {
    }
    
    public DubboxException(final String message) {
        super(message);
        this.message = message;
    }
    
    public DubboxException(final String message, final Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}