package processing;

import gui.CSPmainWindows;

import java.io.IOException;
import java.sql.SQLException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import processing.data.Assignment;
import processing.data.Category;
import processing.data.Offer;
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
	 * 	TOBI	o start main window
	 * 	TOBI	o close main window
	 * 	TOBI	o close main window and start login again
	 * 
	 * ----------------------
	 * 
	 * -> generate TableItems:
	 * 		x	o my Assignments Header
	 * 		x	o my Assignments (short version for dashboard)			-> Bezeichnung, Status, Deadline
	 * 			o next Dates											-> 
	 * 		x	o companies header				
	 * 		x	o companies								
	 * 		x	o my Assignments (detailed list) header
	 * 		x	o my Assignments (detailed list)
	 * 		x	o generate offer list header
	 * 		x	o generate offer list (database action)
	 * 
	 * 			
	 * -> generate Strings:
	 * 	nT	x	o my Profile
	 * 	nT	x	o specific Assignment
	 * 	nT	x	o specific Offer
	 * 	nT	x	o specific Company
	 * 
	 * 
	 *************** DB 
	 * 
	 * -> triggering database actions
	 * 			o create User  			->login controller
	 * 	nT	x	o update User
	 * 			o delete User			->login controller
	 * 	nT	x	o create Assignment
	 * 	nT	x	o update Assignment
	 * 	nT	x	o delete Assignment
	 * 	nT	x	o update Offer
	 * 	nT  x	o generate Offer list
	 * 		x	o create Position
	 * 
	 * 
	 * 	x = implemented
	 * nT = not tested yet
	 *  
	 *  /// String [][] = gneriere pposituino 8assignnmentid<9
	 *  
	 *  
	 * 
	 */
	
	
	
   /*
	* Attributes
	*/
	protected boolean asc = false;
	
	private User activeUser;
	private CSPmainWindows mainWindow;	
	private Company[] companyList;
	private Category[] categoryList;
	private Category[] majorCategoryList;
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
	//TODO dbHandler �bergeben?
	public static Controller init(String[] userData, dbHandler dbHandler) throws SQLException, IOException, Exception {
		instance = Controller.getInstance();
		//initialize helper lists
		instance.serviceTreeList = new ArrayList<>(); 
		instance.positionTreeList = new ArrayList<>();
		if (dbHandler != null) {
			instance.dbHandler = dbHandler;	
		}
		if (userData != null) {
			instance.activeUser = new User(userData);
		}
		else {
			instance.activeUser = instance.importUser(instance.activeUser.getUserID());
		}
		Category [] buf = instance.importCategories();
		instance.categoryList = instance.generateSubCategories(buf);
		instance.majorCategoryList = instance.seperateMajorCategories(buf);		
		instance.importCompanyList();
		instance.importAssingments(instance.activeUser.getUserID());
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
			//gemerkte Arraylist in array �berf�hren
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
		//gemerkte Arraylist in array �berf�hren
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
		//gemerkte Arraylist in array �berf�hren
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
			//Treeitem erzeugen f�r major categories
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
		positionTreeList = new ArrayList<>();
		idspuffer = new ArrayList<>();
		catpuffer = new ArrayList<>();
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
				TreeItem tItem = c.toSubTreeItem(findTreeItemWithID(c.getParentCategory()));
			if (tItem != null) {	
				//TODO test!
				if (c.getSubCategories().length < 1) {
					for ( Position p : positions) {
						if (p.getCategory_ID().equals(c.getCategoryID())) {
							String [] vals = {c.getTitle(), p.getAmount(),p.getDescription()};
							tItem.setText(vals);
						}
					}
				}
				positionTreeList.add(tItem);
			}
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
		if (dataFromDB.size() > 0) {
			String [] buf = new String[dataFromDB.get(String.valueOf(0)).length];
			for (int j = 0; j < dataFromDB.size(); j++) {
				
				int i = 0;
				for (String x : dataFromDB.get(String.valueOf(j))) {
					buf[i] = x;	
					i++;
				}
				result[j] = new Position(buf[1], buf[2], buf[3], buf[4]); 
			}
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
		
		if (assignmentDataFromDB.size() > 0) {
			String [] buf = new String[assignmentDataFromDB.get(String.valueOf(0)).length];
			for (int j = 0; j < assignmentDataFromDB.size(); j++) {
				
				int i = 0;
				for (String x : assignmentDataFromDB.get(String.valueOf(j))) {
					buf[i] = x;	
					i++;
				}
				Position [] temp = importPositionsForAssignment(buf[0]);
				result[j] = new Assignment(buf[0], temp, buf[1], buf[2], buf[3], buf[4], buf[5], buf[6]); 
			}
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
			if (dataFromDB.size() > 0) {	
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
			}
		}catch (SQLException e) {
			throw new SQLException("Datenbankfehler beim Importieren der Firmenliste: " + e.getMessage());
		}
	}
	
	/**
	 * Generates Offer Array for a specific assignment
	 * TODO TES!!
	 * @param  assignment_ID	id to filter specific offers from database
	 * 
	 * @throws SQLException 	Exception is thrown when a data base connection error occurs.
	 * @throws IOException 		Exception is thrown when corrupt data is imported from the data base
	 */
	private Offer [] generateOfferlistforAssignment(String assignment_ID) throws SQLException, IOException {
		HashMap<String,String[]> offerDataFromDB = dbHandler.getOffer(assignment_ID);
		Offer [] result = new Offer[0];
		if (offerDataFromDB.size() > 0) {
			result = new Offer[offerDataFromDB.size()];
			String [] buf = new String[offerDataFromDB.get(String.valueOf(0)).length];
			
			for (int j = 0; j < offerDataFromDB.size(); j++) {
				
				int i = 0;
				for (String x : offerDataFromDB.get(String.valueOf(j))) {
					buf[i] = x;	
					i++;
				}
	
				result[j] = new Offer(buf[0], assignment_ID, buf[2], Double.parseDouble(buf[3]), buf[4], buf[5], buf[6], buf[7]); 
			}
		}
		return result;
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
	public void updateUser(String Username, String Vorname, String Nachname, String Strasse,
						 String Hausnummer, String Postleitzahl, String Stadt, String Email, String Telefonnummer,
						 String Firma, String Geschlecht) throws SQLException, IOException { 
		dbHandler.updateUser(Username, Vorname, Nachname, Strasse, Hausnummer, Postleitzahl, Stadt, Email, Telefonnummer, Firma, Geschlecht);
	}

	/**
	 * Called by GUI when User selects to delete his Account. 
	 * The user will automatically get logged off and will return to the Login Screen. 
	 * @throws SQLException Exception is thrown when a data base connection error occurs.
	 * @throws IOException Exception is thrown when corrupt data is importet from the data base
	 */
	public void deleteUser() throws SQLException, IOException {
		dbHandler.deleteUser(activeUser.getUserID());
		//logOff(); //TODO
	}

	/**
	 * This method creates a new Assignment from data entered into the GUI and appends it to the assignmentHandler's list.
	 * 
	 * TODO TEST! 
	 * 
	 *  All parameters according to the attributes of the assignment object.
	 * @param description
	 * @param dateOfCreation
	 * @param deadline
	 * @param title
	 * 
	 * @throws IOException 
	 * @throws SQLException 
	 */
	// assignmentID raus, positionlist raus 
	public String createAssignment(String description, String dateOfCreation,String title, String dueDate) throws SQLException, IOException {
//		deadline
		DatumFull buf = new DatumFull(dueDate);
		buf.minusDays(7);
		//add entry in database
		String id = dbHandler.createAssignment(activeUser.getUserID(), description, dateOfCreation, buf.toMachineString(), title, dueDate);
		
		//string mit id zurückgeben
		return id;
	}
	
	
	/**
	 * Deletes a specific Assignment within the database (updates the status in database! no real deletion)
	 * 
	 * TODO TEST! 
	 * 
	 * @param assignmentID
	 * @param status
	 * 
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void deleteAssignment(String assignment_ID) throws SQLException, IOException {
		//update entry in database
		dbHandler.deleteAssignment(assignment_ID);

		//TODO fill new database table with description
		dbHandler.cancelAllOffer(assignment_ID);

		
	}
	
	/**
	 * Updates a specific Assignment within the database
	 * 
	 * TODO TEST! 
	 * 
	 * @param offer id 
	 * @param status
	 * 
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void acceptOffer(String offer_ID) throws SQLException, IOException {
		HashMap<String,String[]> hM = dbHandler.getSpecificOffer(offer_ID);
		String [] buf = new String[hM.get(String.valueOf(0)).length];
		Offer offer = null;
		for (int j = 0; j < hM.size(); j++) {
			
			int i = 0;
			for (String x : hM.get(String.valueOf(j))) {
				buf[i] = x;	
				i++;
			}
			offer = new Offer(buf[0], buf[1], buf[2], Double.parseDouble(buf[3]), buf[4], buf[5], buf[6], buf[7]); 
		}
		
		//set state assigned for assignment in database
		dbHandler.acceptAssignmentStatus(offer.getAssignmentID());
		//set state accepted for offer in database
		dbHandler.acceptOffer(offer_ID);
		//set state reject state for all other offers
		dbHandler.cancelAllOtherOffer(offer.getAssignmentID(), offer_ID);
	}
	//TODO + beschreibung
	public void declineOffer(String offer_ID) throws SQLException, IOException {
		dbHandler.rejectOffer(offer_ID);
	}
	
	/**
	 * This method creates a new Position from data entered into the GUI and appends it to the assignment within assignmentHandler's list.
	 * 
	 * TODO TEST! 
	 * 
	 * @param position_ID
	 * @param category_ID
	 * @param assignment_ID
	 * @param description
	 * @param amount
	 * 
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void createPosition(String category_ID, String assignment_ID, String description, String amount) throws SQLException, IOException {
		//add entry in database
		dbHandler.createPosition(category_ID, assignment_ID, description, amount);
	}
	
	
	/**
	 * Gets specific category with given ID
	 * @param ID
	 * @return Category 
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
				return ((Integer)Integer.parseInt(arg0.getCategoryID())).compareTo((Integer)(Integer.parseInt(arg1.getCategoryID())));
			}
		});
		return cats;	
		
	}

	//TODO'S
	// could be global parameters at beginning of class definition! TODO 
	// + documentation
	public void generateTableHeaderMyAssignments(final Table table) {
		TableColumn tblClmnTitle = new TableColumn(table, SWT.CENTER);
		tblClmnTitle.setWidth(218);
		tblClmnTitle.setText("Bezeichnung");
		tblClmnTitle.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsMyAssignments(table, 0, asc);
					asc = false;
				}
				else {
					sortStringTableItemsMyAssignments(table, 0, false);
					asc = true;
				}
			}
		});
		TableColumn tblClmnState = new TableColumn(table, SWT.CENTER);
		tblClmnState.setWidth(121);
		tblClmnState.setText("Status");
		tblClmnState.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsMyAssignments(table, 1, asc);
					asc = false;
				}
				else {
					sortStringTableItemsMyAssignments(table, 1, false);
					asc = true;
				}
			}
		});
		TableColumn tblClmnDeadline = new TableColumn(table, SWT.CENTER);
		tblClmnDeadline.setWidth(153);
		tblClmnDeadline.setText("Deadline");
		tblClmnDeadline.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortDateTableItemsMyAssignments(table, 2, asc);
					asc = false;
				}
				else {
					sortDateTableItemsMyAssignments(table, 2, false);
					asc = true;
				}
			}
		});
		TableColumn tblClmnIcon = new TableColumn(table, SWT.CENTER);
		tblClmnIcon.setWidth(43);
		tblClmnIcon.setText("");
	}
	
	public void generateMyAssignmentTableItemsDashboard(Table table) {
		for (Assignment a : assignmentHandler.getAssignmentList()) {
			TableItem tableItem = new TableItem(table, SWT.CENTER);
			tableItem.setText(new String[] {a.getTitle(), a.getStatus(), a.getDeadline()});
			tableItem.setData("id", a.getAssignmentID());
			
			switch (a.getStatus()) {
			
			//TODO add different states
			case "open": 		tableItem.setImage(3, new Image(null, ".\\images\\openState.png"));
								break;
			
			case "canceled": 	tableItem.setImage(3, new Image(null, ".\\images\\declinedState.png"));
								break;
								
			case "assigned": 	tableItem.setImage(3, new Image(null, ".\\images\\assignedState.png"));
								break;
								
			default :			tableItem.setImage(3, new Image(null, ".\\images\\doneState.png"));
								break;
			}
			
		}
	}
	
	public void generateTableHeaderCompanyTable(final Table table) {
		TableColumn tblClmnName = new TableColumn(table, SWT.CENTER);
		tblClmnName.setWidth(150);
		tblClmnName.setText("Name");
		tblClmnName.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsCompanies(table, 0, asc);
					asc = false;
				}
				else {
					sortStringTableItemsCompanies(table, 0, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnStreet = new TableColumn(table, SWT.CENTER);
		tblClmnStreet.setWidth(150);
		tblClmnStreet.setText("Straße");
		tblClmnStreet.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsCompanies(table, 1, asc);
					asc = false;
				}
				else {
					sortStringTableItemsCompanies(table, 1, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnNumber = new TableColumn(table, SWT.CENTER);
		tblClmnNumber.setWidth(80);
		tblClmnNumber.setText("Nummer");
		tblClmnNumber.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsCompanies(table, 2, asc);
					asc = false;
				}
				else {
					sortStringTableItemsCompanies(table, 2, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnPostcode = new TableColumn(table, SWT.CENTER);
		tblClmnPostcode.setWidth(80);
		tblClmnPostcode.setText("Postleitzahl");
		tblClmnPostcode.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortNumberTableItemsCompanies(table, 3, asc);
					asc = false;
				}
				else {
					sortNumberTableItemsCompanies(table, 3, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnOwner = new TableColumn(table, SWT.CENTER);
		tblClmnOwner.setWidth(100);
		tblClmnOwner.setText("Eigentümer");
		tblClmnOwner.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsCompanies(table, 4, asc);
					asc = false;
				}
				else {
					sortStringTableItemsCompanies(table, 4, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnPhone = new TableColumn(table, SWT.CENTER);
		tblClmnPhone.setWidth(100);
		tblClmnPhone.setText("Telefonnummer");
		tblClmnPhone.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsCompanies(table, 5, asc);
					asc = false;
				}
				else {
					sortStringTableItemsCompanies(table, 5, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnEmail = new TableColumn(table, SWT.CENTER);
		tblClmnEmail.setWidth(140);
		tblClmnEmail.setText("Email");
		tblClmnEmail.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsCompanies(table, 6, asc);
					asc = false;
				}
				else {
					sortStringTableItemsCompanies(table, 6, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnDescription = new TableColumn(table, SWT.CENTER);
		tblClmnDescription.setWidth(300);
		tblClmnDescription.setText("Beschreibung");
		tblClmnDescription.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsCompanies(table, 7, asc);
					asc = false;
				}
				else {
					sortStringTableItemsCompanies(table, 7, false);
					asc = true;
				}
			}
		});
	}
	
	public void generateCompanyTableItems(Table table) {
		for (Company c : companyList) {
			TableItem tableItem = new TableItem(table, SWT.CENTER);
			tableItem.setText(new String[] {c.getName(),c.getStreet(),c.getNumber(),String.valueOf(c.getPostCode()),
											c.getOwner(), c.getPhone(), c.getEMail(),c.getDescription()});
			tableItem.setData("id", c.getCompanyID());
		}
	}
	
	public void generateTableHeaderAssignmentTable(final Table table) {
		TableColumn tblClmnTitle = new TableColumn(table, SWT.CENTER);
		tblClmnTitle.setWidth(220);
		tblClmnTitle.setText("Bezeichnung");
		tblClmnTitle.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsAssignments(table, 0, asc);
					asc = false;
				}
				else {
					sortStringTableItemsAssignments(table, 0, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnCreation = new TableColumn(table, SWT.CENTER);
		tblClmnCreation.setWidth(100);
		tblClmnCreation.setText("Erstellungsdatum");
		tblClmnCreation.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortDateTableItemsAssignments(table, 1, asc);
					asc = false;
				}
				else {
					sortDateTableItemsAssignments(table, 1, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnDeadline = new TableColumn(table, SWT.CENTER);
		tblClmnDeadline.setWidth(100);
		tblClmnDeadline.setText("Ausschreibungsende");
		tblClmnDeadline.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortDateTableItemsAssignments(table, 2, asc);
					asc = false;
				}
				else {
					sortDateTableItemsAssignments(table, 2, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnDuedate = new TableColumn(table, SWT.CENTER);
		tblClmnDuedate.setWidth(100);
		tblClmnDuedate.setText("Fälligkeitsdatum");
		tblClmnDuedate.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortDateTableItemsAssignments(table, 3, asc);
					asc = false;
				}
				else {
					sortDateTableItemsAssignments(table, 3, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnState = new TableColumn(table, SWT.CENTER);
		tblClmnState.setWidth(100);
		tblClmnState.setText("Status");
		tblClmnState.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsAssignments(table, 4, asc);
					asc = false;
				}
				else {
					sortStringTableItemsAssignments(table, 4, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnDescritpion = new TableColumn(table, SWT.CENTER);
		tblClmnDescritpion.setWidth(250);
		tblClmnDescritpion.setText("Beschreibung");
		tblClmnDescritpion.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsAssignments(table, 5, asc);
					asc = false;
				}
				else {
					sortStringTableItemsAssignments(table, 5, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnIcon = new TableColumn(table, SWT.CENTER);
		tblClmnIcon.setWidth(25);
		tblClmnIcon.setText("");
	}
	
	
	
	public void generateMyAssignmentTableItems(Table table) {
		for (Assignment a : assignmentHandler.getAssignmentList()) {
			TableItem tableItem = new TableItem(table, SWT.CENTER);
			tableItem.setText(new String[] {a.getTitle(), a.getDateOfCreation(), a.getDeadline(),
											a.getDueDate(), a.getStatus(), a.getDescription()});
			tableItem.setData("id", a.getAssignmentID());
			
			switch (a.getStatus()) {
				//TODO add different states
				case "open": 		tableItem.setImage(6, new Image(null, ".\\images\\openState.png"));
									break;
				
				case "canceled": 	tableItem.setImage(6, new Image(null, ".\\images\\declinedState.png"));
									break;
									
				case "assigned": 	tableItem.setImage(6, new Image(null, ".\\images\\assignedState.png"));
									break;
									
				default :			tableItem.setImage(6, new Image(null, ".\\images\\doneState.png"));
									break;
									
			}
		}
	}
	
	public void generateTableHeaderOfferTable(final Table table) {
		TableColumn tblClmnTitle = new TableColumn(table, SWT.CENTER);
		tblClmnTitle.setWidth(200);
		tblClmnTitle.setText("Firma");
		tblClmnTitle.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsOffer(table, 0, asc);
					asc = false;
				}
				else {
					sortStringTableItemsOffer(table, 0, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnCreation = new TableColumn(table, SWT.CENTER);
		tblClmnCreation.setWidth(100);
		tblClmnCreation.setText("Preis");
		tblClmnCreation.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortNumberTableItemsOffer(table, 1, asc);
					asc = false;
				}
				else {
					sortNumberTableItemsOffer(table, 1, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnDeadline = new TableColumn(table, SWT.CENTER);
		tblClmnDeadline.setWidth(80);
		tblClmnDeadline.setText("Benötigter Zeitraum");
		tblClmnDeadline.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsOffer(table, 2, asc);
					asc = false;
				}
				else {
					sortStringTableItemsOffer(table, 2, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnDuedate = new TableColumn(table, SWT.CENTER);
		tblClmnDuedate.setWidth(100);
		tblClmnDuedate.setText("Beschreibung");
		tblClmnDuedate.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsOffer(table, 3, asc);
					asc = false;
				}
				else {
					sortStringTableItemsOffer(table, 3, false);
					asc = true;
				}
			}
		});
		
		TableColumn tblClmnState = new TableColumn(table, SWT.CENTER);
		tblClmnState.setWidth(100);
		tblClmnState.setText("Status");
		tblClmnState.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsOffer(table, 4, asc);
					asc = false;
				}
				else {
					sortStringTableItemsOffer(table, 4, false);
					asc = true;
				}
			}
		});
		
		
		TableColumn tblClmnDescritpion = new TableColumn(table, SWT.CENTER);
		tblClmnDescritpion.setWidth(100);
		tblClmnDescritpion.setText("Erstellt am");
		tblClmnDescritpion.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortDateTableItemsOffer(table, 5, asc);
					asc = false;
				}
				else {
					sortDateTableItemsOffer(table, 5, false);
					asc = true;
				}
			}
		});
	}
	
	public void generateOfferTableItems(Table table, String assignment_ID) throws SQLException, IOException {
		Offer [] offerList = generateOfferlistforAssignment(assignment_ID);
		if (offerList.length > 0) {
			for (Offer o : offerList) {
				TableItem tableItem = new TableItem(table, SWT.CENTER);
				String companyName = "";				
				for (Company c : companyList) {
					if (o.getCompanyID().equals(c.getCompanyID())) {
						companyName = c.getName();
					}
				}
				tableItem.setText(new String[] {companyName, String.valueOf(o.getPrice()), o.getAmountOfTimeNeeded(), 
												o.getDescription(),	o.getStatus(), o.getDate()});
				tableItem.setData("id", o.getOfferID());
			}
		}
	}
	
	
	public void generateTableHeaderNextOfferTable(final Table table) {
		TableColumn tblClmnTitle = new TableColumn(table, SWT.CENTER);
		tblClmnTitle.setWidth(215);
		tblClmnTitle.setText("Auftrag");
		tblClmnTitle.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsOffer(table, 0, asc);
					asc = false;
				}
				else {
					sortStringTableItemsOffer(table, 0, false);
					asc = true;
				}
			}
		});
		TableColumn tblClmnComp = new TableColumn(table, SWT.CENTER);
		tblClmnComp.setWidth(144);
		tblClmnComp.setText("Firma");
		tblClmnComp.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortStringTableItemsOffer(table, 1, asc);
					asc = false;
				}
				else {
					sortStringTableItemsOffer(table, 1, false);
					asc = true;
				}
			}
		});
		TableColumn tblClmnPrice = new TableColumn(table, SWT.CENTER);
		tblClmnPrice.setWidth(98);
		tblClmnPrice.setText("Preis");
		tblClmnPrice.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortNumberTableItemsOffer(table, 2, asc);
					asc = false;
				}
				else {
					sortNumberTableItemsOffer(table, 2, false);
					asc = true;
				}
			}
		});
		TableColumn tblClmnDuedate = new TableColumn(table, SWT.CENTER);
		tblClmnDuedate.setWidth(134);
		tblClmnDuedate.setText("Erstellt am:");
		tblClmnDuedate.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					sortDateTableItemsOffer(table, 3, asc);
					asc = false;
				}
				else {
					sortDateTableItemsOffer(table, 3, false);
					asc = true;
				}
			}
		});
	}
	
	public void generateNextOfferTableItems(Table table) throws SQLException, IOException {
		for (Assignment a : assignmentHandler.getAssignmentList()) {
			Offer [] offerList = generateOfferlistforAssignment(a.getAssignmentID());
			if (offerList.length > 0) {
				for (Offer o : offerList) {
					TableItem tableItem = new TableItem(table, SWT.CENTER);
					String companyName = "";				
					for (Company c : companyList) {
						if (o.getCompanyID().equals(c.getCompanyID())) {
							companyName = c.getName();
						}
					}
					tableItem.setText(new String[] {a.getTitle(), companyName, String.valueOf(o.getPrice()), o.getDate()});
					tableItem.setData("id", o.getOfferID());
				}
			}
		}
	}

	//TODO DOCU
	public HashMap<String,String> genereateMyProfileHashMap() {
		HashMap<String,String> result = new HashMap<>();
		result.put("id", activeUser.getUserID());
		result.put("firstname", activeUser.getFirstName());
		result.put("lastname", activeUser.getLastName());
		result.put("street", activeUser.getStreet());
		result.put("number", activeUser.getNumber());
		result.put("postcode", String.valueOf(activeUser.getPostCode()));
		result.put("city", activeUser.getCity());
		result.put("company", activeUser.getCompany());
		result.put("phone", activeUser.getPhone());
		result.put("email", activeUser.geteMail());
		result.put("gender", activeUser.getGender());		
		return result;
	}

	//TODO DOCU
	public HashMap<String,String> genereateAssignmentHashMap(String assignment_ID) {
		Assignment assign = null;
		for ( Assignment a : assignmentHandler.getAssignmentList()) {
			if (a.getAssignmentID().equals(assignment_ID)) {
				assign = a;
			}
		}
		HashMap<String,String> result = new HashMap<>();
		if (assign != null) {
			result.put("title", assign.getTitle());
			result.put("duedate", assign.getDueDate());
			result.put("deadline", assign.getDeadline());
			result.put("dateofcration", assign.getDateOfCreation());
			result.put("status", assign.getStatus());
			result.put("description", assign.getDescription());	
		}
		return result;
	}
	
	//TODO DOCU
	public HashMap<String,String> genereateOfferHashMap(String offer_ID) throws SQLException, IOException {
		Offer offer = null;
		String compID = "";
		Company comp = null;
		//aus db! TOD
		HashMap<String,String[]> hM = dbHandler.getSpecificOffer(offer_ID);
		String [] buf = new String[hM.get(String.valueOf(0)).length];
		for (int j = 0; j < hM.size(); j++) {
			
			int i = 0;
			for (String x : hM.get(String.valueOf(j))) {
				buf[i] = x;	
				i++;
			}
			offer = new Offer(buf[0], buf[1], buf[2], Double.parseDouble(buf[3]), buf[4], buf[5], buf[6], buf[7]); 
		}

		for (Company c : companyList) {
			if (c.getCompanyID().equals(offer.getCompanyID())) {
				comp = c;
			}
		}
		HashMap<String,String> result = new HashMap<>();
		if (offer != null && comp != null) {
			result.put("company", comp.getName());
			result.put("price", String.valueOf(offer.getPrice()));
			result.put("amountoftimeneeded", offer.getAmountOfTimeNeeded());
			result.put("description", offer.getDescription());
			result.put("date", offer.getDate());
			result.put("status", offer.getStatus());	
		}
		return result;
	}
	
	//TODO DOCU
	public HashMap<String,String> genereatCompanyHashMap(String company_ID) {
		Company comp = null;
		for ( Company c : companyList) {
			if (c.getCompanyID().equals(company_ID)) {
				comp = c;
			}
		}
		HashMap<String,String> result = new HashMap<>();
		if (comp != null) {
			result.put("name", comp.getName());
			result.put("street", comp.getStreet());
			result.put("number", comp.getNumber());
			result.put("postcode", String.valueOf(comp.getPostCode()));
			result.put("owner", comp.getOwner());
			result.put("phone", comp.getPhone());
			result.put("email", comp.getEMail());
			result.put("description", comp.getDescription());
		}
		return result;

	}

	public void sortStringTableItemsMyAssignments(Table table, int columnindex, boolean asc) {
	// TODO Auto-generated method stub
	if (asc) {
		asc = false;
		TableItem[] items = table.getItems();
        Collator collator = Collator.getInstance(Locale.getDefault());
        int k = 0;
        while (k < items.length) {
	        for (int i = 1; i < items.length; i++) {
	          String value1 = items[i].getText(columnindex);
	          for (int j = 0; j < i; j++) {
	            String value2 = items[j].getText(columnindex);
	            if (collator.compare(value1, value2) < 0) {
	              String[] values = { items[i].getText(0),
	                  items[i].getText(1), items[i].getText(2) };
	              Image buf = items[i].getImage(3);
	              String data = (String) items[i].getData("id");
	              items[i].dispose();
	              TableItem item = new TableItem(table, SWT.NONE, j);
	              item.setText(values);
	              item.setImage(3,buf);
	              item.setData("id",data);
	              items = table.getItems();
	            }
	       
	          }
	        }
        k++;
        }
	} else {
			asc = true;
			TableItem[] items = table.getItems();
	        Collator collator = Collator.getInstance(Locale.getDefault());
	        int k = 0;
	        while (k < items.length) {
		        for (int i = 1; i < items.length; i++) {
		          String value1 = items[i].getText(columnindex);
		          for (int j = 0; j < i; j++) {
		            String value2 = items[j].getText(columnindex);
		            if (collator.compare(value1, value2) > 0) {
		              String[] values = { items[i].getText(0),
		                  items[i].getText(1), items[i].getText(2) };
		              String data = (String) items[i].getData("id");
		              Image buf = items[i].getImage(3);
		              items[i].dispose();
		              TableItem item = new TableItem(table, SWT.NONE, j);
		              item.setText(values);
		              item.setImage(3,buf);
		              item.setData("id",data);
		              items = table.getItems();
		            }
		       
		          }
		        }
	        k++;
	        }
		}
	}
	
	public void sortDateTableItemsMyAssignments(Table table, int columnindex, boolean asc) {
		// TODO Auto-generated method stub
		if (asc) {
			asc = false;
			TableItem[] items = table.getItems();
	        int k = 0;
	        while (k < items.length) {
	        for (int i = 1; i < items.length; i++) {
	          String value1 = items[i].getText(columnindex);
	          for (int j = 0; j < i; j++) {
	            String value2 = items[j].getText(columnindex);
	            if (new DatumFull(value1).compareTo(new DatumFull(value2)) < 0) {
	              String[] values = { items[i].getText(0),
	                  items[i].getText(1), items[i].getText(2) };
	              String data = (String) items[i].getData("id");
	              Image buf = items[i].getImage(3);
	              items[i].dispose();
	              TableItem item = new TableItem(table, SWT.NONE, j);
	              item.setText(values);
	              item.setImage(3,buf);
	              item.setData("id",data);
	              items = table.getItems();
	            }
	       
	          }
	        }
	        k++;
	        }
			} else {
				asc = true;
				TableItem[] items = table.getItems();
		        int k = 0;
		        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (new DatumFull(value1).compareTo(new DatumFull(value2)) > 0) {
			              String[] values = { items[i].getText(0),
			                  items[i].getText(1), items[i].getText(2) };
			              Image buf = items[i].getImage(3);
			              String data = (String) items[i].getData("id");
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setImage(3,buf);
			              item.setData("id",data);
			              items = table.getItems();
			            }
			       
			          }
			        }
		        k++;
		        }
			}
		}
	
	//TODO DOCU
	public void sortStringTableItemsCompanies(Table table, int columnindex, boolean asc) {
		// TODO Auto-generated method stub
		if (asc) {
			asc = false;
			TableItem[] items = table.getItems();
	        Collator collator = Collator.getInstance(Locale.getDefault());
	        int k = 0;
	        while (k < items.length) {
		        for (int i = 1; i < items.length; i++) {
		          String value1 = items[i].getText(columnindex);
		          for (int j = 0; j < i; j++) {
		            String value2 = items[j].getText(columnindex);
		            if (collator.compare(value1, value2) < 0) {
		              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3),
		            		  			  items[i].getText(4), items[i].getText(5), items[i].getText(6), items[i].getText(7) };
		              String data = (String) items[i].getData("id");
		              items[i].dispose();
		              TableItem item = new TableItem(table, SWT.NONE, j);
		              item.setText(values);
		              item.setData("id", data);
		              items = table.getItems();
		            }
		       
		          }
		        }
	        k++;
	        }
		} else {
				asc = true;
				TableItem[] items = table.getItems();
		        Collator collator = Collator.getInstance(Locale.getDefault());
		        int k = 0;
		        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (collator.compare(value1, value2) > 0) {
			              String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3),
            		  			  items[i].getText(4), items[i].getText(5), items[i].getText(6), items[i].getText(7) };
			              String data = (String) items[i].getData("id");
			              Image buf = items[i].getImage(3);
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setImage(3,buf);
			              item.setData("id", data);
			              items = table.getItems();
			            }
			       
			          }
			        }
		        k++;
		        }
			}
		}

	//TODO sortmethod for all other tables!
//	c.getName(),c.getStreet(),c.getNumber(),String.valueOf(c.getPostCode()),
//	c.getOwner(), c.getPhone(), c.getEMail(),c.getDescription()
	
	public void sortNumberTableItemsCompanies(Table table, int columnindex, boolean asc) {
		// TODO Auto-generated method stub
		if (asc) {
			asc = false;
			TableItem[] items = table.getItems();
	        int k = 0;
	        while (k < items.length) {
		        for (int i = 1; i < items.length; i++) {
		          String value1 = items[i].getText(columnindex);
		          for (int j = 0; j < i; j++) {
		            String value2 = items[j].getText(columnindex);
		            if (((Integer.compare(Integer.parseInt(value1), Integer.parseInt(value2)))) < 0) {
		              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3),
		            		  			  items[i].getText(4), items[i].getText(5), items[i].getText(6), items[i].getText(7) };
		              String data = (String) items[i].getData("id");
		              items[i].dispose();
		              TableItem item = new TableItem(table, SWT.NONE, j);
		              item.setText(values);
		              item.setData("id",data);
		              items = table.getItems();
		            }
		       
		          }
		        }
	        k++;
	        }
		} else {
				asc = true;
				TableItem[] items = table.getItems();
		        int k = 0;
		        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (((Integer.compare(Integer.parseInt(value1), Integer.parseInt(value2)))) > 0) {
			              String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3),
            		  			  items[i].getText(4), items[i].getText(5), items[i].getText(6), items[i].getText(7) };
			              String data = (String) items[i].getData("id");
			              Image buf = items[i].getImage(3);
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setImage(3,buf);
			              item.setData("id",data);
			              items = table.getItems();
			            }
			       
			          }
			        }
		        k++;
		        }
			}
		}
	
	public void sortStringTableItemsAssignments(Table table, int columnindex, boolean asc) {
		// TODO Auto-generated method stub
		if (asc) {
			asc = false;
			TableItem[] items = table.getItems();
	        Collator collator = Collator.getInstance(Locale.getDefault());
	        int k = 0;
	        while (k < items.length) {
		        for (int i = 1; i < items.length; i++) {
		          String value1 = items[i].getText(columnindex);
		          for (int j = 0; j < i; j++) {
		            String value2 = items[j].getText(columnindex);
		            if (collator.compare(value1, value2) < 0) {
		              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
		            		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5) };
		              String data = (String) items[i].getData("id");
		              items[i].dispose();
		              TableItem item = new TableItem(table, SWT.NONE, j);
		              item.setText(values);
		              item.setData("id",data);
		              items = table.getItems();
		            }
		       
		          }
		        }
	        k++;
	        }
		} else {
				asc = true;
				TableItem[] items = table.getItems();
		        Collator collator = Collator.getInstance(Locale.getDefault());
		        int k = 0;
		        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (collator.compare(value1, value2) > 0) {
			              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
            		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5)  };
			              String data = (String) items[i].getData("id");
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setData("id",data);
			              items = table.getItems();
			            }
			       
			          }
			        }
		        k++;
		        }
			}
		}
		
		public void sortDateTableItemsAssignments(Table table, int columnindex, boolean asc) {
			// TODO Auto-generated method stub
			if (asc) {
				asc = false;
				TableItem[] items = table.getItems();
		        int k = 0;
		        while (k < items.length) {
		        for (int i = 1; i < items.length; i++) {
		          String value1 = items[i].getText(columnindex);
		          for (int j = 0; j < i; j++) {
		            String value2 = items[j].getText(columnindex);
		            if (new DatumFull(value1).compareTo(new DatumFull(value2)) < 0) {
		              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
        		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5)  };
		              String data = (String) items[i].getData("id");
		              items[i].dispose();
		              TableItem item = new TableItem(table, SWT.NONE, j);
		              item.setText(values);
		              item.setData("id",data);
		              items = table.getItems();
		            }
		       
		          }
		        }
		        k++;
		        }
				} else {
					asc = true;
					TableItem[] items = table.getItems();
			        int k = 0;
			        while (k < items.length) {
				        for (int i = 1; i < items.length; i++) {
				          String value1 = items[i].getText(columnindex);
				          for (int j = 0; j < i; j++) {
				            String value2 = items[j].getText(columnindex);
				            if (new DatumFull(value1).compareTo(new DatumFull(value2)) > 0) {
				              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
	            		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5)  };
				              String data = (String) items[i].getData("id");
				              items[i].dispose();
				              TableItem item = new TableItem(table, SWT.NONE, j);
				              item.setText(values);
				              item.setData("id",data);
				              items = table.getItems();
				            }
				       
				          }
				        }
			        k++;
			        }
				}
			}
		
		public void sortStringTableItemsOffer(Table table, int columnindex, boolean asc) {
			// TODO Auto-generated method stub
			if (asc) {
				asc = false;
				TableItem[] items = table.getItems();
		        Collator collator = Collator.getInstance(Locale.getDefault());
		        int k = 0;
		        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (collator.compare(value1, value2) < 0) {
			              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
			            		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5) };
			              String data = (String) items[i].getData("id");
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setData("id",data);
			              items = table.getItems();
			            }
			       
			          }
			        }
		        k++;
		        }
			} else {
					asc = true;
					TableItem[] items = table.getItems();
			        Collator collator = Collator.getInstance(Locale.getDefault());
			        int k = 0;
			        while (k < items.length) {
				        for (int i = 1; i < items.length; i++) {
				          String value1 = items[i].getText(columnindex);
				          for (int j = 0; j < i; j++) {
				            String value2 = items[j].getText(columnindex);
				            if (collator.compare(value1, value2) > 0) {
				              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
	            		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5)  };
				              String data = (String) items[i].getData("id");
				              items[i].dispose();
				              TableItem item = new TableItem(table, SWT.NONE, j);
				              item.setText(values);
				              items = table.getItems();
				              item.setData("id",data);
				            }
				       
				          }
				        }
			        k++;
			        }
				}
			}
			
			public void sortDateTableItemsOffer(Table table, int columnindex, boolean asc) {
				// TODO Auto-generated method stub
				if (asc) {
					asc = false;
					TableItem[] items = table.getItems();
			        int k = 0;
			        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (new DatumFull(value1).compareTo(new DatumFull(value2)) < 0) {
			              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
	        		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5)  };
			              String data = (String) items[i].getData("id");
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setData("id",data);
			              items = table.getItems();
			            }
			       
			          }
			        }
			        k++;
			        }
					} else {
						asc = true;
						TableItem[] items = table.getItems();
				        int k = 0;
				        while (k < items.length) {
					        for (int i = 1; i < items.length; i++) {
					          String value1 = items[i].getText(columnindex);
					          for (int j = 0; j < i; j++) {
					            String value2 = items[j].getText(columnindex);
					            if (new DatumFull(value1).compareTo(new DatumFull(value2)) > 0) {
					              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
		            		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5)  };
					              String data = (String) items[i].getData("id");
					              items[i].dispose();
					              TableItem item = new TableItem(table, SWT.NONE, j);
					              item.setText(values);
					              item.setData("id",data);
					              items = table.getItems();
					            }
					       
					          }
					        }
				        k++;
				        }
					}
				}
			
			public void sortNumberTableItemsOffer(Table table, int columnindex, boolean asc) {
				// TODO Auto-generated method stub
				if (asc) {
					asc = false;
					TableItem[] items = table.getItems();
			        int k = 0;
			        while (k < items.length) {
				        for (int i = 1; i < items.length; i++) {
				          String value1 = items[i].getText(columnindex);
				          for (int j = 0; j < i; j++) {
				            String value2 = items[j].getText(columnindex);
				            if (((Double.compare(Double.parseDouble(value1), Double.parseDouble(value2)))) < 0) {
				              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2),
				            		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5) };
				              String data = (String) items[i].getData("id");
				              items[i].dispose();
				              TableItem item = new TableItem(table, SWT.NONE, j);
				              item.setText(values);
				              item.setData("id",data);
				              items = table.getItems();
				            }
				       
				          }
				        }
			        k++;
			        }
				} else {
						asc = true;
						TableItem[] items = table.getItems();
				        int k = 0;
				        while (k < items.length) {
					        for (int i = 1; i < items.length; i++) {
					          String value1 = items[i].getText(columnindex);
					          for (int j = 0; j < i; j++) {
					            String value2 = items[j].getText(columnindex);
					            if (((Double.compare(Double.parseDouble(value1), Double.parseDouble(value2)))) > 0) {
					              String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2),
					            		  			 items[i].getText(3), items[i].getText(4), items[i].getText(5)};
					              Image buf = items[i].getImage(3);
					              String data = (String) items[i].getData("id");
					              items[i].dispose();
					              TableItem item = new TableItem(table, SWT.NONE, j);
					              item.setText(values);
					              item.setData("id",data);
					              item.setImage(3,buf);
					              items = table.getItems();
					            }
					       
					          }
					        }
				        k++;
				        }
					}
				}

			public void sortNumberTableItemsNextOffer(Table table, int columnindex, boolean asc) {
				// a.getTitle(), companyName, String.valueOf(o.getPrice()), o.getDate()
				if (asc) {
					asc = false;
					TableItem[] items = table.getItems();
			        int k = 0;
			        while (k < items.length) {
				        for (int i = 1; i < items.length; i++) {
				          String value1 = items[i].getText(columnindex);
				          for (int j = 0; j < i; j++) {
				            String value2 = items[j].getText(columnindex);
				            if (((Double.compare(Double.parseDouble(value1), Double.parseDouble(value2)))) < 0) {
				              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3) };
				              String data = (String) items[i].getData("id");
				              items[i].dispose();
				              TableItem item = new TableItem(table, SWT.NONE, j);
				              item.setText(values);
				              item.setData("id",data);
				              items = table.getItems();
				            }
				       
				          }
				        }
			        k++;
			        }
				} else {
						asc = true;
						TableItem[] items = table.getItems();
				        int k = 0;
				        while (k < items.length) {
					        for (int i = 1; i < items.length; i++) {
					          String value1 = items[i].getText(columnindex);
					          for (int j = 0; j < i; j++) {
					            String value2 = items[j].getText(columnindex);
					            if (((Double.compare(Double.parseDouble(value1), Double.parseDouble(value2)))) > 0) {
					              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3) };
					              Image buf = items[i].getImage(3);
					              String data = (String) items[i].getData("id");
					              items[i].dispose();
					              TableItem item = new TableItem(table, SWT.NONE, j);
					              item.setText(values);
					              item.setData("id",data);
					              item.setImage(3,buf);
					              items = table.getItems();
					            }
					       
					          }
					        }
				        k++;
				        }
					}
				}
			
			public void sortDateTableItemsNextOffer(Table table, int columnindex, boolean asc) {
				// TODO Auto-generated method stub
				if (asc) {
					asc = false;
					TableItem[] items = table.getItems();
			        int k = 0;
			        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (new DatumFull(value1).compareTo(new DatumFull(value2)) < 0) {
			              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3) };
			              String data = (String) items[i].getData("id");
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setData("id",data);
			              items = table.getItems();
			            }
			       
			          }
			        }
			        k++;
			        }
					} else {
						asc = true;
						TableItem[] items = table.getItems();
				        int k = 0;
				        while (k < items.length) {
					        for (int i = 1; i < items.length; i++) {
					          String value1 = items[i].getText(columnindex);
					          for (int j = 0; j < i; j++) {
					            String value2 = items[j].getText(columnindex);
					            if (new DatumFull(value1).compareTo(new DatumFull(value2)) > 0) {
					              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3) };
					              String data = (String) items[i].getData("id");
					              items[i].dispose();
					              TableItem item = new TableItem(table, SWT.NONE, j);
					              item.setText(values);
					              item.setData("id",data);
					              items = table.getItems();
					            }
					       
					          }
					        }
				        k++;
				        }
					}
				}
			
			public void sortStringTableItemsNextOffer(Table table, int columnindex, boolean asc) {
				// TODO Auto-generated method stub
				if (asc) {
					asc = false;
					TableItem[] items = table.getItems();
			        Collator collator = Collator.getInstance(Locale.getDefault());
			        int k = 0;
			        while (k < items.length) {
				        for (int i = 1; i < items.length; i++) {
				          String value1 = items[i].getText(columnindex);
				          for (int j = 0; j < i; j++) {
				            String value2 = items[j].getText(columnindex);
				            if (collator.compare(value1, value2) < 0) {
				              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3) };
				              String data = (String) items[i].getData("id");
				              items[i].dispose();
				              TableItem item = new TableItem(table, SWT.NONE, j);
				              item.setText(values);
				              item.setData("id",data);
				              items = table.getItems();
				            }
				       
				          }
				        }
			        k++;
			        }
				} else {
						asc = true;
						TableItem[] items = table.getItems();
				        Collator collator = Collator.getInstance(Locale.getDefault());
				        int k = 0;
				        while (k < items.length) {
					        for (int i = 1; i < items.length; i++) {
					          String value1 = items[i].getText(columnindex);
					          for (int j = 0; j < i; j++) {
					            String value2 = items[j].getText(columnindex);
					            if (collator.compare(value1, value2) > 0) {
					              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3) };
					              String data = (String) items[i].getData("id");
					              items[i].dispose();
					              TableItem item = new TableItem(table, SWT.NONE, j);
					              item.setText(values);
					              items = table.getItems();
					              item.setData("id",data);
					            }
					       
					          }
					        }
				        k++;
				        }
					}
				}
}

