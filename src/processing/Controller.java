package processing;

import gui.CSPmainWindows;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import org.eclipse.swt.widgets.TreeItem;

import processing.data.Assignment;
import processing.data.OfferHandler;
import processing.data.User;
import processing.data.Company;
import processing.data.AssignmentHandler;
import processing.dataBase.dbHandler;
import processing.helper.DatumFull;

public class Controller {

// Attributes
	
	private User activeUser;
	private CSPmainWindows mainWindow;
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
	 * Categories and Companies are already imported in the method, because the importAssignment method already needs them!!
	 * @param userData Data of the active user which is loaded from the data base
	 * @param dbHandler object that connects to the data base
	 * @return Returns the controller singleton instance 
	 * @throws Exception 
	 */
	public static Controller init(String[] userData, dbHandler dbHandler, LoginController loginController) throws Exception {
		Controller.getInstance();
		instance.mainWindow = new CSPmainWindows(null);
		//TODO does the constructor automatically display the GUI?
		instance.loginController = loginController;
		instance.importUser(userData);
		instance.dbHandler = dbHandler;
		instance.importCategories();
		instance.importCompanyList();
		instance.importAssingments();
		return instance;
	}

	/**
	 * Creates the active User from the result coming from the data base.
	 * @throws ArrayIndexOutOfBoundsException thrown if wrong string array is put into the method. If it occurs, ensure proper data base extraction.
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
	 * TODO finish Method, Treeitems has to be build top-down, therefore a StringArray is needed or the hashMap must be used correctly
	 * This Method imports all Categories and creates the TreeItem Array
	 * @throws SQLException Exception is thrown when a data base connection error occurs.
	 * @throws IOException Exception is thrown when corrupt data is importet from the data base
	 */
	private void importCategories() throws SQLException, IOException {

		try {
			//get HashMaps from DB
			HashMap<String, String[]> dataFromDB;
			try{
				dataFromDB = this.dbHandler.getCategories();
			}catch (SQLException e){
				throw new SQLException("Fehler beim importieren der Kategorien: " + e.getMessage());
			}catch (IOException e){
				throw new IOException("Fehler beim importieren der Kategorien: " + e.getMessage());
			}
			this.mainCategoryList = new TreeItem[1];
			//for loop for parsing the HashMap
			for (int j = 0; j < dataFromDB.size(); j++) {
				TreeItem[] temporaryCategoryList = new TreeItem[j];
				TreeItem temporaryCategory;
				//gets Array from HashMap which represents a data record for a category
				String [] buff = dataFromDB.get(String.valueOf(j));
				// creates TreeItem object out of new data record
				temporaryCategory = new TreeItem(this.searchForCategory(buff[1]), 0);
				//add new object to offerList
				for (int i = 0; i < j; i++){
					temporaryCategoryList[i] = this.mainCategoryList[1];
				}
				temporaryCategoryList[j] = temporaryCategory;
				this.mainCategoryList = temporaryCategoryList;
			}
		} catch (SQLException e) {
			throw new SQLException("Fehler beim Import der Kategorien: "
					+ e.getMessage());
		}

	}

	/**
	 * TODO purpose of this method
	 * @throws SQLException Exception is thrown when a data base connection error occurs.
	 * @throws IOException Exception is thrown when corrupt data is importet from the data base
	 */
	private void importAssingments() throws SQLException, IOException {
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
				temporaryAssignment= new Assignment(buff, positionDataFromDB, offerDataFromDB);//TODO Exception spezifizieren, siehe Assignment Constructor
				//add new object to assigmentList
				for (int i = 0; i < j; i++){
					temporaryAssignmentList[i] = this.assignmentHandler.getAssignmentList()[i];
				}
				temporaryAssignmentList[j] = temporaryAssignment;
				this.assignmentHandler.setAssignmentList(temporaryAssignmentList);
			}
		}catch (SQLException e) {
			throw new SQLException("Datenbankfehler beim Importieren der Ausschreibungen: " + e.getMessage());
		}catch (IOException e) {
			throw new IOException("Datenbankfehler beim Importieren der Ausschreibungen: " + e.getMessage());
		}
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
		LoginController l = loginController;
		instance = null;
		l.showLoginScreen();
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

		// Creation of the new assignment initialized from the GUI
		Assignment newAssignment = new Assignment(assignmentID, positionList,
				offerHandler, description, dateOfCreation, deadline, status,
				title);

		// Add the new assignment to the Controller's AssignmentList
		Assignment[] newAssignmentList = new Assignment[this.assignmentHandler
				.getAssignmentList().length + 1];
		for (int j = 0; j < this.assignmentHandler.getAssignmentList().length; j++) {
			newAssignmentList[j] = this.assignmentHandler.getAssignmentList()[j];
		}
		newAssignmentList[this.assignmentHandler.getAssignmentList().length] = newAssignment;
		this.assignmentHandler.setAssignmentList(newAssignmentList);
	}

	/**
	 * TODO finish this Method and JavaDoc
	 * @param assignmentID
	 * @return
	 */
	public TreeItem[] createPositionTree(String assignmentID) {
		//Creation of the positionTree
		TreeItem[] positionTree = new TreeItem[1];
		//Import positionList from assignment
		TreeItem[] positionList = this.assignmentHandler.SearchForID(assignmentID).getPositionList();
		for (int j = 0; j < positionList.length; j++) {
			TreeItem temporaryPosition = positionList[j];
			TreeItem temporaryCategory = Controller.getInstance().searchForCategory(temporaryPosition.getText(3));
			//TODO create an array containing the Position and the Path
			//Stopping to pick Categories into the PositionTree when they have already been picked!
			while(temporaryCategory.getText()==null){
				
				 
			}
		}
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
			if(this.mainCategoryList[j].getText()==ID){
				return this.mainCategoryList[j];
			}
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
	
	public TreeItem[] getMainCategoryList() throws SQLException {
		return this.mainCategoryList;
	}

	public AssignmentHandler getAssignmentHandler() {
		return assignmentHandler;
	}

	public dbHandler getDbHandler() {
		return dbHandler;
	}


}
