package atm;

import atm.exceptions.WrongCommandException;

public class CashCommand {
  private String[] command;

  public CashCommand(String command) {
    this.command = command.split("\\s+");
  }

  public void executeCommand() throws WrongCommandException {
    if (!isCorrectCommand()) {
      throw new WrongCommandException();
    }

    StorageMoney storage = StorageMoney.getInstance();
    switch (command[0]) {
      case "+":
        storage.add(command[1].toUpperCase(), Integer.parseInt(command[2]), Integer.parseInt(command[3]));
        break;
      case "-":
        storage.get(command[1].toUpperCase(), Integer.parseInt(command[2]));
        break;
      case "?":
        storage.print();
        break;
      case "q":
        storage.save();
        System.exit(0);
        break;
      default:
        throw new WrongCommandException();
    }
  }

  public boolean isCorrectCommand() {
    if (command.length == 0) {
      return false;
    }
    try {
      switch (command[0]) {
        case "+":
          String currency = command[1];
          int value = Integer.parseInt(command[2]);
          int amount = Integer.parseInt(command[3]);
          if (command.length != 4 || currency.length() != 3 || !isValidateValue(value) || value <= 0 || amount <= 0) {
            return false;
          }
          break;
        case "-":
          if (command.length != 3 || command[1].length() != 3 || Integer.parseInt(command[2]) <= 0) {
            return false;
          }
          break;
        case "?":
        case "q":
          if (command.length != 1) {
            return false;
          }
          break;
        default:
          return false;
      }
    } catch (ArrayIndexOutOfBoundsException exception) {
      return false;
    }

    return true;
  }

  private static boolean isValidateValue(int value) {
    int denomination;
    for (int i = 0; i <= 3; i++) {
      denomination = (int) Math.pow(10, i);
      if (value == denomination || value == denomination * 5) {
        return true;
      }
    }
    return false;
  }

}
