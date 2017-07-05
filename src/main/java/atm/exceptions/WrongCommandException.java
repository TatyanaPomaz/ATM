package atm.exceptions;

public class WrongCommandException extends Exception {
  @Override
  public String getMessage() {
    return "ERROR. Entered an invalid command";
  }
}
