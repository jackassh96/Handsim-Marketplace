package testing;

import gui.AuftragErstellenPositionenWindow;
import gui.CSPmainWindows;

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

		/*************************************************************************************
		 ************************************************************************************
		 ** 
		 **		GENERAL TESTCLASS FOR CONTROLLER
		 **
		 ************************************************************************************
		 **
		 **
		 **
		 *************************************************************************************/
		

		dbHandler ner = new dbHandler();
		
		String [] x = ner.loadUserData("max32");

		Controller neu = Controller.getInstance();
		neu = neu.init(x, new dbHandler());
		
		System.out.println("\n\n------------------------------------------------------------------- \n" +
				   "-------------------------------------------------------------------\n\n");
		
		/*************************************************************************************
		 ************************************************************************************
		 **
		 **		Tree Tests
		 **
		 ************************************************************************************ 
		 *************************************************************************************/
		
		//////////////////////////////////////////////////////////////////////////////////////
		//
		//	1) buildTreeFormMajorCategories
		//
		//////////////////////////////////////////////////////////////////////////////////////
		
		
		//TODO !!!
		
		System.out.println("\n\n ------------------------------------------------------------------- \n\n");
		
		//////////////////////////////////////////////////////////////////////////////////////
		//
		//	2) buildTreeWithPositions
		//
		//////////////////////////////////////////////////////////////////////////////////////
		

		
		//TODO !!!!
		
		
			}

}
