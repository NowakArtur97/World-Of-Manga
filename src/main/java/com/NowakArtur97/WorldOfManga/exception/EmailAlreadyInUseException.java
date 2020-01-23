package com.NowakArtur97.WorldOfManga.exception;

public class EmailAlreadyInUseException extends Exception {

	private static final long serialVersionUID = 3194715228466160185L;

	public EmailAlreadyInUseException() {
		super();
	}

	public EmailAlreadyInUseException(String message) {
		super(message);
	}
}
