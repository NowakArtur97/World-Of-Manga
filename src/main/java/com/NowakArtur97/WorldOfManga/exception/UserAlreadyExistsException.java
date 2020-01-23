package com.NowakArtur97.WorldOfManga.exception;

public class UserAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 7696053849275821125L;

	public UserAlreadyExistsException() {
		super();
	}

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
