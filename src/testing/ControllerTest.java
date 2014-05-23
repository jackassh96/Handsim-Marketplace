package testing;

import java.io.IOException;
import java.sql.SQLException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import processing.Controller;
import processing.data.Category;
import processing.dataBase.dbHandler;

public class ControllerTest {

	@SuppressWarnings("static-access")
	public static void main(String[] args){

		/*************************************************************************************
		 ************************************************************************************
		 ** 
		 **		GENERAL TESTCLASS FOR CONTROLLER
		 **
		 ************************************************************************************
		 **
		 **		General test class for controller object containing: 
		 **			-> Category tests
		 **			-> Tree tests
		 **			
		 *************************************************************************************
		 **	
		 **		WARNING: ONLY RUN THIS CLASS ON A TEST DATABASE SYSTEM 
		 **				 	(CREATES INPUT FOR DATABASE TABLES!) 
		 **
		 *************************************************************************************/

		dbHandler ner = new dbHandler();
		Controller instance = Controller.getInstance();
		try {
			String [] x = ner.loadUserData("max32");
			instance = instance.init(x, new dbHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Category[] buf = null;
		try {
			buf = instance.importCategories();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		System.out.println("\n\n------------------------------------------------------------------- \n" +
				   "-------------------------------------------------------------------\n\n");
		
		/*************************************************************************************
		 ************************************************************************************
		 **
		 **		Category Tests
		 **
		 ************************************************************************************ 
		 *************************************************************************************/
		
		//////////////////////////////////////////////////////////////////////////////////////
		//
		//	1) generateSubCategories
		//
		//////////////////////////////////////////////////////////////////////////////////////
		
		Category[] subCategoryList = instance.generateSubCategories(buf);
		for (Category c : subCategoryList) {
			if (c.getParentCategory().equals("-1")) {
				System.out.println("Category = " + c.getTitle());
			}
			for (Category ca : c.getSubCategories()) {
				System.out.println("--> \tSub-Category = " + ca.getTitle());
			}
		}

		System.out.println("\n\n ------------------------------------------------------------------- \n\n");
		
		//////////////////////////////////////////////////////////////////////////////////////
		//
		//	1) generateMajorCategories
		//
		//////////////////////////////////////////////////////////////////////////////////////
		
		Category[] majorCategoryList = instance.seperateMajorCategories(buf);
		for (Category c : majorCategoryList) {
			System.out.println("\nCategory       = " + c.getTitle());
			System.out.println("CategoryID     = " + c.getCategoryID());
			System.out.println("ParentCategory = " + c.getParentCategory());
		}
		
		System.out.println("\n\n ------------------------------------------------------------------- \n\n");
		
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
		Tree testTree = new Tree(new Shell(), SWT.BORDER);
		instance.buildTreeFromMajorCategories(testTree);
		int counter = 0;
		for (TreeItem t : instance.getServiceTreeList()) {
			System.out.println("TreeItem = " + t.getText());
			counter++;
		}
		System.out.println("\n insgesamt = " + counter + " Treeitems wurden erzeugt.");
		

		System.out.println("\n\n ------------------------------------------------------------------- \n\n");
		
		//////////////////////////////////////////////////////////////////////////////////////
		//
		//	2) buildTreeWithPositions
		//
		//////////////////////////////////////////////////////////////////////////////////////
		
		Tree posiTree = new Tree(new Shell(), SWT.BORDER);
		try {
			instance.builTreeWithPositons("12345", posiTree);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int count = 0;
		for (TreeItem t : instance.getPositionTreeList()) {
			System.out.println("TreeItem = " + t.getText());
			count++;
		}
		System.out.println("\n insgesamt = " + count + " Treeitems wurden erzeugt.");
	}
}
