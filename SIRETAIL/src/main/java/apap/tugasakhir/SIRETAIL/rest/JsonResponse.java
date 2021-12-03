package apap.tugasakhir.SIRETAIL.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.*;

@NoArgsConstructor
public class JsonResponse<T> {

  public ResponseEntity<Result<T>> response(String message) {
    var data = new Result<T>(message, HttpStatus.OK.value(), null);
    ResponseEntity<Result<T>> res = new ResponseEntity<Result<T>>(data, HttpStatus.OK);
    return res;
  }

  public ResponseEntity<Result<T>> response(String message, HttpStatus status) {
    var data = new Result<T>(message, status.value(), null);
    ResponseEntity<Result<T>> res = new ResponseEntity<Result<T>>(data, status);
    return res;
  }

  public ResponseEntity<Result<T>> response(String message, T result) {
    var data = new Result<T>(message, HttpStatus.OK.value(), result);
    ResponseEntity<Result<T>> res = new ResponseEntity<Result<T>>(data, HttpStatus.OK);
    return res;
  }

  public ResponseEntity<Result<T>> response(String message, T result, HttpStatus status) {
    var data = new Result<T>(message, status.value(), result);
    ResponseEntity<Result<T>> res = new ResponseEntity<Result<T>>(data, status);
    return res;
  }

  public Object response(String message, itemDetail temp) {
    return null;
  }
}

