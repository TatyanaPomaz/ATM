package atm.exceptions;

public class NotEnoughMoneyException extends Exception {
  @Override
  public String getMessage() {
    return "ERROR. Not enough money.";
  }
}
