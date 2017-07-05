package atm;

import atm.exceptions.NotEnoughMoneyException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class StorageMoney implements Serializable {
  private static StorageMoney instance;
  private Map<String, TreeMap<Integer, Integer>> storage = new TreeMap<>(Collections.reverseOrder());

  @SuppressWarnings({"unchecked"})
  private StorageMoney() {
    if (new File("atm.ser").exists()) {
      try {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream("atm.ser"));
        storage = (TreeMap<String, TreeMap<Integer, Integer>>) is.readObject();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public static StorageMoney getInstance() {
    if (instance == null) {
      instance = new StorageMoney();
    }
    return instance;
  }

  public void add(String currency, int value, int amount) {
    TreeMap<Integer, Integer> temp;
    if (storage.containsKey(currency)) {
      temp = storage.get(currency);
      if (temp.containsKey(value)) {
        temp.put(value, temp.get(value).intValue() + amount);
      } else {
        temp.put(value, amount);
      }
    } else {
      temp = new TreeMap<>();
      temp.put(value, amount);
    }

    storage.put(currency, temp);
    System.out.println("OK");
  }

  public void get(String currency, int amount) {
    try {
      Map<Integer, Integer> result = getWithdrawAmount(currency, amount);

      for (Map.Entry entry : result.entrySet()) {
        System.out.println(entry.getKey() + " " + entry.getValue());
      }
      System.out.println("OK");
    } catch (NotEnoughMoneyException ex) {
      System.out.println(ex.getMessage());
    }
  }

  private Map<Integer, Integer> getWithdrawAmount(String currency, int expectedAmount) throws NotEnoughMoneyException {
    TreeMap<Integer, Integer> denomination = getDenomination(currency);

    TreeMap<Integer, Integer> result = new TreeMap<>(Collections.reverseOrder());

    int rest = withdrawMoney(denomination, result, expectedAmount);
    if (rest != 0) {
      throw new NotEnoughMoneyException();
    }

    changeStoredCash(denomination, result);

    return result;
  }

  private TreeMap<Integer, Integer> getDenomination(String currency) throws NotEnoughMoneyException {
    if (!storage.containsKey(currency)) {
      throw new NotEnoughMoneyException();
    }
    return storage.get(currency);
  }

  private void changeStoredCash(Map<Integer, Integer> denomination,  TreeMap<Integer, Integer> result) {
    for (Map.Entry<Integer, Integer> e : result.entrySet()) {
      int count = denomination.get(e.getKey()) - e.getValue();
      if (count == 0) {
        denomination.remove(e.getKey());
      } else {
        denomination.put(e.getKey(), count);
      }
    }
  }

  private int withdrawMoney(Map<Integer, Integer> map, Map<Integer, Integer> result, int sum) {
    Map<Integer, Integer> copymap = new TreeMap<>(Collections.reverseOrder());
    copymap.putAll(map);
    for (Map.Entry<Integer, Integer> e : copymap.entrySet()) {
      if (e.getKey() <= sum && e.getValue() > 0) {
        sum -= e.getKey();
        copymap.put(e.getKey(), e.getValue() - 1);

        if (result.containsKey(e.getKey())) {
          result.put(e.getKey(), result.get(e.getKey()) + 1);
        } else {
          result.put(e.getKey(), 1);
        }

        if (sum <= 0) {
          return sum;
        }
        return withdrawMoney(copymap, result, sum);
      }
    }
    return sum;
  }

  @SuppressWarnings({"unchecked"})
  public void print() {
    for (Map.Entry entry: storage.entrySet()) {
      TreeMap<Integer, Integer> temp = (TreeMap<Integer, Integer>) entry.getValue();
      for (Map.Entry entryTemp: temp.entrySet()) {
        System.out.println(entry.getKey() + " " + entryTemp.getKey() + " " + entryTemp.getValue());
      }
    }

    System.out.println("OK");
  }

  public void save() {
    try {
      ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("atm.ser"));
      os.writeObject(storage);
      os.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
