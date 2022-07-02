package com.bd.tpfinal.utils;

public class DeliveryException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeliveryException (String errorMessage){
		super(errorMessage);
    }
}
