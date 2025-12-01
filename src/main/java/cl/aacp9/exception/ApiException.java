package cl.aacp9.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private final String message;
	private final HttpStatus httpStatus;

	public ApiException(String message, HttpStatus httpStatus) {
	    super(message);
	    this.message = message;
	    this.httpStatus = httpStatus;
	 }

	  @Override
	  public String toString() {
	    return message;
	  }

}
