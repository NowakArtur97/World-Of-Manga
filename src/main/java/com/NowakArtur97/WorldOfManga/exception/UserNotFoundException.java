package com.NowakArtur97.WorldOfManga.exception;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -599051704218391282L;

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(String message) {
		super(message);
	}
}
