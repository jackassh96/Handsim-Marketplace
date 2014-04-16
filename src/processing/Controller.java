package processing;

import gui.CSPmainWindows;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.eclipse.swt.widgets.TreeItem;

import processing.data.Assignment;
import processing.data.OfferHandler;
import processing.data.User;
import processing.data.Company;
import processing.data.Category;
import processing.data.AssignmentHandler;
import processing.dataBase.dbHandler;

public class Controller {

// Attributes
	
	private User activeUser;
	private CSPmainWindows mainWindow; //TODO Erstellung in init-Methode
	private Company[] companyList;
	private TreeItem[] mainCategoryList;
	private AssignmentHandler assignmentHandler;
	private dbHandler dbHandler;
	private LoginController loginController;

// Singleton methods and attributes

	private static Controller instance;

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
	 * 
	 * @param userData Data of the active user which is loaded from the data base
	 * @param dbHandler object that connects to the data base
	 * @return Returns the controller singleton instance 
	 * @throws ArrayIndexOutOfBoundsException Forwards exception from User import
	 */
	public static Controller init(String[] userData, dbHandler dbHandler, LoginController loginController) throws ArrayIndexOutOfBoundsException {
		Controller c = Controller.getInstance();
		c.loginController = loginController;
		c.importUser(userData);
		c.dbHandler = dbHandler;
		c.importAssingments();
		return c;
	}

	/**
	 * Creates the active User from the result coming from the data base.
	 * TODO@throws??
	 * @param data The User information
	 */
	private void importUser(String[] data) throws ArrayIndexOutOfBoundsException {
		try{
			this.activeUser = new User(data);
		}catch (ArrayIndexOutOfBoundsException e){
			throw new ArrayIndexOutOfBoundsException("Transmitted string Array is too short. Please check the proper extraction of user data from the data base.");
		}
		
	}

