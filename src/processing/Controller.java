package processing;

import gui.CSPmainWindows;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import processing.data.Assignment;
import processing.data.Category;
import processing.data.OfferHandler;
import processing.data.Position;
import processing.data.User;
import processing.data.Company;
import processing.data.AssignmentHandler;
import processing.dataBase.dbHandler;
import processing.helper.DatumFull;


public class Controller {

   /*
	* Attributes
	*/
	
	private User activeUser;
	//WOZU? GUI MUSS NUR DEN CONTROLLER KENNEN
//	private CSPmainWindows mainWindow;	TODO
	private Company[] companyList;
	private Category[] mainCategoryList;
	private Position[] positionList;
	private AssignmentHandler assignmentHandler;
	private dbHandler dbHandler;
	//WARUM ? KEIN ATTRIBUT DES CONTROLLER! TODO
//	private LoginController loginController;

// Singleton methods and attributes

	private static Controller instance;

	
	//TODO WARUM? WOZU SOLLTE DAS BENÖTIGT WERDEN? 
	/**
	 * Constructor is private (Singleton).
	 */
	private Controller() {}

	
	/**
	 * @return Returns the singleton instance of Controller
	 */
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	// Init methods

	/**
	 * Gives the caller the singleton instance and (re)creates the attributes.
	 * ONLY USE FOR THE INITIAL SETUP OR A WANTED RESET OF THE OBJECT
	 * Categories and Companies are already imported in the method, because the importAssignment method already needs them!!
	 * @param userData Data of the active user which is loaded from the data base
	 * @param dbHandler object that connects to the data base
	 * @return Returns the controller singleton instance 
	 * @throws IOException 
	 * @throws SQLException
	 * @throws Exception  
	 */
	//TODO warum den dbHandler übergeben???
	public static Controller init(String[] userData, dbHandler dbHandler) throws SQLException, IOException, Exception {
		instance = Controller.getInstance();
		instance.dbHandler = dbHandler;
		instance.activeUser = instance.importUser("max32"); 

		instance.mainCategoryList = instance.importCategories();
		instance.importCompanyList();
		instance.importAssingments(instance.activeUser.getUserID());
		
		//TODO remove test stuff
		for (Category c : instance.mainCategoryList) {
			System.out.println(c.getTitle());
		}
		
		System.out.println("--------------------------------");
		
		for (Company co : instance.companyList) {
			System.out.println(co.getName());
		}
		
		System.out.println("--------------------------------");
		
		for (Assignment a : instance.assignmentHandler.getAssignmentList()) {
			System.out.println(a.getTitle());
			for (Position p : a.getPositionList()) {
				System.out.println(p.getDescription());
			}
		}
		//insatnce of cotroller must be input! TODO!
//		CSPmainWindows.main(null);
		return instance;
	}

	/**
	 * Creates the active User from the result coming from the data base.
	 * @throws ArrayIndexOutOfBoundsException thrown if wrong string array is put into the method. If it occurs, ensure proper data base extraction.
	 * @param data The User information
	 * @throws IOException 
	 * @throws SQLException
	 * @throws ArrayIndexOutOfBoundsException 
	 */
	private User importUser(String User_id) throws ArrayIndexOutOfBoundsException, SQLException, IOException {
		User buf = null;
		try{
			buf = new User(dbHandler.loadUserData(User_id));
		}catch (ArrayIndexOutOfBoundsException e){
			throw new ArrayIndexOutOfBoundsException("Transmitted string Array is too short. Please check the proper extraction of user data from the data base."); //TODO fix errmsg
		}
		
		return buf;
	}

	/**
	 * TODO finish Method, Treeitems has to be build top-down, therefore a StringArray is needed or the hashMap must be used correctly
	 * This Method imports all Categories and creates the TreeItem Array
	 * @throws SQLException Exception is thrown when a data base connection error occurs.
	 * @throws IOException Exception is thrown when corrupt data is importet from the data base
	 */
	private Category [] importCategories() throws SQLException, IOException { 
			HashMap<String, String[]> dataFromDB = dbHandler.getCategories();
			Category [] result = new Category[dataFromDB.size()];
			String [] buf = new String[dataFromDB.get(String.valueOf(0)).length];
			
			for (int j = 0; j < dataFromDB.size(); j++) {
				
				int i = 0;
				for (String x : dataFromDB.get(String.valueOf(j))) {
					buf[i] = x;	
					i++;
				}
				
				result[j] = new Category(buf[0], buf[1], buf[2]); 
			}
			return result;
	}
	
	/**
	 * TODO finish Method, Treeitems has to be build top-down, therefore a StringArray is needed or the hashMap must be used correctly
	 * This Method imports all Positions 
	 * @throws SQLException Exception is thrown when a data base connection error occurs.
	 * @throws IOException Exception is thrown when corrupt data is importet from the data base
	 */
	
