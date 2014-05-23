package processing;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import processing.data.Position;
import processing.data.User;
import processing.data.Company;
import processing.data.AssignmentHandler;
import processing.dataBase.dbHandler;
import processing.helper.DatumFull;
import processing.helper.Sorter;


public class Controller {
	
	
   /*
	* Attributes
	*/
	protected boolean asc = false;
	private User activeUser;
	private Company[] companyList;
	private Category[] categoryList;
	private Category[] majorCategoryList;
	private AssignmentHandler assignmentHandler;
	private dbHandler dbHandler;
	private ArrayList<TreeItem> serviceTreeList;
	private ArrayList<TreeItem> positionTreeList;
	ArrayList<String> idspuffer = new ArrayList<>();
	ArrayList<Category> catpuffer = new ArrayList<>();

	//Singleton methods and attributes
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

	//Initialization method
	/**
	 * Gives the caller the singleton instance and (re)creates the attributes.
	 * ONLY USE FOR THE INITIAL SETUP OR A WANTED RESET OF THE OBJECT
	 * all data needed is imported
	 * 
	 * @param 	userData (data of the active user which is loaded from the data base)
	 * @param 	dbHandler (object that connects to the data base)
	 * 
	 * @return 	Controller (singleton object of controller class) 
	 * 
	 * @throws SQLException  (thrown when a data base connection error occurs)
	 * @throws IOException 	 (thrown when corrupt data is imported from the data base)
	 */
	public static Controller init(String[] userData, dbHandler dbHandler) throws SQLException, IOException  {
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
	 * Imports all data of the active user from data base.
	 * 
	 * @param  User_ID (primary key of user entry in database)
	 * 
	 * @return User (user object containing all information
	 * 
	 * @throws SQLException (thrown when a data base connection error occurs)
	 * @throws IOException  (thrown when corrupt data is imported from the data base)
	 */
	private User importUser(String User_id) throws SQLException, IOException {
		User buf = new User(dbHandler.loadUserData(User_id));
		return buf;
	}

	/**
	 * This Method imports all Categories from database table
	 * 
	 * @return Category []	 (containing all categories imported from database ,without sub category list!)
	 * 
	 * @throws SQLException  (thrown when a data base connection error occurs)
	 * @throws IOException 	 (thrown when corrupt data is imported from the data base)
	 */
	//needs to be public for test class
	public Category [] importCategories() throws SQLException, IOException  { 
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
	 * This Method imports all sub categories into the category objects
	 * 
	 * @return Category []	array containing all categories with their sub category lists
	 * 
	 */
	//needs to be public for test class
	public Category [] generateSubCategories(Category [] Categories) { 
		Category [] result = Categories;
		int j = 0;
		for (Category c : Categories) {
			ArrayList<Category> buf = new ArrayList<>();
			for (Category c2 : Categories) {
				if (c2.getParentCategory().equals(c.getCategoryID())) {
					buf.add(c2);
				}
			}
			Category [] subCats = new Category[buf.size()];
			for (int i = 0; i < buf.size(); i++) {
				subCats[i] = buf.get(i);
			} 
			result[j].setSubCategories(subCats);
			j++;
		}
		return result;
	}

	/**
	 * Separates major categories from category list
	 * 
	 * @param  Category [] (containing all categories)
	 * 
	 * @return Category [] (containing only major categories)
	 */
	//needs to be public for test class
	public Category [] seperateMajorCategories(Category [] Categories) { 
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
	 * @param  Category [] (containing all categories)
	 * 
	 * @return Category [] (containing only sub categories)
	 * 
	 * @throws SQLException  (thrown when a data base connection error occurs)
	 * @throws IOException 	 (thrown when corrupt data is imported from the data base)
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
	 * @param  tree (parent for the TreeItems)
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
	 * @param  category	(object to get specific sub categories)
	 * @param  treeitem	(as the parent to build tree hierarchy)
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
	 * @param  Assignment_ID (to get positions of specific assignment)
	 * @param  tree (as the parent to build tree hierarchy)
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
		subC = Sorter.sortCategoryByID(subC);

		for (Category c : subC) {
				TreeItem tItem = c.toSubTreeItem(findTreeItemWithID(c.getParentCategory()));
			if (tItem != null) {	
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
		//add category to list
		if (!(catpuffer.contains(cat))) {
			instance.catpuffer.add(cat);
		}
		//recursively searching for parent until it has no
		if (!(cat.getParentCategory().equals("-1"))) {
			findParentCategory(cat.getParentCategory());
		}
	}
	
	/**
	 * Finds tree item with specific id
	 * 
	 * @param  Category_ID (identifier for category object)
	 * 
	 * @return TreeItem (item with given category id)
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
	 * @param Assignment_ID	 (ID of the needed positions)
	 * 
	 * @return Position [] (position objects for specific assignment)
	 * 
	 * @throws SQLException  (thrown when a data base connection error occurs)
	 * @throws IOException 	 (thrown when corrupt data is imported from the data base)
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
	 * @param  User_ID (ID of the needed assignments)
	 * 
	 * @throws SQLException  (thrown when a data base connection error occurs)
	 * @throws IOException 	 (thrown when corrupt data is imported from the data base)
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
	 * @throws SQLException  (thrown when a data base connection error occurs)
	 * @throws IOException 	 (thrown when corrupt data is imported from the data base)
	 */
	private void importCompanyList() throws SQLException, IOException {
		try{
			//get HashMap from DB
			HashMap<String,String[]> dataFromDB = this.dbHandler.getCompanyList();
			if (dataFromDB.size() > 0) {	
				//loop for parsing the HashMap
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
	 * 
	 * @param  assignment_ID (id to filter specific offers from database)
	 * 
	 * @throws SQLException  (thrown when a data base connection error occurs)
	 * @throws IOException 	 (thrown when corrupt data is imported from the data base)
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
	
	/**
	 * Method to update active user
	 * 
	 * @param every single attribute of user object
	 * 
	 * @throws SQLException  (thrown when a data base connection error occurs)
	 * @throws IOException 	 (thrown when corrupt data is imported from the data base)
	 */
	public void updateUser(String Username, String Vorname, String Nachname, String Strasse,
						 String Hausnummer, String Postleitzahl, String Stadt, String Email, String Telefonnummer,
						 String Firma, String Geschlecht) throws SQLException, IOException { 
		dbHandler.updateUser(Username, Vorname, Nachname, Strasse, Hausnummer, Postleitzahl, Stadt, Email, Telefonnummer, Firma, Geschlecht);
	}

	/** 
	 * Method to log off a user automatically and return to the Login Screen. 
	 * 
	 * @throws SQLException  (thrown when a data base connection error occurs)
	 * @throws IOException 	 (thrown when corrupt data is imported from the data base)
	 */
	public void deleteUser() throws SQLException, IOException {
		dbHandler.deleteUser(activeUser.getUserID());
		//TODO no function to log off is implemented
	}

	/**
	 * This method creates a new Assignment from data entered into the GUI and appends it to the assignmentHandler's list.
	 * 
	 * @param every single attribute of assignment object 
	 *
	 * @throws SQLException  (thrown when a data base connection error occurs)
	 * @throws IOException 	 (thrown when corrupt data is imported from the data base)
	 */
	public String createAssignment(String description, String dateOfCreation,String title, String dueDate) throws SQLException, IOException {
		DatumFull buf = new DatumFull(dueDate);
		buf.minusDays(7);
		String id = dbHandler.createAssignment(activeUser.getUserID(), description, dateOfCreation,
											   buf.toMachineString(), title, dueDate);
		return id;
	}
	
	
	/**
	 * Deletes a specific Assignment within the database (updates the status in database! no real deletion)
	 * 
	 * @param assignmentID (ID to identify assignment to delete)
	 * 
	 * @throws SQLException  (thrown when a data base connection error occurs)
	 * @throws IOException 	 (thrown when corrupt data is imported from the data base)
	 */
	public void deleteAssignment(String assignment_ID) throws SQLException, IOException {
		dbHandler.deleteAssignment(assignment_ID);
		
		dbHandler.cancelAllOffer(assignment_ID);
	}
	
	/**
	 * Accepts a specific Offer within the database
	 * 
	 * @param offer_ID (ID of the offer to accept)
	 * 
	 * @throws SQLException  (thrown when a data base connection error occurs)
	 * @throws IOException 	 (thrown when corrupt data is imported from the data base)
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

	/**
	 * Declines a specific Offer within the database
	 * 
	 * @param offer_ID (ID of the offer to decline)
	 * 
	 * @throws SQLException  (thrown when a data base connection error occurs)
	 * @throws IOException 	 (thrown when corrupt data is imported from the data base)
	 */
	public void declineOffer(String offer_ID) throws SQLException, IOException {
		dbHandler.rejectOffer(offer_ID);
	}
	
	/**
	 * This method creates a new Position from data entered into the GUI and appends it to the assignment within assignmentHandler's list.
	 * 
	 * @param  every single attribute of position object
	 * 
	 * @throws SQLException  (thrown when a data base connection error occurs)
	 * @throws IOException 	 (thrown when corrupt data is imported from the data base)
	 */
	public void createPosition(String category_ID, String assignment_ID, String description, String amount) throws SQLException, IOException {
		//add entry in database
		dbHandler.createPosition(category_ID, assignment_ID, description, amount);
	}
	
	
	/**
	 * Gets specific category with given ID
	 * 
	 * @param Cat_ID (ID of the category you want to find)
	 *  
	 * @return Category (Category Object with given ID)
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
	 * 
	 * @param 	Assignment_ID (ID of the needed assignment)
	 * 
	 * @return	Assignment (object with given id or null if not in there)
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
	 * Generates the table columns for the table of my assignments 
	 * 
	 * @param table (parent of created table columns)
	 */
	public void generateTableHeaderMyAssignments(final Table table) {
		TableColumn tblClmnTitle = new TableColumn(table, SWT.CENTER);
		tblClmnTitle.setWidth(218);
		tblClmnTitle.setText("Bezeichnung");
		tblClmnTitle.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					Sorter.sortStringTableItemsMyAssignments(table, 0, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsMyAssignments(table, 0, false);
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
					Sorter.sortStringTableItemsMyAssignments(table, 1, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsMyAssignments(table, 1, false);
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
					Sorter.sortDateTableItemsMyAssignments(table, 2, asc);
					asc = false;
				}
				else {
					Sorter.sortDateTableItemsMyAssignments(table, 2, false);
					asc = true;
				}
			}
		});
		TableColumn tblClmnIcon = new TableColumn(table, SWT.CENTER);
		tblClmnIcon.setWidth(43);
		tblClmnIcon.setText("");
	}

	/**
	 * Generates the table items for the table of my assignments 
	 * 
	 * @param table (parent of created table items)
	 */
	public void generateMyAssignmentTableItemsDashboard(Table table) {
		for (Assignment a : assignmentHandler.getAssignmentList()) {
			TableItem tableItem = new TableItem(table, SWT.CENTER);
			tableItem.setText(new String[] {a.getTitle(), a.getStatus(), a.getDeadline()});
			tableItem.setData("id", a.getAssignmentID());
			
			//add different states with small icon
			switch (a.getStatus()) {
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
	
	/**
	 * Generates the table columns for the table of companies
	 * 
	 * @param table (parent of created table columns)
	 */
	public void generateTableHeaderCompanyTable(final Table table) {
		TableColumn tblClmnName = new TableColumn(table, SWT.CENTER);
		tblClmnName.setWidth(150);
		tblClmnName.setText("Name");
		tblClmnName.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					Sorter.sortStringTableItemsCompanies(table, 0, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsCompanies(table, 0, false);
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
					Sorter.sortStringTableItemsCompanies(table, 1, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsCompanies(table, 1, false);
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
					Sorter.sortStringTableItemsCompanies(table, 2, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsCompanies(table, 2, false);
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
					Sorter.sortNumberTableItemsCompanies(table, 3, asc);
					asc = false;
				}
				else {
					Sorter.sortNumberTableItemsCompanies(table, 3, false);
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
					Sorter.sortStringTableItemsCompanies(table, 4, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsCompanies(table, 4, false);
					asc = true;
				}
			}
		});
		TableColumn tblClmnPhone = new TableColumn(table, SWT.CENTER);
		tblClmnPhone.setWidth(120);
		tblClmnPhone.setText("Telefonnummer");
		tblClmnPhone.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					Sorter.sortStringTableItemsCompanies(table, 5, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsCompanies(table, 5, false);
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
					Sorter.sortStringTableItemsCompanies(table, 6, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsCompanies(table, 6, false);
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
					Sorter.sortStringTableItemsCompanies(table, 7, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsCompanies(table, 7, false);
					asc = true;
				}
			}
		});
	}
	
	/**
	 * Generates the table items for the table of companies
	 * 
	 * @param table (parent of created table items)
	 */
	public void generateCompanyTableItems(Table table) {
		for (Company c : companyList) {
			TableItem tableItem = new TableItem(table, SWT.CENTER);
			tableItem.setText(new String[] {c.getName(),c.getStreet(),c.getNumber(),String.valueOf(c.getPostCode()),
											c.getOwner(), c.getPhone(), c.getEMail(),c.getDescription()});
			tableItem.setData("id", c.getCompanyID());
		}
	}
	
	/**
	 * Generates the table columns for the table of assignment view 
	 * 
	 * @param table (parent of created table columns)
	 */
	public void generateTableHeaderAssignmentTable(final Table table) {
		TableColumn tblClmnTitle = new TableColumn(table, SWT.CENTER);
		tblClmnTitle.setWidth(220);
		tblClmnTitle.setText("Bezeichnung");
		tblClmnTitle.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					Sorter.sortStringTableItemsAssignments(table, 0, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsAssignments(table, 0, false);
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
					Sorter.sortDateTableItemsAssignments(table, 1, asc);
					asc = false;
				}
				else {
					Sorter.sortDateTableItemsAssignments(table, 1, false);
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
					Sorter.sortDateTableItemsAssignments(table, 2, asc);
					asc = false;
				}
				else {
					Sorter.sortDateTableItemsAssignments(table, 2, false);
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
					Sorter.sortDateTableItemsAssignments(table, 3, asc);
					asc = false;
				}
				else {
					Sorter.sortDateTableItemsAssignments(table, 3, false);
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
					Sorter.sortStringTableItemsAssignments(table, 4, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsAssignments(table, 4, false);
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
					Sorter.sortStringTableItemsAssignments(table, 5, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsAssignments(table, 5, false);
					asc = true;
				}
			}
		});
		TableColumn tblClmnIcon = new TableColumn(table, SWT.CENTER);
		tblClmnIcon.setWidth(25);
		tblClmnIcon.setText("");
	}
	
	/**
	 * Generates the table items for the table of assignment view 
	 * 
	 * @param table (parent of created table items)
	 */
	public void generateMyAssignmentTableItems(Table table) {
		for (Assignment a : assignmentHandler.getAssignmentList()) {
			TableItem tableItem = new TableItem(table, SWT.CENTER);
			tableItem.setText(new String[] {a.getTitle(), a.getDateOfCreation(), a.getDeadline(),
											a.getDueDate(), a.getStatus(), a.getDescription()});
			tableItem.setData("id", a.getAssignmentID());
			
			//add different states with small icons
			switch (a.getStatus()) {
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
	
	/**
	 * Generates the table columns for the offer table 
	 * 
	 * @param table (parent of created table columns)
	 */
	public void generateTableHeaderOfferTable(final Table table) {
		TableColumn tblClmnTitle = new TableColumn(table, SWT.CENTER);
		tblClmnTitle.setWidth(200);
		tblClmnTitle.setText("Firma");
		tblClmnTitle.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					Sorter.sortStringTableItemsOffer(table, 0, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsOffer(table, 0, false);
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
					Sorter.sortNumberTableItemsOffer(table, 1, asc);
					asc = false;
				}
				else {
					Sorter.sortNumberTableItemsOffer(table, 1, false);
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
					Sorter.sortStringTableItemsOffer(table, 2, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsOffer(table, 2, false);
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
					Sorter.sortStringTableItemsOffer(table, 3, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsOffer(table, 3, false);
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
					Sorter.sortStringTableItemsOffer(table, 4, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsOffer(table, 4, false);
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
					Sorter.sortDateTableItemsOffer(table, 5, asc);
					asc = false;
				}
				else {
					Sorter.sortDateTableItemsOffer(table, 5, false);
					asc = true;
				}
			}
		});
	}
	
	/**
	 * Generates the table items for the offer table 
	 * 
	 * @param table (parent of created table items)
	 */
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
	
	/**
	 * Generates the table columns for the table of next offer 
	 * 
	 * @param table (parent of created table columns)
	 */
	public void generateTableHeaderNextOfferTable(final Table table) {
		TableColumn tblClmnTitle = new TableColumn(table, SWT.CENTER);
		tblClmnTitle.setWidth(215);
		tblClmnTitle.setText("Auftrag");
		tblClmnTitle.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (asc) {
					Sorter.sortStringTableItemsOffer(table, 0, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsOffer(table, 0, false);
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
					Sorter.sortStringTableItemsOffer(table, 1, asc);
					asc = false;
				}
				else {
					Sorter.sortStringTableItemsOffer(table, 1, false);
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
					Sorter.sortNumberTableItemsOffer(table, 2, asc);
					asc = false;
				}
				else {
					Sorter.sortNumberTableItemsOffer(table, 2, false);
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
					Sorter.sortDateTableItemsOffer(table, 3, asc);
					asc = false;
				}
				else {
					Sorter.sortDateTableItemsOffer(table, 3, false);
					asc = true;
				}
			}
		});
	}
	
	/**
	 * Generates the table items for the table of next offer 
	 * 
	 * @param table (parent of created table items)
	 */
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

	/**
	 * Generates HashMap for the active User 
	 * 
	 * @return HashMap (containing all attributes of the object and their values)
	 */
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

	/**
	 * Generates HashMap for an assignment 
	 * 
	 * @return HashMap (containing all attributes of the object and their values)
	 */
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
	
	/**
	 * Generates HashMap for the an offer 
	 * 
	 * @return HashMap (containing all attributes of the object and their values)
	 */
	public HashMap<String,String> genereateOfferHashMap(String offer_ID) throws SQLException, IOException {
		Offer offer = null;
		Company comp = null;
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
	
	/**
	 * Generates HashMap for a company 
	 * 
	 * @return HashMap (containing all attributes of the object and their values)
	 */
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


	/**
	 * Getter and Setter needed for test classes
	 */
	public ArrayList<TreeItem> getServiceTreeList() {return serviceTreeList;}
	public ArrayList<TreeItem> getPositionTreeList() {return positionTreeList;}
}

