package testing;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

import processing.data.Assignment;
import processing.data.Offer;
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
			/*************************************************************************************
			 ************************************************************************************
			 ** 
			 **		GENERAL TESTS
			 **
			 ************************************************************************************
			 *************************************************************************************/
			
			//1) SetUpConnection
			//-> will be tested with different methods (no possibility to create anything within the database without connection!)
			
			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
			
			//2) specific constructor
			//->3
			
			
			System.out.println("\n\n------------------------------------------------------------------- \n" +
					   "-------------------------------------------------------------------\n\n");
			
			/*************************************************************************************
			 ************************************************************************************
			 **
			 **		USERTESTS
			 **
			 ************************************************************************************ 
			 *************************************************************************************/
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	1) checkLogin
			//
			//////////////////////////////////////////////////////////////////////////////////////
			
			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	2) checkUserExistence 
			//
			//////////////////////////////////////////////////////////////////////////////////////
			
			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	3) createUser
			//
			//////////////////////////////////////////////////////////////////////////////////////
			
			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	4) deleteUser
			//
			//////////////////////////////////////////////////////////////////////////////////////
			
			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//5) updateUser
			//
			//////////////////////////////////////////////////////////////////////////////////////
			
			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//6) updateUserNamePW
			//
			//////////////////////////////////////////////////////////////////////////////////////
			
			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	7) loadUserdata
			//
			//////////////////////////////////////////////////////////////////////////////////////
			
			System.out.println("\n\n------------------------------------------------------------------- \n" +
					   "-------------------------------------------------------------------\n\n");
			
			/*************************************************************************************
			 ************************************************************************************
			 **
			 **		COMPANYTESTS
			 ** 
			 ************************************************************************************
			 *************************************************************************************/
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	1) getCompanyList
			//
			//////////////////////////////////////////////////////////////////////////////////////
			
			HashMap<String, String[]> companyMap = dbhandler.getCompanyList();
			String [] comp = companyMap.get("0");
			System.out.println("Attribut: PositionID \t\t--->\t\t\t\t " + comp[0]);
			System.out.println("Attribut: Category_ID \t\t--->\t\t\t\t " + comp[1]);
			System.out.println("Attribut: Assignment_ID \t--->\t\t\t\t " + comp[2]);
			System.out.println("Attribut: Description \t\t--->\t\t\t\t " + comp[3]);
			System.out.println("Attribut: Amount \t\t--->\t\t\t\t " + comp[4]);
			
			System.out.println("\n\n------------------------------------------------------------------- \n" +
					   "-------------------------------------------------------------------\n\n");
			
			/*************************************************************************************
			 ************************************************************************************
			 **
			 **		POSITIONTESTS
			 **
			 ************************************************************************************
			 *************************************************************************************/
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	1) getPositionList
			//
			//////////////////////////////////////////////////////////////////////////////////////
			HashMap<String, String[]> positionMap = dbhandler.getPositionList("12345");
			String [] posi = positionMap.get("0");
			System.out.println("Attribut: PositionID \t\t--->\t\t\t\t " + posi[0]);
			System.out.println("Attribut: Category_ID \t\t--->\t\t\t\t " + posi[1]);
			System.out.println("Attribut: Assignment_ID \t--->\t\t\t\t " + posi[2]);
			System.out.println("Attribut: Description \t\t--->\t\t\t\t " + posi[3]);
			System.out.println("Attribut: Amount \t\t--->\t\t\t\t " + posi[4]);

			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	2) createPosition
			//
			//////////////////////////////////////////////////////////////////////////////////////
			dbhandler.createPosition("104", "987654987", "...", "99");
			dbhandler.createPosition("999", "987654987", "description999%%99", "99");
			HashMap<String, String[]> positionList = dbhandler.getPositionList("987654987");
			String idPosi = "";
			boolean createPositionWorked = false;
			for (String index : positionList.keySet()) {
				if (positionList.get(index)[1].equals("999") && positionList.get(index)[3].equals("description999%%99")) {
					createPositionWorked = true;
					idPosi = positionList.get(index)[0];
				}
			}
			if (createPositionWorked) {
				System.out.println("createPosition wurde erfolgreich ausgeführt");
			}
			else {
				System.out.println("createPosition wurde fehlerhaft ausgeführt !!!ERROR!!!");
			}
			
			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
			
			////////////////////////////////////////////////////////////////////////////////////
			//
			//	3) deletePosition
			//
			//////////////////////////////////////////////////////////////////////////////////////
			dbhandler.deletePosition(idPosi);
			positionList = dbhandler.getPositionList("987654987");
			boolean deletePositionWorked = true;
			for (String index : positionList.keySet()) {
				if (positionList.get(index)[0].equals(idPosi)) {
					deletePositionWorked = false;
				}
			}
			if (deletePositionWorked) {
				System.out.println("deletePosition wurde erfolgreich ausgeführt");
			}
			else {
				System.out.println("deletePosition wurde fehlerhaft ausgeführt !!!ERROR!!!");
			}
			
			System.out.println("\n\n------------------------------------------------------------------- \n" +
								   "-------------------------------------------------------------------\n\n");
			
			
			/*************************************************************************************
			 ************************************************************************************
			 **
			 **		ASSIGNMENTTESTS
			 **
			 ************************************************************************************
			 *************************************************************************************/
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	1) getAssignments
			//
			//////////////////////////////////////////////////////////////////////////////////////
