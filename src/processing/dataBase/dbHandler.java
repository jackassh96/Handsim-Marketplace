package dataBase;

import helper.DatumFull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class dbHandler {
	
	private String dbUrl;
	private String dbUser;
	private String dbPw;
	private String dbName = "benzn";
	
	private String userTable = "nutzer";
	private String companyTable = "tn_teilnehmer";
	private String assingmentTable = "assignments";
	private String categoryTable = "categories";
	private String positionTable = "position";
	
	private String rejectStateName = "Abgelehnt";
			
	//default constructor (hard coded)
	public dbHandler() {
		this.dbUrl = "jdbc:mysql://localhost:3306";	//TODO
		this.dbUser = "root";	//TODO
		this.dbPw = "";		//TODO
	}
	
	//specific constructor 
	public dbHandler(String serverUrl, String databaseUser, String userPassword, String Databasename) {
		this.dbUrl = serverUrl;
		this.dbUser = databaseUser;
		this.dbPw = userPassword;
	}

	//TODO Documentation 
	private Connection setUpConnection() throws SQLException {
		Connection con = null;
		int tries = 0;
		int maxTries = 3;
				
		while (con == null && tries < maxTries) {

			try {
				 con = DriverManager.getConnection(dbUrl, dbUser, dbPw); 
			}
			catch (SQLException e) {
				con = null;
				throw new SQLException("Keine Verbindung !\n" + e.getMessage()); //TODO write Errortext
			}
			finally { tries++; }
		}
		return con;
	}
	
	//TODO Documentation 
	public boolean createUser(String username, String password) throws SQLException {
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("INSERT INTO " + dbName + "." + userTable + " ( Nutzername , Passwort ) VALUES ( \"" + username + "\" , \"" + password + "\" );");
				pst.execute();
		}
		catch (SQLException ex) {
			throw new SQLException("User konnte nicht angelegt werden! \n" + ex.getMessage()); //TODO write Errortext
		}
		con.close();
		return true;
	}
	
	//TODO Documentation 
	public boolean deleteUser(String username) throws SQLException {
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("DELETE FROM " + dbName + "." + userTable + " WHERE Nutzername=\"" + username +"\"");
				pst.execute();
		}
		catch (SQLException ex) {
			throw new SQLException("User konnte nicht angelegt werden! \n" + ex.getMessage()); //TODO write Errortext
		}
		con.close();
		return true;
	}
	
	//TODO Documentation 
	public boolean updateUser(String oldUsername, String newUsername, String oldPassword, String newPassword) throws SQLException {
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("UPDATE " + dbName + "." + userTable + " SET Nutzername=\"" + newUsername + "\" , Passwort=\"" + newPassword + "\" WHERE Nutzername=\"" + oldUsername +"\" AND Passwort=\"" + oldPassword + "\"");
				pst.execute();
		}
		catch (SQLException ex) {
			throw new SQLException("User update gescheitert! \n" + ex.getMessage()); //TODO write Errortext
		}
		con.close();
		return true;
	}
	
	//TODO Documentation 
	public boolean createUserTable() throws SQLException {
		Connection con = setUpConnection();
		try {	//TODO fix statement to create database structure
				System.out.println();
				PreparedStatement pst = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + dbName + "." + userTable + " ( " +
															"`Nutzername` varchar(20) COLLATE utf8_unicode_ci NOT NULL,+" +
															"`Password` varchar(20) COLLATE utf8_unicode_ci NOT NULL," +
															" PRIMARY KEY (`Nutzername`)" +
															") ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=62");
				pst.execute();
		}
		catch (SQLException ex) {
			throw new SQLException("Tabelle konnte nicht angelegt werden!\n" + ex.getMessage()); //TODO write Errortext
		}
		con.close();
		return true;
	}
	
	//TODO Documentation
	public boolean checkLogInData(String username, String password) throws SQLException {
		Connection con = setUpConnection();
		boolean correct = false; 
		try {
				PreparedStatement pst = con.prepareStatement("SELECT * FROM " + dbName + "." + userTable + " WHERE Nutzername=\"" + username + "\" AND Passwort =\"" + password + "\"");
				ResultSet neu = pst.executeQuery("SELECT * FROM " + dbName + "." + userTable + " WHERE Nutzername=\"" + username + "\" AND Passwort =\"" + password + "\"");
				correct = neu.first();
		}
		catch (SQLException ex) {
			throw new SQLException("Login fehlgeschlagen :(!\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { con.close(); }
	
		return correct;
	}
	
	//TODO Documentation
	public boolean checkUserExistence(String username) throws SQLException {
		boolean exists = false;
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("SELECT * FROM " + dbName + "." + userTable + " WHERE Nutzername=\"" + username  + "\"");
				ResultSet neu = pst.executeQuery("SELECT * FROM " + dbName + "." + userTable + " WHERE Nutzername=\"" + username + "\"");
				exists = neu.first();
		}
		catch (SQLException ex) {
			throw new SQLException("Datenbankabfrage gescheitert :(!\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { con.close(); }
		return exists;
	}

	//TODO Documentation
	public String encodePw(String password) {
	    String geheim = "RaJzEkTSFRbW54oBwkfryQ"; 
		
		try {
		      // byte-Array erzeugen
		      byte[] key = (geheim).getBytes("UTF-8");
		      // aus dem Array einen Hash-Wert erzeugen mit MD5 oder SHA
		      MessageDigest sha = MessageDigest.getInstance("MD5");
		      key = sha.digest(key);
		      // nur die ersten 128 bit nutzen
		      key = Arrays.copyOf(key, 16); 
		      // der fertige Schluessel
		      SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
	      
		      // Verschluesseln
		      Cipher cipher;
		      cipher = Cipher.getInstance("AES");
		      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		      byte[] encrypted = cipher.doFinal(password.getBytes());
		 
		      // bytes zu Base64-String konvertieren (dient der Lesbarkeit)
		      BASE64Encoder myEncoder = new BASE64Encoder();
		      geheim = myEncoder.encode(encrypted);
		      
		      //dont give the hint its base64 :D
		      geheim = geheim.replaceAll("==", "");
	      }
	      catch (Exception ex) {
	    	  //TODO throw new WasAuchImmerException -> nothing to do hard error!
	      }
		
		return geheim;
	}
	
	//TODO Documentation
	public HashMap<String,String[]> getCompanyList() throws SQLException{
//		INSERT INTO `tn_teilnehmer` (`t_id`, `anrede`, `vorname`, `nachname`, `firma`, `gehalt`, `country`, `region`, `province_code`, `kurs_id`, `berechtigung`, `email`, `pass`, `regdate`, `status`, `code`) VALUES
		boolean exists = false;
		ResultSet neu = null;
		HashMap<String,String[]> hFirmenMap = new HashMap<>();
		
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("");
				neu = pst.executeQuery("SELECT * FROM " + dbName + "." + companyTable + " ORDER BY firma ASC");
				exists = neu.first();
				//TODO evaluate exists
		}
		catch (SQLException ex) {
			throw new SQLException("Datenbankabfrage (Unternehmensliste) gescheitert !\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { 
			int i = 0;
			do {
				String [] rowStr = new String[5];
				rowStr[0] = neu.getNString("firma");
				rowStr[1] = neu.getNString("anrede");
				rowStr[2] = neu.getNString("vorname");
				rowStr[3] = neu.getNString("nachname");
				rowStr[4] = neu.getNString("email");
				
				
				hFirmenMap.put(String.valueOf(i), rowStr);
				i++;
			} while (neu.next()); 
			con.close(); 
		}
		
		return hFirmenMap;
	}

	//TODO Documentation 
	public boolean createAssignmentTable() throws SQLException {
	Connection con = setUpConnection();
	try {	//TODO fix statement to create database structure
			PreparedStatement pst = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + dbName + "." + assingmentTable + " ( " +
														"`Assignment_ID` int(15) NOT NULL AUTO_INCREMENT," +
														"`Owner` varchar(100) COLLATE utf8_unicode_ci NOT NULL," +
														"`PositionList` varchar(100) COLLATE utf8_unicode_ci NOT NULL," +
														"`OfferHandler` varchar(15) COLLATE utf8_unicode_ci NOT NULL," + 
														"`Description` varchar(100) COLLATE utf8_unicode_ci NOT NULL," + 
														"`DateOfCreation` varchar(15) COLLATE utf8_unicode_ci NOT NULL," +
														"`Deadline` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL," +
														"`Status` varchar(15) COLLATE utf8_unicode_ci NOT NULL," +
														"`Title` varchar(15) COLLATE utf8_unicode_ci NOT NULL," + 
														"`DueDate` varchar(15) COLLATE utf8_unicode_ci NOT NULL," + 
														" PRIMARY KEY (`Assignment_ID`)" +
														") ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=62");
			pst.execute();
	}
	catch (SQLException ex) {
		throw new SQLException("Tabelle (Assignment) konnte nicht angelegt werden!\n" + ex.getMessage()); //TODO write Errortext
	}
	con.close();
	return true;
	}
	
	//TODO Documentation 
	public boolean createAssignment( String owner, Position[] positionList, OfferHandler  oHandler, String description, DatumFull dateOfCreation, DatumFull deadline , String status, String title, DatumFull dueDate) throws SQLException {
	Connection con = setUpConnection();
	try {	//TODO fix statement to create database structure
			PreparedStatement pst = con.prepareStatement("INSERT INTO " + dbName + "." + assingmentTable + " " +
														"( Assignment_ID, Owner, PositionList , OfferHandler , Description , DateOfCreation , Deadline , Status , Title , DueDate )" +
														//set NULL for ID for auto_increment and automatically generated id
														" VALUES ( NULL, \"" + owner + "\" , \"" + positionList + "\" , \"" + oHandler + "\" , \"" +
																	description + "\" , \"" + dateOfCreation + "\" , \"" + deadline + "\" , \"" + status + "\" , \"" + 
																	title + "\" , \"" + dueDate + "\" );");
			pst.execute();
	}
	catch (SQLException ex) {
		throw new SQLException("Wert konnte nicht eingefügt werden!\n" + ex.getMessage()); //TODO write Errortext
	}
	con.close();
	return true;
	}
	
	//TODO Documentation 
	public boolean deleteAssignment(String assignment_ID) throws SQLException {
	Connection con = setUpConnection();
	try {	//TODO fix statement to create database structure -> Status -> "Abgelehnt"?
			PreparedStatement pst = con.prepareStatement("UPDATE  " + dbName + "." + assingmentTable + " SET Status=\"" + rejectStateName + "\"" +
														"WHERE Assignment_ID=\"" + assignment_ID +"\";");
			pst.execute();
	}
	catch (SQLException ex) {
		throw new SQLException("Tabelle konnte nicht angelegt werden!\n" + ex.getMessage()); //TODO write Errortext
	}
	con.close();
	return true;
	}
	
	//TODO Documentation 
	public boolean updateAssignmentStatus(String assignment_ID, String Status) throws SQLException {
	Connection con = setUpConnection();
	try {	//TODO fix statement to create database structure -> Status -> "Abgelehnt"?
			PreparedStatement pst = con.prepareStatement("UPDATE  " + dbName + "." + assingmentTable + " SET Status=\"" + Status + "\"" +
														"WHERE Assignment_ID=\"" + assignment_ID +"\";");
			pst.execute();
	}
	catch (SQLException ex) {
		throw new SQLException("Tabelle konnte nicht angelegt werden!\n" + ex.getMessage()); //TODO write Errortext
	}
	con.close();
	return true;
	}
	
	
	int x = 23; //-> Zeilen
	int y = 5; //-> Spalten
	
//	String[][] neurwfe; 
	
	String[][] arS = new String[x][y];
	
	

//	ArrayList<String[]> b = new ArrayList<>();
	//TODO DATENBANKTABELLE SIEHT WIE AUS???
//	getCategories	--> Hole alle Categorien 
//	CREATE TABLE categories ( `Category_ID` int(15) NOT NULL AUTO_INCREMENT , `Title` varchar(25) COLLATE utf8_unicode_ci NOT NULL, `Parent_Category` varchar(15) COLLATE utf8_unicode_ci NOT NULL, `Sub_Category_List` varchar(50) COLLATE utf8_unicode_ci NOT NULL, PRIMARY KEY (`Category_ID`)) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=62;

	public HashMap<String,String[]> getCategories() throws SQLException {
		boolean exists = false;
		ResultSet neu = null;
		HashMap<String,String[]> hCategoryMap = new HashMap<>();
		
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("");
				neu = pst.executeQuery("SELECT * FROM " + dbName + "." + categoryTable);
				exists = neu.first();
				//TODO evaluate exists
		}
		catch (SQLException ex) {
			throw new SQLException("Datenbankabfrage (Kategorieliste) gescheitert !\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { 
			int i = 0;
			do {
				String [] rowStr = new String[4];
				rowStr[0] = String.valueOf(neu.getInt("Category_ID"));
				rowStr[1] = neu.getNString("Title");
				rowStr[2] = neu.getNString("Parent_Category");
				rowStr[3] = neu.getNString("Sub_Category_List");
				
				hCategoryMap.put(String.valueOf(i), rowStr);
				i++;
			} while (neu.next());
			con.close(); 
		}
		
		return hCategoryMap;
	}
	
	//TODO Documentation 
	public HashMap<String,String[]> getAssignments(String owner) throws SQLException {
		boolean exists = false;
		ResultSet neu = null;
		HashMap<String,String[]> hAssignmentMap = new HashMap<>();
		
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("");
				neu = pst.executeQuery("SELECT * FROM " + dbName + "." + assingmentTable + " WHERE Owner=\"" + owner + "\"");
				exists = neu.first();
				//TODO evaluate exists
		}
		catch (SQLException ex) {
			throw new SQLException("Datenbankabfrage (Aufträge/Ausschreibungen) gescheitert !\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { 
			int i = 0;
			do {
				//( Assignment_ID, Owner, PositionList , OfferHandler , Description , DateOfCreation , Deadline , Status , Title , DueDate )
				String [] rowStr = new String[10];
				rowStr[0] = String.valueOf(neu.getInt("Assignment_ID"));
				rowStr[1] = neu.getNString("Owner");
				rowStr[2] = neu.getNString("PositionList");
				rowStr[3] = neu.getNString("OfferHandler");
				rowStr[4] = neu.getNString("Description");
				rowStr[5] = neu.getNString("DateOfCreation");
				rowStr[6] = neu.getNString("Deadline");
				rowStr[7] = neu.getNString("Status");
				rowStr[8] = neu.getNString("Title");
				rowStr[9] = neu.getNString("DueDate");
				
				hAssignmentMap.put(String.valueOf(i), rowStr);
				i++;
			} while (neu.next());
			con.close(); 
		}
		return hAssignmentMap;
	}
	
	//TODO DATENBANKTABELLE SIEHT WIE AUS???
//	
//	getPositionList
//	CREATE TABLE positions ( `Position_ID` int(15) NOT NULL AUTO_INCREMENT , `Owner` varchar(25) COLLATE utf8_unicode_ci NOT NULL,, `Category` varchar(25) COLLATE utf8_unicode_ci NOT NULL, `Description` varchar(25) COLLATE utf8_unicode_ci NOT NULL, `Amount` int(15) COLLATE utf8_unicode_ci NOT NULL, PRIMARY KEY (`Position_ID`)) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=62;
	
	
	//TODO Documentation 
	public HashMap<String,String[]> getPositionList(String ids) throws SQLException {
		boolean exists = false;
		ResultSet neu = null;
		HashMap<String,String[]> hPositionMap = new HashMap<>();
		
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("");
				neu = pst.executeQuery("SELECT * FROM " + dbName + "." + positionTable + " WHERE Owner=\"");// + owner + "\"");
				exists = neu.first();
				//TODO evaluate exists
		}
		catch (SQLException ex) {
			throw new SQLException("Datenbankabfrage (Positionen) gescheitert !\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { 
			int i = 0;
			do {
				//(`Position_ID`, `Category`, `Description`, `Amount`)
				String [] rowStr = new String[4];
				rowStr[0] = String.valueOf(neu.getInt("Position_ID"));
				rowStr[1] = neu.getNString("Category");
				rowStr[2] = neu.getNString("Description");
				rowStr[3] = neu.getNString("Amount");
				
				hPositionMap.put(String.valueOf(i), rowStr);
				i++;
			} while (neu.next());
			con.close(); 
		}
		return hPositionMap;
	}
//	TODO 's
//	createPosition
	
	
	
	//++
//	IMPLEMENTED
//	setUpConnection
//	createUserTable -> just for testing
//	checkUserExistence
//	checkLogInData
//	createUser
//	updateUser
//	deleteUser
//	encryptPW
// 	getCompanyList
//  createAssignmentTable -> just for testing
//	createAssignment	
//	deleteAssignment	
//	updateAssignmentStatus
//  getAssignments
	
	
	//GETTER AND SETTER
	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPw() {
		return dbPw;
	}

	public void setDbPw(String dbPw) {
		this.dbPw = dbPw;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}


}
