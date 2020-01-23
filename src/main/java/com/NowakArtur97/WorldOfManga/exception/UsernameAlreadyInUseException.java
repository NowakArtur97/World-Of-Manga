package com.NowakArtur97.WorldOfManga.exception;

public class UsernameAlreadyInUseException extends Exception {

	private static final long serialVersionUID = 7696053849275821125L;

	public UsernameAlreadyInUseException() {
		super();
	}

	public UsernameAlreadyInUseException(String message) {
		super(message);
	}
}
