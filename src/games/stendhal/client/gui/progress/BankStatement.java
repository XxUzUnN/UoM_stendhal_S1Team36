package games.stendhal.client.gui.progress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BankStatement {
	//list for bank names, and the contents of each bank
	public static List<String> bankStatementNames = Arrays.asList("Semos", "Ados", "Deniran", "Fado", "Nalwor", "Zaras");
	public static List<String> semosBankStatement = new ArrayList<String>();
	public static List<String> adosBankStatement = new ArrayList<String>();
	public static List<String> deniranBankStatement = new ArrayList<String>();
	public static List<String> fadoBankStatement = new ArrayList<String>();
	public static List<String> nalworBankStatement = new ArrayList<String>();
	public static List<String> zarasBankStatement = new ArrayList<String>();
	
	//contains debug message
	public static List<String> debug = new ArrayList<String>();

	public BankStatement() {}
	
	//returns list of bank names
	public static List<String> getBankStatementNames() {
		return bankStatementNames;
	}
	
	//returns bank statement for each bank based on parameter
	public static List<String> getBankStatement(String item) {
		switch(item) {
		case "Semos":
			semosBankStatement.add("Semos Demo, This string will duplicate on clicking the tab again, but this is expected; its just a feature of how the demo messages added");
			return semosBankStatement;
		case "Ados":
			adosBankStatement.add("Ados Demo, This string will duplicate on clicking the tab again, but this is expected; its just a feature of how the demo messages added\"");
			return adosBankStatement;
		case "Deniran":
			deniranBankStatement.add("Deniran Demo, This string will duplicate on clicking the tab again, but this is expected; its just a feature of how the demo messages added\"");
			return deniranBankStatement;
		case "Fado":
			fadoBankStatement.add("Fado Demo, This string will duplicate on clicking the tab again, but this is expected; its just a feature of how the demo messages added\"");
			return fadoBankStatement;
		case "Nalwor":
			nalworBankStatement.add("Nalwor Demo, This string will duplicate on clicking the tab again, but this is expected; its just a feature of how the demo messages added\"");
			return nalworBankStatement;
		case "Zaras":
			zarasBankStatement.add("Zaras Demo, This string will duplicate on clicking the tab again, but this is expected; its just a feature of how the demo messages added\"");
			return zarasBankStatement;
		default:
			return debug;			
		}		
	}
}