	private Position [] importPositionsForAssignment(String Assignment_ID) throws SQLException, IOException { 
		HashMap<String, String[]> dataFromDB = dbHandler.getPositionList(Assignment_ID);
		Position [] result = new Position[dataFromDB.size()];
		String [] buf = new String[dataFromDB.get(String.valueOf(0)).length];
		
		for (int j = 0; j < dataFromDB.size(); j++) {
			
			int i = 0;
			for (String x : dataFromDB.get(String.valueOf(j))) {
				buf[i] = x;	
				i++;
			}
			result[j] = new Position(buf[0], buf[1], buf[2], buf[3], buf[4]); 
		}
		return result;
}

	/**
	 * TODO purpose of this method
	 * @throws SQLException Exception is thrown when a data base connection error occurs.
	 * @throws IOException Exception is thrown when corrupt data is importet from the data base
	 * @throws Exception
	 */
//	private void importAssingments(String User_ID) throws SQLException, IOException, Exception {
//		
//			this.assignmentHandler = new AssignmentHandler(new Assignment[1]);
//			//get HashMaps from DB
//			HashMap<String,String[]> assignmentDataFromDB = dbHandler.getAssignments(User_ID);
//			
//			//for loop for parsing the HashMap
//			for (int j = 0; j < assignmentDataFromDB.size(); j++) { 
//				Assignment[] temporaryAssignmentList = new Assignment[j+1]; 
//				Assignment temporaryAssignment;
//				//gets Array from HashMap which represents a data record for an assignment
//				String [] buff = assignmentDataFromDB.get(String.valueOf(j));
//				//gets Array from HashMap which represents a data record for an assignment's positionList
//				String temporaryAssignmentID = buff[0];
//				HashMap<String,String[]> positionDataFromDB = this.dbHandler.getPositionList(temporaryAssignmentID);
//				//gets Array from HashMap which represents a data record for an assignment's OfferList
//				HashMap<String,String[]> offerDataFromDB = this.dbHandler.getOffer(temporaryAssignmentID);
//				//creates object out of new data record
//				temporaryAssignment= new Assignment(buff, positionDataFromDB, offerDataFromDB);//TODO Exception spezifizieren, siehe Assignment Constructor
//				//add new object to assigmentList
//				for (int i = 0; i < j; i++){
//					temporaryAssignmentList[i] = this.assignmentHandler.getAssignmentList()[i];
//				}
//				temporaryAssignmentList[j] = temporaryAssignment;
//				this.assignmentHandler.setAssignmentList(temporaryAssignmentList);
//			}
//	}
	private void importAssingments(String User_ID) throws SQLException, IOException, Exception {
		
		HashMap<String,String[]> assignmentDataFromDB = dbHandler.getAssignments(User_ID);
		Assignment [] result = new Assignment[assignmentDataFromDB.size()];
		String [] buf = new String[assignmentDataFromDB.get(String.valueOf(0)).length];
		
		for (int j = 0; j < assignmentDataFromDB.size(); j++) {
			
			int i = 0;
			for (String x : assignmentDataFromDB.get(String.valueOf(j))) {
				buf[i] = x;	
				i++;
			}
			Position [] temp = importPositionsForAssignment(buf[0]);
			result[j] = new Assignment(buf[0], temp, buf[1], buf[2], buf[3], buf[4], buf[5]); 
		}
		this.assignmentHandler = new AssignmentHandler(result);
		}

	

	/**
	 * TODO purpose of this method
	 * @throws SQLException Exception is thrown when a data base connection error occurs.
	 * @throws IOException Exception is thrown when corrupt data is importet from the data base
	 */
	private void importCompanyList() throws SQLException, IOException {
		try{
			//get HashMap from DB
			HashMap<String,String[]> dataFromDB = this.dbHandler.getCompanyList();
			//for loop for parsing the HashMap
			for (int j = 0; j < dataFromDB.size(); j++) { 
				Company[] temporaryCompanyList = new Company[j+1]; 
				Company temporaryCompany;
				//gets Array from HashMap which represents a data record for a company
				String [] buff = dataFromDB.get(String.valueOf(j));
				//creates object out of new data record
				temporaryCompany= new Company(buff);
				//add new object to Controller.companyList
				for (int i = 0; i < j; i++){
					temporaryCompanyList[i] = this.companyList[i];
				}
				temporaryCompanyList[j] = temporaryCompany;
				this.companyList = temporaryCompanyList;
			}
			
		}catch (SQLException e) {
			throw new SQLException("Datenbankfehler beim Importieren der Firmenliste: " + e.getMessage());
		}
	}

// GUI triggered Methods

