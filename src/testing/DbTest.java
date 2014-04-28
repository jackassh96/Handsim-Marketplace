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
			/**
			 * GENERAL TESTS
			 */
			
			//1) SetUpConnection
			//-> will be tested with different methods (no possibility to create anything within the database without connection!)
			
			//2) specific constructor
			//->3
			
			/**
			 * USERTESTS
			 */
			
			//1) checkLogin
			
			//2) checkUserExistence 
			
			//3) createUser

			//4) deleteUser
			
			//5) updateUser
			
			//6) updateUserNamePW
			
			//7) loadUserdata
			
			/**
			 * COMPANYTESTS
			 */
			
			//1) getCompanyList
			
			/**
			 * POSITIONTESTS
			 */
			
			//1) getPositionList
			
			//2) createPosition
			
			//3) deletePosition
			
			/**
			 * OFFERTESTS
			 */
			
			//1) getOffer
			
			//2) acceptOffer
			
			//3) rejectOffer
			
			//4) cancelAllOffer
			
			//5) cancelAllOtherOffer
			
			//6) getSpecifcOffer
			
			/**
			 * ASSIGNMENTTESTS
			 */
			
			//1) getAssignments
			
			//2) createAssignment
			String id = dbhandler.createAssignment("max32", "orifjre", "2014.04.28", "2014.06.21", "title", "2014.06.28");
			System.out.println(id);
			
			//3) acceptAssignment
			
			//4) finishAssignmentStatus
			
			//5) deleteAssignment
			
			
//			dbhandler.createPosition("99", "12949847", "ganz schön viel ...", "12 Stück");
//			dbhandler.createPosition("88", "12949847", "ganz schön viel ...", "12 Stück");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