	/**
	 * TODO finish Method
	 * This Method imports all Categories and creates the TreeItem Array
	 * @return TODO
	 * @throws SQLException Exception is thrown when an import error from the data base occurs.
	 */
	private TreeItem[] importCategories() throws SQLException {

		try {
			//get HashMaps from DB
			HashMap<String, String[]> dataFromDB = this.dbHandler.getCategories();
			this.mainCategoryList = new TreeItem[1];
			//for loop for parsing the HashMap
			for (int j = 0; j < dataFromDB.size(); j++) {
				TreeItem[] temporaryCategoryList = new TreeItem[j];
				TreeItem temporaryCategory;
				//gets Array from HashMap which represents a data record for a category
				String [] buff = dataFromDB.get(String.valueOf(j));
				//TODO creates TreeItem object out of new data record
				temporaryCategory = new TreeItem(this.searchForCategory(buff[1]), 0);
				//add new object to offerList
				for (int i = 0; i < j; i++){
					temporaryPositionList[i] = instance.getMainCategoryList()[1];
				}
				temporaryPositionList[j] = temporaryPosition;
				instance.setMainCategoryList(temporaryPositionList);
		} catch (SQLException e) {
			throw new SQLException("Fehler beim Import der Kategorien: "
					+ e.getMessage());
		}

	}

	/**
	 * TODO are there any Exceptions thrown? If yes, also add proper exception handling to init 
	 */
	private void importAssingments() {
		try{
			this.assignmentHandler = new AssignmentHandler(new Assignment[1]);
			//get HashMaps from DB
			HashMap<String,String[]> assignmentDataFromDB = this.dbHandler.getAssignments(this.activeUser.getUserID());
			
			//for loop for parsing the HashMap
			for (int j = 0; j < assignmentDataFromDB.size(); j++) { 
				Assignment[] temporaryAssignmentList = new Assignment[j+1]; 
				Assignment temporaryAssignment;
				//gets Array from HashMap which represents a data record for an assignment
				String [] buff = assignmentDataFromDB.get(String.valueOf(j));
				//gets Array from HashMap which represents a data record for an assignment's positionList
				String temporaryAssignmentID = buff[0];
				HashMap<String,String[]> positionDataFromDB = this.dbHandler.getPositionList(temporaryAssignmentID);
				//gets Array from HashMap which represents a data record for an assignment's OfferList
				HashMap<String,String[]> offerDataFromDB = this.dbHandler.getOffer(temporaryAssignmentID);
				//creates object out of new data record
				temporaryAssignment= new Assignment(buff, positionDataFromDB, offerDataFromDB);
				//add new object to assigmentList
				for (int i = 0; i < j; i++){
					temporaryAssignmentList[i] = this.assignmentHandler.getAssignmentList()[i];
				}
				temporaryAssignmentList[j] = temporaryAssignment;
				this.assignmentHandler.setAssignmentList(temporaryAssignmentList);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * TODO are there any Exceptions thrown? If yes, also add proper exception handling to init 
	 */
	private void importCompanyList() {
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
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

// GUI triggered Methods

	/**
	 * Called by GUI, when user changes their user data. Method changes its
	 * actual user and notifies DBHandler of changes
	 * 
	 * @return Returns true when User update was successful
	 * @param updatedUser Updated User item that comes from the GUI
	 */
	public boolean editUser(User updatedUser) {
		this.activeUser = updatedUser;
		// TODO DB speicherung triggern
		dbHandler.setDbUser(activeUser.getUserID());
		dbHandler.setDbPw(activeUser.getPasswd());
		return true;
	}

	/**
	 * Called by GUI when User selects to delete his Account. 
	 * The user will automatically get logged off and will return to the Login Screen. 
	 */
	public void deleteUser() {
		// TODO DBHandler: User aus DB löschen
		logOff();
	}
	
	/**
	 * Logs the user off and returns to the login screen
	 */
	public void logOff() {
		// TODO GUI schliessen
		LoginController l = loginController;
		instance = null;
		l.showLoginScreen();
	}

	/**
	 * This method creates a new Assignment from data entered into the GUI and appends it to the assignmentHandler's list.
	 * @param assignmentID All parameters according to the attributes of the assignment object.
	 * @param positionList
	 * @param offerHandler
	 * @param description
	 * @param dateOfCreation
	 * @param deadline
	 * @param status
	 * @param title
	 */
	public void createAssignment(String assignmentID, TreeItem[] positionList, OfferHandler offerHandler, 
								 String description, Date dateOfCreation, Date deadline, String status, String title) {
		
		// Creation of the new assignment initialized from the GUI    
		Assignment newAssignment = new Assignment(assignmentID, positionList, offerHandler, 
												  description, dateOfCreation,deadline, status, title);
		
		// Add the new assigment to the Controller's AssignmentList TODO Ich hoffe, dir ist klar, dass damit die Daten aus dem alten AssignmentArray nicht übertragen werden. Da brauchst du noch ein for-Schleife. So ist nur im letzten Feld des Arrays das Assignment gespeichert.
		Assignment[] newAssignmentList = new Assignment[this.assignmentHandler.getAssignmentList().length + 1];
		newAssignmentList[this.assignmentHandler.getAssignmentList().length] = newAssignment;
		this.assignmentHandler.setAssignmentList(newAssignmentList);
	}

	/**
	 * TODO
	 */
	public void savePosition() {
		// TODO Position in DBHandler speichern
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public TreeItem[] createPositionTree() {
		// TODO
		return null;
	}
	
	/**
	 * TODO right exception??
	 * @param ID
	 * @return TreeItem
	 * @throws SQLException
	 */
	public TreeItem searchForCategory(String ID) throws SQLException{
		if(this.mainCategoryList==null){
			this.importCategories();
		} 
		for(int j = 0; j<this.companyList.length; j++ ){
			if(this.mainCategoryList[j].getText()==ID){
				return this.mainCategoryList[j];
			}
		}
		return null;
	}

	
// Getters

	public User getUser() {
		return activeUser;
	}
	
	/**
	 * If the CompanyList has not been imported from the DB yet, it is done during the first call of this Method. 
	 * That guarantees that the CompanyList is created when an instance really needs it and not during the init()Method (thus DB-Load is optimized)
	 * @return companyList
	 */
	public Company[] getCompanyList() {
		if(this.companyList == null){
			this.importCompanyList();
		}
		return this.companyList;
	}
	
	/**
	 * If the SeviceTree has not been imported from the DB yet, it is done during the first call of this Method. 
	 * That guarantees that the ServiceTree is created when an instance really needs it and not during the init()Method (thus DB-Load is optimized)
	 * @return mainCategoryList
	 * @throws SQLException TODO
	 */
	public TreeItem[] getMainCategoryList() throws SQLException {
		if(this.mainCategoryList==null){
			this.importCategories(); 
		}
		return this.mainCategoryList;
	}

	public AssignmentHandler getAssignmentHandler() {
		return assignmentHandler;
	}

	public dbHandler getDbHandler() {
		return dbHandler;
	}

// Setters TODO
}
