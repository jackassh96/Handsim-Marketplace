package testing;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

import processing.data.Position;
import processing.dataBase.dbHandler;

public class DbTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		dbHandler dbhandler = new dbHandler();
		
		try {
			
			dbhandler.createPosition("99", "12949847", "ganz sch�n viel ...", "12 St�ck");
			dbhandler.createPosition("88", "12949847", "ganz sch�n viel ...", "12 St�ck");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
