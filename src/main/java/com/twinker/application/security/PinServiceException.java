package com.twinker.application.security;

/**
 * Exception class for PIN-related operations in the Twinker application.
 * This class wraps lower-level exceptions from the PIN storage mechanism
 * and provides application-specific error handling.
 *
 * <p>
 * This exception is thrown when:
 * <ul>
 * <li>PIN storage operations fail</li>
 * <li>PIN verification encounters errors</li>
 * <li>PIN existence checks fail</li>
 * <li>PIN removal operations fail</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.application.security.PinService
 */
public class PinServiceException extends RuntimeException {

    /**
     * Constructs a new PinServiceException with the specified detail message and
     * cause.
     *
     * @param message the detail message
     * @param cause   the cause of this exception
     */
    public PinServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
