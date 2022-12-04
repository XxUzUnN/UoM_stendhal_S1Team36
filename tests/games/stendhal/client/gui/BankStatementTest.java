package games.stendhal.client.gui;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import games.stendhal.client.gui.progress.BankStatement;

/*
 * tests that the list of bank names that will be displayed in the travel log are correct
 */
public class BankStatementTest {
	private List<String> testList = Arrays.asList("Semos", "Ados", "Deniran", "Fado", "Nalwor", "Zaras");
	
	@Test
	public void testBankNamesArray() {
		assertEquals(testList, BankStatement.bankStatementNames);
	}
}