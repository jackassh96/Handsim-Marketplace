package processing;

import gui.CSPmainWindows;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

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

	/**
	 * GENERAL TODO's: (NEEDS DISCUSSION AND AGREEMENT)
	 * 
	 ********** GUI
	 * 
	 * -> triggering gui:
	 * 			o start man window
	 * 			o close main window
	 * 			o close main window and start login again
	 * 
	 * -> generate TableItems:
	 * 			o my Assignments (short version for dashboard)
	 * 			o next Dates
	 * 			o companies
	 * 			o my Assignments (detailed list)
	 * 			
	 * -> generate Strings:
	 * 			o my Profile
	 * 
	 * 
	 *************** DB 
	 * 
	 * -> triggering db actions
	 * 			o create User
	 * 			o update User
	 * 			o delete User
	 * 			o create Assignment
	 * 			o updateAssignmen
	 * 			o delete Assignment
	 * 			o updateOffer
	 * 			o createPosition
	 */
	
	
	
   /*
	* Attributes
	*/
	
	private User activeUser;
	private CSPmainWindows mainWindow;	
	private Company[] companyList;
	private Category[] categoryList;
	private Category[] majorCategoryList;
	private ArrayList<Category> neededCategoryList;
	private AssignmentHandler assignmentHandler;
	private dbHandler dbHandler;
	private ArrayList<TreeItem> serviceTreeList;
	private ArrayList<TreeItem> positionTreeList;
	
	ArrayList<String> idspuffer = new ArrayList<>();
	ArrayList<Category> catpuffer = new ArrayList<>();

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
	 * all data needed is imported
	 * @param userData Data of the active user which is loaded from the data base
	 * @param dbHandler object that connects to the data base
	 * @return Returns the controller singleton instance 
	 * @throws IOException 
	 * @throws SQLException
	 * @throws Exception  
	 */
	//TODO dbHandler übergeben?
	public static Controller init(String[] userData, dbHandler dbHandler) throws SQLException, IOException, Exception {
		instance = Controller.getInstance();
		//initialize helper lists
		instance.serviceTreeList = new ArrayList<>(); //TODO reset when method is called
		instance.positionTreeList = new ArrayList<>(); //TODO reset when method is called
		instance.neededCategoryList = new ArrayList<>(); //TODO reset when method is called
		
		instance.dbHandler = dbHandler;
		instance.activeUser = instance.importUser("max32");  //TODO remove hard coded name when login is implemented

		Category [] buf = instance.importCategories();
		instance.categoryList = instance.generateSubCategories(buf);
		instance.majorCategoryList = instance.seperateMajorCategories(buf);		
		instance.importCompanyList();
		instance.importAssingments(instance.activeUser.getUserID());
		//instance of cotroller must be input! TODO!
//		CSPmainWindows.main(null);
		return instance;
	}

	/**
	 * Creates the active User from the result coming from the data base.
	 * @param data The User information
	 * 
	 * @return User
	 * 
	 * @throws IOException 
	 * @throws SQLException
	 * @throws ArrayIndexOutOfBoundsException 
	 */
	private User importUser(String User_id) throws SQLException, IOException {
		User buf = new User(dbHandler.loadUserData(User_id));
		return buf;
	}

	/**
	 * This Method imports all Categories from database table
	 * 
	 * @return Category []	 array containing all categories imported from database (without sub category list!)
	 * 
	 * @throws SQLException  Exception is thrown when a data base connection error occurs.
	 * @throws IOException 	 Exception is thrown when corrupt data is imported from the data base
	 */
	private Category [] importCategories() throws SQLException, IOException  { 
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
	 * This Method imports all sub categories into the categories
	 * 
	 * @return Category []	array containing all categories with their sub category lists
	 * 
	 */
	private Category [] generateSubCategories(Category [] Categories) { 
		Category [] result = Categories;
		int j = 0;
		//durch das gesamte array laufen
		for (Category c : Categories) {
			ArrayList<Category> buf = new ArrayList<>();
			//pro categorien subcategorien zusammensammeln
			for (Category c2 : Categories) {
				if (c2.getParentCategory().equals(c.getCategoryID())) {
					buf.add(c2);
				}
			}
			Category [] subCats = new Category[buf.size()];
			//gemerkte Arraylist in array überführen
			for (int i = 0; i < buf.size(); i++) {
				subCats[i] = buf.get(i);
			}
			//ermittelte subcategory liste ins finale array schreiben 
			result[j].setSubCategories(subCats);
			j++;
		}
		return result;
	}

	/**
	 * Separates major categories from category list
	 * 
	 * @param  Category []		array containing all categories
	 * 
	 * @return Category [] 		array containing only major categories
	 */
	private Category [] seperateMajorCategories(Category [] Categories) { 
		ArrayList<Category> buf = new ArrayList<>();
		Category [] result = Categories;
		//durch das gesamte array laufen
		for (Category c : Categories) {
			if (c.getParentCategory().equals("-1")) {
				buf.add(c);
			}
		}
		Category [] majorCats = new Category[buf.size()];
		//gemerkte Arraylist in array überführen
		for (int i = 0; i < buf.size(); i++) {
			majorCats[i] = buf.get(i);
		}
		//ermittelte subcategory liste ins finale array schreiben 
		result = majorCats;
		return result;
	}
	
	/**
	 * Separates sub categories from category list
	 * 
	 * @param  Category []		array containing all categories
	 * 
	 * @return Category [] 		array containing only sub categories
	 */
	private Category [] seperateSubCategories(Category [] Categories) throws SQLException, IOException { 
		ArrayList<Category> buf = new ArrayList<>();
		Category [] result = Categories;
		//durch das gesamte array laufen
		for (Category c : Categories) {	
			if (!(c.getParentCategory().equals("-1"))) {
				buf.add(c);
			}
		}
		Category [] majorCats = new Category[buf.size()];
		//gemerkte Arraylist in array überführen
		for (int i = 0; i < buf.size(); i++) {
			majorCats[i] = buf.get(i);
		}
		//ermittelte subcategory liste ins finale array schreiben 
		result = majorCats;		
		return result;
	}
	
	/**
	 * Builds tree from top down (major categories to sub categories)
	 * 
	 * @param  tree 			tree to put in TreeItems (needed as a parent for the first TreeItems)
	 * 
	 */
	public void buildTreeFromMajorCategories(Tree tree) { 
		Category [] majCats = instance.majorCategoryList;
		//durch major categories laufen um subkategorien zu erhalten
		for (Category c : majCats) {
			//Treeitem erzeugen für major categories
			TreeItem x = c.toMajorTreeItem(tree);
			serviceTreeList.add(x);
			for (Category ca : c.getSubCategories()) {
				createSubTreeItems(ca, x);
			}
		}
	}
	
	/**
	 * Builds all sub tree items (recursively)
	 * 
	 * @param  category		category object to get specific sub categories
	 * @param  treeitem		tree item as the parent to build tree hierarchy
	 * 
	 */
	private void createSubTreeItems(Category category, TreeItem treeitem) { 
		Category [] cats = category.getSubCategories();
		TreeItem tI = category.toSubTreeItem(treeitem);
		serviceTreeList.add(tI);
		for (Category c : cats) {
			createSubTreeItems(c, tI);
		}
	}
	
	/**
	 * Builds tree from bottom up (sub categories to major categories)
	 * 
	 * @param  Assignment_ID	ID to get positions of specifc assignment
	 * @param  tree 			tree to put in TreeItems (needed as a parent for the first TreeItems)
	 * 
	 */
	public void builTreeWithPositons(String Assignment_ID, Tree tree) throws SQLException, IOException { 
		Assignment assign = instance.searchForID(Assignment_ID);
		Position [] positions = assign.getPositionList();
		
		for (Position p : positions) {
				String id = p.getCategory_ID();
				idspuffer.add(id);
				Category c = instance.searchForCategory(id);
				catpuffer.add(c);
				findParentCategory(c.getParentCategory());
		}
		//arrayList in Array überführen
		Category [] buffer = new Category[catpuffer.size()];
		int i = 0;
		for (Category c : catpuffer) {
			buffer[i] = c;
			i++;
		}
		
		Category [] majC = instance.seperateMajorCategories(buffer);
		for (Category c : majC) {
			positionTreeList.add(c.toMajorTreeItem(tree));
		}
		Category [] subC = instance.seperateSubCategories(buffer);
		//sort by id
		subC = sortCategoryByID(subC);		
		for (Category c : subC) {
			positionTreeList.add(c.toSubTreeItem(findTreeItemWithID(c.getParentCategory())));
		}
		
	}
	
	/**
	 * Finds parent categories (recursively)
	 * 
	 * @param  Parent_ID	ID of parent category object
	 */
	private void findParentCategory(String Parent_ID) {
		Category cat = instance.searchForCategory(Parent_ID);
		//category in liste aufnehmen
		if (!(catpuffer.contains(cat))) {
			instance.catpuffer.add(cat);
		}
		//rekursive Suche nach Parent bis Kategorie keinen mehr hat
		if (!(cat.getParentCategory().equals("-1"))) {
			findParentCategory(cat.getParentCategory());
		}
	}
	
	/**
	 * Finds tree item with specific id
	 * 
	 * @param  Category_ID	ID of parent category object
	 * 
	 * @return TreeItem 	tree item with given category id
	 */
	private TreeItem findTreeItemWithID(String Category_ID) {
		for (TreeItem t : instance.positionTreeList) {
			
			String [] buf = (String[]) t.getData();
			if (buf[0].equals(Category_ID)) {
				return t;
			}
		}
		return null;
	}
	
	
	/**
	 * Imports position list for specific assignment
	 * 
	 * @param Assignment_ID		Assignment ID of the needed positions
	 * 
	 * @throws SQLException 	Exception is thrown when a data base connection error occurs.
	 * @throws IOException 		Exception is thrown when corrupt data is imported from the data base
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
	 * Imports all assignments from database table
	 * 
	 * @param  User_ID			User ID of the needed assignments
	 * 
	 * @throws SQLException 	Exception is thrown when a data base connection error occurs.
	 * @throws IOException 		Exception is thrown when corrupt data is imported from the data base
	 */
	private void importAssingments(String User_ID) throws SQLException, IOException {
		
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
	 * Imports all companies from database table
	 * 
	 * @throws SQLException Exception is thrown when a data base connection error occurs.
	 * @throws IOException Exception is thrown when corrupt data is imported from the data base
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
	
	

// GUI triggered Methods TODO's!!!

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
		//TODO 
//		dbHandler.updateUser(Username, Password, Vorname, Nachname, Strasse, Hausnummer, Postleitzahl, Stadt, Email, Telefonnummer, Firma, Geschlecht)
		
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
		//
		LoginController l = new LoginController();
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

//		// Creation of the new assignment initialized from the GUI
//		Assignment newAssignment = new Assignment(assignmentID, positionList,
//				offerHandler, description, dateOfCreation, deadline, status,
//				title);

		// Add the new assignment to the Controller's AssignmentList
		Assignment[] newAssignmentList = new Assignment[this.assignmentHandler.getAssignmentList().length + 1];
		for (int j = 0; j < this.assignmentHandler.getAssignmentList().length; j++) {
			newAssignmentList[j] = this.assignmentHandler.getAssignmentList()[j];
		}
//		newAssignmentList[this.assignmentHandler.getAssignmentList().length] = newAssignment;
		this.assignmentHandler.setAssignmentList(newAssignmentList);
	}
	
	/**
	 * TODO Finish this method with exception, if it is needed for searching a Company - Felix: which exception??
	 * @param ID
	 * @return
	 */
	public Category searchForCategory(String Cat_ID){
		for (Category a : instance.categoryList) {
			if (a.getCategoryID().equals(Cat_ID)) {
				return a;
			}
		}
		return null;
	}
	
	/**
	 * Gets specific assignment from the assignment handler 
	 * @param 	Assignment_ID		ID of the needed assignment
	 * @return	Assignment			assignment object with given id or null if not in there
	 */
	public Assignment searchForID(String Assingtment_ID){
		for (Assignment a : instance.assignmentHandler.getAssignmentList()) {
			if (a.getAssignmentID().equals(Assingtment_ID)) {
				return a;
			}
		}
		return null;
	}
	
	/**
	 * Sorts the category array by category ID (ASC) 
	 * @param 	Category []			category array you want to sort
	 * @return	Category []			sorted category array
	 */
	private Category [] sortCategoryByID(Category [] cats) {	
		Arrays.sort(cats, new Comparator<Category>(){
			@Override
			public int compare(Category arg0, Category arg1) {
				return arg0.getCategoryID().compareTo(arg1.getCategoryID());
			}
		});
		return cats;	
		
	}

	
// Getters
// No setters needed. Only setting possibility is through the init method
	
	public User getUser() {
		return activeUser;
	}
	
	public Company[] getCompanyList() {
		return this.companyList;
	}
	
	public AssignmentHandler getAssignmentHandler() {
		return assignmentHandler;
	}

	public dbHandler getDbHandler() {
		return dbHandler;
	}


	public ArrayList<TreeItem> getPositionTreeList() {
		return positionTreeList;
	}


	public void setPositionTreeList(ArrayList<TreeItem> positionTreeList) {
		this.positionTreeList = positionTreeList;
	}


	public ArrayList<Category> getNeededCategoryList() {
		return neededCategoryList;
	}


	public void setNeededCategoryList(ArrayList<Category> neededCategoryList) {
		this.neededCategoryList = neededCategoryList;
	}


}
