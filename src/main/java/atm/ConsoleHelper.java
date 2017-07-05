package atm;

import atm.exceptions.WrongCommandException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleHelper {
  private StorageMoney storage = StorageMoney.getInstance();

  public void start() {
    while (true) {
      try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        CashCommand command = new CashCommand(reader.readLine());
        command.executeCommand();
      } catch (WrongCommandException ex) {
        System.out.println(ex.getMessage());
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }
  }

}