//			HashMap<String, String[]> assignmentList = dbhandler.getAssignments("max32");
//			String [] assign = assignmentList.get("0");
//			System.out.println("Attribut: Assignment_ID \t--->\t\t\t\t " + assign[0]);
//			System.out.println("Attribut: Description \t\t--->\t\t\t\t " + assign[1]);
//			System.out.println("Attribut: DateOfCreation \t--->\t\t\t\t " + assign[2]);
//			System.out.println("Attribut: Deadline \t\t--->\t\t\t\t " + assign[3]);
//			System.out.println("Attribut: Status \t\t--->\t\t\t\t " + assign[4]);
//			System.out.println("Attribut: Title \t\t--->\t\t\t\t " + assign[5]);
//			System.out.println("Attribut: DueDate \t\t--->\t\t\t\t " + assign[6]);
//			
			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//2) createAssignment
			//
			//////////////////////////////////////////////////////////////////////////////////////
//			String id = dbhandler.createAssignment("max32", "Beschreibung...", "2014.04.28", "2014.06.21", "Titel", "2014.06.28");
//			assignmentList = dbhandler.getAssignments("max32");
//			boolean createAssignWorked = false;
//			for (String index : assignmentList.keySet()) {
//				if (assignmentList.get(index)[0].equals(id)) {
//					createAssignWorked = true;
//				}
//			}
//			if (createAssignWorked) {
//				System.out.println("createAssignment wurde erfolgreich ausgeführt");
//			}
//			else {
//				System.out.println("createAssignment wurde fehlerhaft ausgeführt !!!ERROR!!!");
//			}
			
			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	3) acceptAssignment
			//
			//////////////////////////////////////////////////////////////////////////////////////
//			dbhandler.acceptAssignmentStatus(id);
//			assignmentList = dbhandler.getAssignments("max32");
//			boolean acceptAssignWorked = false;
//			for (String index : assignmentList.keySet()) {
//				if (assignmentList.get(index)[0].equals(id)) {
//					if (assignmentList.get(index)[4].equals("assigned")) {
//						acceptAssignWorked = true;
//					}
//				}
//			}
//			if (acceptAssignWorked) {
//				System.out.println("acceptAssignmentStatus wurde erfolgreich ausgeführt");
//			}
//			else {
//				System.out.println("acceptAssignmentStatus wurde fehlerhaft ausgeführt !!!ERROR!!!");
//			}
			
			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	4) finishAssignmentStatus
			//
			//////////////////////////////////////////////////////////////////////////////////////
//			dbhandler.finishAssignmentStatus(id);
//			assignmentList = dbhandler.getAssignments("max32");
//			boolean finishAssignWorked = false;
//			for (String index : assignmentList.keySet()) {
//				if (assignmentList.get(index)[0].equals(id)) {
//					if (assignmentList.get(index)[4].equals("done")) {
//						finishAssignWorked = true;
//					}
//				}
//			}
//			if (finishAssignWorked) {
//				System.out.println("finishAssignmentStatus wurde erfolgreich ausgeführt");
//			}
//			else {
//				System.out.println("finishAssignmentStatus wurde fehlerhaft ausgeführt !!!ERROR!!!");
//			}
			
			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	5) deleteAssignment
			//
			//////////////////////////////////////////////////////////////////////////////////////
//			boolean deleteAssignWorked = dbhandler.deleteAssignment(id);
//			assignmentList = dbhandler.getAssignments("max32");
//			for (String index : assignmentList.keySet()) {
//				if (assignmentList.get(index)[0].equals(id)) {
//					if (assignmentList.get(index)[4].equals("closed")) {
//						deleteAssignWorked = false;
//					}
//				}
//			}
//			if (deleteAssignWorked) {
//				System.out.println("deleteAssignment wurde erfolgreich ausgeführt");
//			}
//			else {
//				System.out.println("deleteAssignment wurde fehlerhaft ausgeführt !!!ERROR!!!");
//			}
			
			System.out.println("\n\n------------------------------------------------------------------- \n" +
								   "-------------------------------------------------------------------\n\n");
			
			/*************************************************************************************
			 ************************************************************************************
			 **
			 **		OFFERTESTS
			 **
			 ************************************************************************************
			 *************************************************************************************/
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	1) getOffer
			//
			//////////////////////////////////////////////////////////////////////////////////////
