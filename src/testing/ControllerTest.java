package testing;

import gui.AuftragErstellenPositionenWindow;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.swt.widgets.TreeItem;

import processing.Controller;
import processing.data.Category;
import processing.dataBase.dbHandler;

public class ControllerTest {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException, IOException, Exception {
		// TODO Auto-generated method stub

		Controller neu = Controller.getInstance();
		dbHandler ner = new dbHandler();
		String [] x = ner.loadUserData("max32");
		
//		for (String a : x ) {
//			System.out.println(a);
//		}
		neu.init(x, new dbHandler());
		
		
		AuftragErstellenPositionenWindow bla = new AuftragErstellenPositionenWindow();
		
			}

}