	/**
	 * Called by GUI, when user changes their user data. Method changes its
	 * actual user and notifies DBHandler of changes
	 * 
	 * @return Returns true when User update was successful
	 * @param updatedUser Updated User item that comes from the GUI
	 * @throws SQLException Exception is thrown when a data base connection error occurs.
	 * @throws IOException Exception is thrown when corrupt data is importet from the data base
	 */
	public void editUser(User updatedUser) throws SQLException, IOException {
		this.activeUser = updatedUser;
		dbHandler.updateUser(activeUser.getUserID(), activeUser.getPasswd(), activeUser.getFirstName(), activeUser.getLastName(), activeUser.getStreet(), activeUser.getNumber(), String.valueOf(activeUser.getPostCode()), activeUser.getCity(), activeUser.geteMail(), activeUser.getPhone(), activeUser.getCompany(), activeUser.getGender());
		dbHandler.setDbUser(activeUser.getUserID());
		dbHandler.setDbPw(activeUser.getPasswd());
	}

	/**
	 * Called by GUI when User selects to delete his Account. 
	 * The user will automatically get logged off and will return to the Login Screen. 
	 * @throws SQLException Exception is thrown when a data base connection error occurs.
	 * @throws IOException Exception is thrown when corrupt data is importet from the data base
	 */
	public void deleteUser() throws SQLException, IOException {
		dbHandler.deleteUser(activeUser.getUserID());
		logOff();
	}
	
	/**
	 * Logs the user off and returns to the login screen
	 */
	public void logOff() {
		// TODO GUI schliessen
//		LoginController l = loginController;
		instance = null;
//		l.showLoginScreen();
	}

	/**
	 * This method creates a new Assignment from data entered into the GUI and appends it to the assignmentHandler's list.
	 * TODO Tobi - implement the assignment creation in GUI (also with positionList)
	 * @param assignmentID All parameters according to the attributes of the assignment object.
	 * @param positionList
	 * @param offerHandler
	 * @param description
	 * @param dateOfCreation
	 * @param deadline
	 * @param status
	 * @param title
	 */
	public void createAssignment(String assignmentID, TreeItem[] positionList,
			OfferHandler offerHandler, String description, DatumFull dateOfCreation,
			DatumFull deadline, String status, String title) {

//		// Creation of the new assignment initialized from the GUI
//		Assignment newAssignment = new Assignment(assignmentID, positionList,
//				offerHandler, description, dateOfCreation, deadline, status,
//				title);

		// Add the new assignment to the Controller's AssignmentList
		Assignment[] newAssignmentList = new Assignment[this.assignmentHandler
				.getAssignmentList().length + 1];
		for (int j = 0; j < this.assignmentHandler.getAssignmentList().length; j++) {
			newAssignmentList[j] = this.assignmentHandler.getAssignmentList()[j];
		}
//		newAssignmentList[this.assignmentHandler.getAssignmentList().length] = newAssignment;
		this.assignmentHandler.setAssignmentList(newAssignmentList);
	}

	/**
	 * TODO finish this Method and JavaDoc
	 * @param assignmentID
	 * @return
	 */
	public TreeItem[] createPositionTree(String assignmentID) {
//		//Creation of the positionTree
		TreeItem[] positionTree = new TreeItem[1];
//		//Import positionList from assignment
//		TreeItem[] positionList = this.assignmentHandler.SearchForID(assignmentID).getPositionList();
//		for (int j = 0; j < positionList.length; j++) {
//			TreeItem temporaryPosition = positionList[j];
//			TreeItem temporaryCategory = Controller.getInstance().searchForCategory(temporaryPosition.getText(3));
//			//TODO create an array containing the Position and the Path
//			//Stopping to pick Categories into the PositionTree when they have already been picked!
//			while(temporaryCategory.getText()==null){
//				
//				 
//			}
//		}
		return positionTree;
	}
	
	/**
	 * TODO right exception?? - Felix: which exception?
	 * method searches for a category using its ID
	 * @param ID
	 * @return TreeItem
	 * @throws SQLException
	 */
	public TreeItem searchForCategory(String ID) {
		for(int j = 0; j<this.mainCategoryList.length; j++){
//			if(this.mainCategoryList[j].getText()==ID){
////				return this.mainCategoryList[j];
//			}
		}
		return null;
	}
	
	/**
	 * TODO Finish this method with exception, if it is needed for searching a Company - Felix: which exception??
	 * @param ID
	 * @return
	 */
	public Company searchForCompany(String ID){
		for(int j = 0; j<this.companyList.length; j++){
			if(this.companyList[j].getCompanyID()==ID){
				return this.companyList[j];
			}
		}
		return null;
	}

	
// Getters
// No setters needed. Only setting possibility is through the init method
	
	public User getUser() {
		return activeUser;
	}
	
	public Company[] getCompanyList() {
		return this.companyList;
	}
	
//	public TreeItem[] getMainCategoryList() throws SQLException {
//		return this.mainCategoryList;
//	}

	public AssignmentHandler getAssignmentHandler() {
		return assignmentHandler;
	}

	public dbHandler getDbHandler() {
		return dbHandler;
	}


}