//			 HashMap<String,String[]> offerMap = dbhandler.getOffer("12345");
//			 String [] offer = offerMap.get("0");
//			System.out.println("Attribut: OfferID \t\t--->\t\t\t\t " + offer[0]);
//			System.out.println("Attribut: AssignmentID \t\t--->\t\t\t\t " + offer[1]);
//			System.out.println("Attribut: CompanyID \t\t--->\t\t\t\t " + offer[2]);
//			System.out.println("Attribut: Price \t\t--->\t\t\t\t " + offer[3]);
//			System.out.println("Attribut: AmountOfTimeNeeded \t--->\t\t\t\t " + offer[4]);
//			System.out.println("Attribut: Description \t\t--->\t\t\t\t " + offer[5]);
//			System.out.println("Attribut: Datum \t\t--->\t\t\t\t " + offer[6]);
//			System.out.println("Attribut: Status \t\t--->\t\t\t\t " + offer[7]);
			 
			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	2) acceptOffer
			//
			//////////////////////////////////////////////////////////////////////////////////////
//			dbhandler.acceptOffer(offer[0]);
//			offerMap = dbhandler.getOffer("12345");
//			boolean acceptOfferWorked = false;
//			for (String index : offerMap.keySet()) {
//				if (offerMap.get(index)[0].equals(offer[0])) {
//					if (offerMap.get(index)[7].equals("accepted")) {
//						acceptOfferWorked = true;
//					}
//				}
//			}
//			if (acceptOfferWorked) {
//				System.out.println("acceptOffer wurde erfolgreich ausgeführt");
//			}
//			else {
//				System.out.println("acceptOffer wurde fehlerhaft ausgeführt !!!ERROR!!!");
//			}
//			
//			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
//			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//3) rejectOffer
			//
			//////////////////////////////////////////////////////////////////////////////////////
//			dbhandler.rejectOffer(offer[0]);
//			offerMap = dbhandler.getOffer("12345");
//			boolean rejectOfferWorked = false;
//			for (String index : offerMap.keySet()) {
//				if (offerMap.get(index)[0].equals(offer[0])) {
//					if (offerMap.get(index)[7].equals("rejected")) {
//						rejectOfferWorked = true;
//					}
//				}
//			}
//			if (rejectOfferWorked) {
//				System.out.println("rejectOffer wurde erfolgreich ausgeführt");
//			}
//			else {
//				System.out.println("rejectOffer wurde fehlerhaft ausgeführt !!!ERROR!!!");
//			}
//			
//			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
//			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	4) cancelAllOffer
			//
			//////////////////////////////////////////////////////////////////////////////////////
//			dbhandler.cancelAllOffer("12345");
//			offerMap = dbhandler.getOffer("12345");
//			boolean cancelAllOfferWorked = true;
//			for (String index : offerMap.keySet()) {
//				if (offerMap.get(index)[0].equals(offer[0])) {
//					if (!(offerMap.get(index)[7].equals("CanceledByAssignment"))) {
//						cancelAllOfferWorked = false;
//					}
//				}
//			}
//			if (cancelAllOfferWorked) {
//				System.out.println("cancelAllOffer wurde erfolgreich ausgeführt");
//			}
//			else {
//				System.out.println("cancelAllOffer wurde fehlerhaft ausgeführt !!!ERROR!!!");
//			}
//			
//			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
//			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	5) cancelAllOtherOffer
			//
			//////////////////////////////////////////////////////////////////////////////////////
			//TODO
//			
//			System.out.println("\n\n ------------------------------------------------------------------- \n\n");
//			
			//////////////////////////////////////////////////////////////////////////////////////
			//
			//	6) getSpecifcOffer
			//
			//////////////////////////////////////////////////////////////////////////////////////
//			HashMap<String,String[]> oMap = dbhandler.getSpecificOffer(offer[0]);
//			 String [] offerarray = oMap.get("0");
//			System.out.println("Attribut: OfferID \t\t--->\t\t\t\t " + offerarray[0]);
//			System.out.println("Attribut: AssignmentID \t\t--->\t\t\t\t " + offerarray[1]);
//			System.out.println("Attribut: CompanyID \t\t--->\t\t\t\t " + offerarray[2]);
//			System.out.println("Attribut: Price \t\t--->\t\t\t\t " + offerarray[3]);
//			System.out.println("Attribut: AmountOfTimeNeeded \t--->\t\t\t\t " + offerarray[4]);
//			System.out.println("Attribut: Description \t\t--->\t\t\t\t " + offerarray[5]);
//			System.out.println("Attribut: Datum \t\t--->\t\t\t\t " + offerarray[6]);
//			System.out.println("Attribut: Status \t\t--->\t\t\t\t " + offerarray[7]);
//		
			
		} catch (Exception e) {
			//Just print stack trace to find errors with internal tests
			e.printStackTrace();
		}
		
		
	}

}
