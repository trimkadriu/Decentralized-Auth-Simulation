package com.tk.domain.exception;

/**
 * DecAuthSimException
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class DecAuthSimException extends Exception {

    public DecAuthSimException() {
        super("A generic error has occurred.");
    }

    public DecAuthSimException(String message) {
        super(message);
    }
}
