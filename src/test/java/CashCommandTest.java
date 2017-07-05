import atm.CashCommand;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class CashCommandTest {
    @Test
    public void addCommandTest() throws Exception {
        CashCommand command = new CashCommand("+ USD 100 5");
        assertThat(command.isCorrectCommand(), is(true));
    }

    @Test
    public void getCommandTest() throws Exception {
        CashCommand command = new CashCommand("- USD 100");
        assertThat(command.isCorrectCommand(), is(true));
    }

    @Test
    public void printCommandTest() throws Exception {
        CashCommand command = new CashCommand("?");
        assertThat(command.isCorrectCommand(), is(true));
    }

    @Test
    public void correctCommandTest1() throws Exception {
        CashCommand command = new CashCommand("+ USDD 100 5");
        assertThat(command.isCorrectCommand(), is(false));
    }

    @Test
    public void correctCommandTest2() throws Exception {
        CashCommand command = new CashCommand("+ USD 20 10");
        assertThat(command.isCorrectCommand(), is(false));
    }

    @Test
    public void correctCommandTest3() throws Exception {
        CashCommand command = new CashCommand("+ USD 2000 10");
        assertThat(command.isCorrectCommand(), is(false));
    }

    @Test
    public void correctCommandTest4() throws Exception {
        CashCommand command = new CashCommand("+ USD500 10");
        assertThat(command.isCorrectCommand(), is(false));
    }

    @Test
    public void correctCommandTest5() throws Exception {
        CashCommand command = new CashCommand("+USD 100 5");
        assertThat(command.isCorrectCommand(), is(false));
    }

    @Test
    public void correctCommandTest6() throws Exception {
        CashCommand command = new CashCommand("+ USD 100 -50");
        assertThat(command.isCorrectCommand(), is(false));
    }

    @Test
    public void correctCommandTest7() throws Exception {
        CashCommand command = new CashCommand("- USD500");
        assertThat(command.isCorrectCommand(), is(false));
    }

    @Test
    public void correctCommandTest8() throws Exception {
        CashCommand command = new CashCommand("-USD 500");
        assertThat(command.isCorrectCommand(), is(false));
    }

    @Test
    public void correctCommandTest9() throws Exception {
        CashCommand command = new CashCommand("- USDD 500");
        assertThat(command.isCorrectCommand(), is(false));
    }

    @Test
    public void correctCommandTest10() throws Exception {
        CashCommand command = new CashCommand("- USD -500");
        assertThat(command.isCorrectCommand(), is(false));
    }

    @Test
    public void correctCommandTest11() throws Exception {
        CashCommand command = new CashCommand("? usd");
        assertThat(command.isCorrectCommand(), is(false));
    }
}
