package processing.dataBase;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.text.Position;

import processing.data.OfferHandler;
import processing.helper.DatumFull;
import sun.misc.BASE64Encoder;
import sun.security.pkcs.EncodingException;


public class dbHandler {

	/**
	 *  GENERAL TODO's :
	 *	-> Documentation
	 *	-> Tests 
	 *	-> Comments
	 */

		
	 /* 
	  * attributes
	  */
	private String dbUrl;
	private String dbUser;
	private String dbPw;
	
	private String dbName = "Handsim";
	
	private String userTable = "marketuser";
	private String companyTable = "tn_teilnehmer";
	private String assingmentTable = "marketassignments";
	private String categoryTable = "marketcategories";
	private String positionTable = "marketpositions";
	private String offerTable = "marketoffer";
	
	//GLOBAL STRINGS FOR Assignment open; canceled; assigned; done
	private String openStateName = "open";
	private String rejectStateName = "canceled";
	private String acceptStateName = "assigned"; //TODO
	private String doneStateName = "done";
	
	//GLOBAL STRINGS FOR Offer 	open; declined; accepted; CanceledByAssignment
	private String openStateOffer = "open";
	private String declinedStateOffer = "declined";
	private String acceptStateOffer = "accepted"; //TODO
	private String doneStateOffer = "done";
	private String rejctStateOffer = "rejected";
	private String canceledStateOffer = "CanceledByAssignment";
	
	
	
	/**
	 * default constructor	-	uses hard coded parameter for database connection 
	 * 
	 */
	public dbHandler() {
		this.dbUrl = "jdbc:mysql://localhost:3306";	//TODO
		this.dbUser = "root";	//TODO
		this.dbPw = "";		//TODO
	}
	
	/**
	 * specific constructor	-	possibility to change database connection while client is running 
	 * 
	 */ 
	public dbHandler(String serverUrl, String databaseUser, String userPassword, String Databasename) {
		this.dbUrl = serverUrl;
		this.dbUser = databaseUser;
		this.dbPw = userPassword;
	}

	/**
	 * Creates database connection 
	 * 
	 * @throws SQLException
	 *            if object can't get database connection with a maximum of 3 tries 
	 */
	public Connection setUpConnection() throws SQLException {
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
	
	/**
	 * Creates user entry in database table
	 *  
	 * @param	Username		
	 * @param	Password		
	 * @param	Vorname
	 * @param	Nachname
	 * @param	Strasse
	 * @param	Hausnummer
	 * @param	Postleitzahl
	 * @param	Stadt
	 * @param	Email
	 * @param	Telefonnummer
	 * @param	Firma
	 * @param   Geschlecht
	 * 
	 * @return boolean			should return true if successful otherwise false TODO ->evaluate boolean
	 *  
	 * @throws IOException		input data or output statement is corrupt 
	 * @throws SQLException		if object can't get database connection with a maximum of 3 tries 
	 */
	public boolean createUser(String Username, String Password, String Vorname, String Nachname, String Strasse, String Hausnummer,
			  String Postleitzahl, String Stadt, String Email, String Telefonnummer, String Firma, String Geschlecht) throws SQLException, IOException {
		Connection con = setUpConnection();
		try {
			PreparedStatement pst = con.prepareStatement("INSERT INTO " + dbName + "." + userTable + 
					" ( User_ID , Password , First_Name , Last_Name , Street , Number ," +
					" Post_Code , City , Email , Phone , Company , Gender ) VALUES ( \"" + 
					Username + "\" , \"" + Password + "\" , \"" + Vorname + "\" , \"" + Nachname + "\" , \"" + Strasse + "\" , \"" +
					Hausnummer + "\" , \"" + Postleitzahl + "\" , \"" + Stadt + "\" , \"" + Email + "\" , \"" + Telefonnummer + "\" , \"" +
					Firma + "\" , \"" + Geschlecht + "\" );");
			pst.execute();
		}
		catch (SQLException ex) {
			throw new IOException("User konnte nicht angelegt werden! \n" + ex.getMessage()); //TODO write Errortext
		}
		con.close();
		return true;
	}
	
	/**
	 * Delete user entry in database table
	 *  
	 * @param  Username			user id
	 * 
	 * @return boolean			should return true if successful otherwise false TODO ->evaluate boolean
	 *  
	 * @throws IOException		input data or output statement is corrupt 
	 * @throws SQLException		if object can't get database connection with a maximum of 3 tries 
	 */ 
	public boolean deleteUser(String username) throws SQLException, IOException {
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("DELETE FROM " + dbName + "." + userTable + " WHERE User_ID=\"" + username +"\"");
				pst.execute();
		}
		catch (SQLException ex) {
			throw new IOException("User konnte nicht angelegt werden! \n" + ex.getMessage()); //TODO write Errortext
		}
		con.close();
		return true;
	}
	
	/**
	 * Update user password and/or user name (user id) in database table
	 *  
	 * @param  oldUsername			old user id
	 * @param  newUsername			new user id 
	 * @param  oldPassword			old password
	 * @param  newPassword			new password
	 * 
	 * @return boolean			should return true if successful otherwise false TODO ->evaluate boolean
	 *  
	 * @throws IOException		input data or output statement is corrupt 
	 * @throws SQLException		if object can't get database connection with a maximum of 3 tries 
	 */ 
	public boolean updateUserNamePW(String oldUsername, String newUsername, String oldPassword, String newPassword) throws SQLException, IOException {
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("UPDATE " + dbName + "." + userTable + " SET User_ID=\"" + newUsername + "\" , Password=\"" + newPassword + "\" WHERE User_ID=\"" + oldUsername +"\" AND Password=\"" + oldPassword + "\"");
				pst.execute();
		}
		catch (SQLException ex) {
			throw new IOException("User update gescheitert! \n" + ex.getMessage()); //TODO write Errortext
		}
		con.close();
		return true;
	}
	
	
	/**
	 * Update user values in database table
	 *  
	 * @param  Username		
	 * @param  Password		
	 * @param  Vorname
	 * @param  Nachname
	 * @param  Strasse
	 * @param  Hausnummer
	 * @param  Postleitzahl
	 * @param  Stadt
	 * @param  Email
	 * @param  Telefonnummer
	 * @param  Firma
	 * @param  Geschlecht
	 * 
	 * @return boolean			should return true if successful otherwise false TODO ->evaluate boolean
	 *  
	 * @throws IOException		input data or output statement is corrupt 
	 * @throws SQLException		if object can't get database connection with a maximum of 3 tries 
	 */  
	public boolean updateUser(String Username, String Vorname, String Nachname, String Strasse, String Hausnummer,
							  String Postleitzahl, String Stadt, String Email, String Telefonnummer, String Firma, String Geschlecht) throws SQLException, IOException {
		Connection con = setUpConnection();
		try {
//			UPDATE `marketuser` SET  `User_ID`='peterX',  `Password`='000',  `First_Name`='Peter',  `Last_Name`='Pan',  `Street`='Freidrichstr.',  `Number`='104',  `Post_Code`='13317',  `City`='Berlin',  `Email`='ppan@pan.de',  `Phone`='0190123456',  `Company`='Pan AG',  `Gender`='maennlich' WHERE `User_ID` = '';
				PreparedStatement pst = con.prepareStatement("UPDATE " + dbName + "." + userTable + 
						" SET User_ID=\"" + Username + "\" , First_Name=\"" + Vorname + 
						"\" , Last_Name=\"" + Nachname + "\" , Street=\"" + Strasse + "\" , Number=\"" + Hausnummer +
						"\" , Post_Code=\"" + Postleitzahl + "\" , City=\"" + Stadt + "\" , Email=\"" + Email +
						"\" , Phone=\"" + Telefonnummer + "\" , Company=\"" + Firma + "\" , Gender=\"" + Geschlecht +
						"\" WHERE User_ID=\"" + Username + "\"");
				pst.execute();

		}
		catch (SQLException ex) {
			throw new IOException("User update gescheitert! \n" + ex.getMessage()); //TODO write Errortext
		}
		con.close();
		return true;
	}
	
	/**
	 * Proves if log in data given by the user is correct (exists in database table)
	 *  
	 * @param  Username		
	 * @param  Password		
	 * 
	 * @return boolean			should return true if successful otherwise false TODO ->evaluate boolean
	 *  
	 * @throws IOException		input data or output statement is corrupt 
	 * @throws SQLException		if object can't get database connection with a maximum of 3 tries 
	 */ 
	public boolean checkLogInData(String username, String password) throws SQLException, IOException {
		Connection con = setUpConnection();
		boolean correct = false; 
		try {
			
				PreparedStatement pst = con.prepareStatement("SELECT * FROM " + dbName + "." + userTable + " WHERE User_ID=\"" + username + "\" AND Password =\"" + password + "\"");
				ResultSet neu = pst.executeQuery("SELECT * FROM " + dbName + "." + userTable + " WHERE User_ID=\"" + username + "\" AND Password =\"" + password + "\"");
				correct = neu.first();
		}
		catch (SQLException ex) {
			throw new IOException("Login fehlgeschlagen :(!\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { con.close(); }

		return correct;
	}
	
	/**
	 * Proves if given user name is already existing
	 *  
	 * @param  Username		
	 * @param  Password		
	 * 
	 * @return boolean			returns true if user already exists, otherwise false
	 *  
	 * @throws IOException		input data or output statement is corrupt 
	 * @throws SQLException		if object can't get database connection with a maximum of 3 tries 
	 */ 
	public boolean checkUserExistence(String username) throws SQLException, IOException {
		boolean exists = false;
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("SELECT * FROM " + dbName + "." + userTable + " WHERE User_ID=\"" + username  + "\"");
				ResultSet neu = pst.executeQuery("SELECT * FROM " + dbName + "." + userTable + " WHERE User_ID=\"" + username + "\"");
				exists = neu.first();
		}
		catch (SQLException ex) {
			throw new IOException("Datenbankabfrage gescheitert :(!\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { con.close(); }
		return exists;
	}
	
	/**
	 * Loads specific user data from database table
	 *  
	 * @param  Username		
	 * @param  Password		
	 * 
	 * @return boolean			returns true if user already exists, otherwise false
	 *  
	 * @throws IOException		input data or output statement is corrupt 
	 * @throws SQLException		if object can't get database connection with a maximum of 3 tries 
	 */ 
	public String [] loadUserData(String User_id) throws SQLException, IOException {
		boolean exists = false;
		ResultSet neu = null;
		String [] rowStr = null;
		
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("SELECT * FROM " + dbName + "." + userTable + " WHERE User_ID=\"" + User_id  + "\"");
				neu = pst.executeQuery("SELECT * FROM " + dbName + "." + userTable + " WHERE User_ID=\"" + User_id + "\"");
				exists = neu.first();
		}
		catch (SQLException ex) {
			throw new IOException("Datenbankabfrage gescheitert :(!\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { 
			
			if (exists) {
				rowStr = new String[12];
				rowStr[0] = neu.getNString("User_ID");
				rowStr[1] = neu.getNString("Password");
				rowStr[2] = neu.getNString("First_Name");
				rowStr[3] = neu.getNString("Last_Name");
				rowStr[4] = neu.getNString("Street");
				rowStr[5] = neu.getNString("Number");
				rowStr[6] = String.valueOf(neu.getInt("Post_Code"));
				rowStr[7] = neu.getNString("City");
				rowStr[10] = neu.getNString("Email");
				rowStr[9] = neu.getNString("Phone");
				rowStr[8] = neu.getNString("Company");
				rowStr[11] = neu.getNString("Gender");
			}
			con.close();
			 }
		return rowStr;
	}

	/**
	 * Encodes the users password 
	 *  		
	 * @param  password			
	 * 
	 * @return String				encoded password string			
	 * 
	 * @throws EncoderException		if encoding didn't work 
	 */ 
	public String encodePw(String password) throws ParseException, EncodingException {
		
		//TODO change after last github commit!
		String randomString = "RaJzEkTSFRbW54oBwkfryQ"; 
		try {
		      // byte-Array erzeugen
		      byte[] key = (randomString).getBytes("UTF-8");
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
		      randomString = myEncoder.encode(encrypted);
		      
		      //dont give a hint its base64 :D
		      randomString = randomString.replaceAll("==", "");
	      }
	      catch (Exception ex) {
	    	  //TODO throw new WasAuchImmerException -> nothing to do hard error!
	    	  throw new EncodingException(""); //TODO fix error msg
	      }
		
		return randomString;
	}
	
	/**
	 * Loads company list from database table	
	 * 
	 * @return HashMap<String,String[]>		containing every single database entry with a key string for iterations
	 *  
	 * @throws IOException					if output statement is corrupt 
	 * @throws SQLException					if object can't get database connection with a maximum of 3 tries 
	 */
	public HashMap<String,String[]> getCompanyList() throws SQLException, IOException{
		boolean exists = false;
		ResultSet neu = null;
		HashMap<String,String[]> hFirmenMap = new HashMap<>();
		
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("");
				neu = pst.executeQuery("SELECT * FROM " + dbName + "." + companyTable + " ORDER BY firma ASC");
				exists = neu.first();
		}
		catch (SQLException ex) {
			throw new IOException("Datenbankabfrage (Unternehmensliste) gescheitert !\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { 
			int i = 0;
			do {
				if (exists) {
					String [] rowStr = new String[9];
					rowStr[0] = String.valueOf(neu.getInt("t_id"));
					rowStr[1] = neu.getNString("firma");
					rowStr[2] = neu.getNString("street");
					rowStr[3] = neu.getNString("number");
					rowStr[4] = neu.getNString("postcode");
					rowStr[5] = neu.getNString("vorname") + " " + neu.getNString("nachname");
					rowStr[6] = neu.getNString("phone");
					rowStr[7] = neu.getNString("email");
					rowStr[8] = neu.getNString("description");
					hFirmenMap.put(String.valueOf(i), rowStr);
				}
				i++;
			} while (neu.next()); 
			con.close(); 
		}
		
		return hFirmenMap;
	}

	
	
	/**
	 * Creates assignment in database table	
	 * TODO params 
	 * @return boolean			should return true if successful otherwise false TODO ->evaluate boolean
	 *  
	 * @throws IOException		if output statement is corrupt 
	 * @throws SQLException		if object can't get database connection with a maximum of 3 tries 
	 */ 
	//	TODO TEST!
	public String createAssignment( String owner, String description, String dateOfCreation, String deadline , String title, String dueDate) throws SQLException, IOException {
	Connection con = setUpConnection();
	ResultSet neu = null;
	boolean exists = false;
	String temp = "";
	
	try {	//TODO fix statement to create database structure
			PreparedStatement pst = con.prepareStatement("INSERT INTO " + dbName + "." + assingmentTable + " " +
														"( Assignment_ID, Owner, Description , DateOfCreation , Deadline , Status , Title , DueDate )" +
														//set NULL for ID for auto_increment and automatically generated id
														" VALUES ( NULL, \"" + owner + "\" , \"" + description + "\" , \"" + dateOfCreation + "\" , \"" +
														deadline + "\" , \"" + openStateName + "\" , \"" + title + "\" , \"" + dueDate + "\" );");
			pst.execute();
			
	}
	catch (SQLException ex) {
		throw new IOException("Wert konnte nicht eingefuegt werden!\n" + ex.getMessage()); //TODO write Errortext
	}
	
	try {
		PreparedStatement pst2 = con.prepareStatement("");
		neu = pst2.executeQuery("SELECT * FROM " + dbName + "." + assingmentTable + " WHERE Owner=\"" + owner + "\" AND Description=\"" + description + "\" " +
								"AND DateOfCreation=\"" + dateOfCreation + "\" AND Deadline=\"" + deadline + "\" " +
								"AND Status=\"" + openStateName + "\" AND Title=\"" + title + "\" " +
								"AND DueDate=\"" + dueDate + "\"");
		
		exists = neu.first();
		temp = String.valueOf(neu.getInt(1));
		
	} catch (Exception ex) {
		throw new IOException(ex.getMessage());
	}
	
	con.close();
	return temp;
	}
	
	/**
	 * Sets assignment status to declined in database table	
	 * 
	 * @return boolean			should return true if successful otherwise false TODO ->evaluate boolean
	 *  
	 * @throws IOException		if output statement is corrupt 
	 * @throws SQLException		if object can't get database connection with a maximum of 3 tries 
	 */ 
	public boolean deleteAssignment(String assignment_ID) throws SQLException, IOException {
	Connection con = setUpConnection();
	try {	//TODO fix statement to create database structure -> Status -> "Abgelehnt"?
			PreparedStatement pst = con.prepareStatement("UPDATE  " + dbName + "." + assingmentTable + " SET Status=\"" + rejectStateName + "\"" +
														"WHERE Assignment_ID=\"" + assignment_ID +"\";");
			pst.execute();
	}
	catch (SQLException ex) {
		throw new IOException("Tabelle konnte nicht angelegt werden!\n" + ex.getMessage()); //TODO write Errortext
	}
	con.close();
	return true;
	}
	
	/**
	 * Updates assignment status in database table	
	 * 
	 * @return boolean			should return true if successful otherwise false TODO ->evaluate boolean
	 *  
	 * @throws IOException		if output statement is corrupt 
	 * @throws SQLException		if object can't get database connection with a maximum of 3 tries 
	 */  
	public boolean acceptAssignmentStatus(String assignment_ID) throws SQLException, IOException {
	Connection con = setUpConnection();
	try {	//TODO fix statement to create database structure -> Status -> "Abgelehnt"?
			PreparedStatement pst = con.prepareStatement("UPDATE  " + dbName + "." + assingmentTable + " SET Status=\"" + acceptStateName + "\"" +
														"WHERE Assignment_ID=\"" + assignment_ID +"\";");
			pst.execute();
	}
	catch (SQLException ex) {
		throw new IOException("Auftrag konnte nicht aktualisiert werden!\n" + ex.getMessage()); //TODO write Errortext
	}
	con.close();
	return true;
	}
	
	//TODO DOCUMENTATION + Test
	public boolean finishAssignmentStatus(String assignment_ID) throws SQLException, IOException {
		Connection con = setUpConnection();
		try {	
				PreparedStatement pst = con.prepareStatement("UPDATE  " + dbName + "." + assingmentTable + " SET Status=\"" + doneStateName + "\"" +
															"WHERE Assignment_ID=\"" + assignment_ID +"\";");
				pst.execute();
		}
		catch (SQLException ex) {
			throw new IOException("Auftrag konnte nicht aktualisiert werden!\n" + ex.getMessage()); //TODO write Errortext
		}
		con.close();
		return true;
		}

	/**
	 * Updates offer status in database table	
	 * 
	 * @return boolean			should return true if successful otherwise false TODO ->evaluate boolean
	 *  
	 * @throws IOException		if output statement is corrupt 
	 * @throws SQLException		if object can't get database connection with a maximum of 3 tries 
	 */   
	public boolean acceptOffer(String Offer_ID) throws SQLException, IOException {
	Connection con = setUpConnection();
	try {	
			PreparedStatement pst = con.prepareStatement("UPDATE  " + dbName + "." + offerTable + " SET Status=\"" + acceptStateOffer + "\"" +
														"WHERE Offer_ID=\"" + Offer_ID +"\";");
			pst.execute();
	}
	catch (SQLException ex) {
		throw new IOException("Angebot konnte nicht aktualisiert werden!\n" + ex.getMessage()); //TODO write Errortext
	}
	con.close();
	return true;
	}
	
	//TODO DOCU
	public boolean rejectOffer(String Offer_ID) throws SQLException, IOException {
		Connection con = setUpConnection();
		try {	
				PreparedStatement pst = con.prepareStatement("UPDATE  " + dbName + "." + offerTable + " SET Status=\"" + rejctStateOffer + "\"" +
															"WHERE Offer_ID=\"" + Offer_ID +"\";");
				pst.execute();
		}
		catch (SQLException ex) {
			throw new IOException("Angebot konnte nicht aktualisiert werden!\n" + ex.getMessage()); //TODO write Errortext
		}
		con.close();
		return true;
	}
	
	//TODO DOCU + test
	public boolean cancelAllOtherOffer(String Assignment_ID, String Offer_ID) throws SQLException, IOException {
		Connection con = setUpConnection();
		try {	
				PreparedStatement pst = con.prepareStatement("UPDATE  " + dbName + "." + offerTable + " SET Status=\"" + rejctStateOffer + "\"" +
															"WHERE Assignment_ID=\"" + Assignment_ID +"\" AND Offer_ID!=\"" + Offer_ID +"\";");
				pst.execute();
		}
		catch (SQLException ex) {
			throw new IOException("Angebot konnte nicht aktualisiert werden!\n" + ex.getMessage()); //TODO write Errortext
		}
		con.close();
		return true;
	}
	
	//TODO DOCU + test
		public boolean cancelAllOffer(String Assignment_ID) throws SQLException, IOException {
			Connection con = setUpConnection();
			try {	
					PreparedStatement pst = con.prepareStatement("UPDATE  " + dbName + "." + offerTable + " SET Status=\"" + canceledStateOffer + "\"" +
																"WHERE Assignment_ID=\"" + Assignment_ID +"\";");
					pst.execute();
			}
			catch (SQLException ex) {
				throw new IOException("Angebot konnte nicht aktualisiert werden!\n" + ex.getMessage()); //TODO write Errortext
			}
			con.close();
			return true;
		}

	/**
	 * Loads category list from database table	
	 * 
	 * @return HashMap<String,String[]>		containing every single database entry with a key string for iterations
	 *  
	 * @throws IOException					if output statement is corrupt 
	 * @throws SQLException					if object can't get database connection with a maximum of 3 tries 
	 */
	public HashMap<String,String[]> getCategories() throws SQLException, IOException {
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
			throw new IOException("Datenbankabfrage (Kategorieliste) gescheitert !\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { 
			int i = 0;
			do {
				if (exists) {
					String [] rowStr = new String[3];
					rowStr[0] = String.valueOf(neu.getInt("Category_ID"));
					rowStr[1] = neu.getNString("Title");
					rowStr[2] = String.valueOf(neu.getInt("Parent_Category"));
					hCategoryMap.put(String.valueOf(i), rowStr);
				}
				i++;
			} while (neu.next());
			con.close(); 
		}
		
		return hCategoryMap;
	}
	
	/**
	 * Loads assignment list from database table	
	 * 
	 * @return HashMap<String,String[]>		containing every single database entry with a key string for iterations
	 *  
	 * @throws IOException					if output statement is corrupt 
	 * @throws SQLException					if object can't get database connection with a maximum of 3 tries 
	 */ 
	public HashMap<String,String[]> getAssignments(String owner) throws SQLException, IOException {
		boolean exists = false;
		ResultSet neu = null;
		HashMap<String,String[]> hAssignmentMap = new HashMap<>();
		
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("");
				neu = pst.executeQuery("SELECT * FROM " + dbName + "." + assingmentTable + " WHERE Owner=\"" + owner + "\"");
				exists = neu.first();
		}
		catch (SQLException ex) {
			throw new IOException("Datenbankabfrage (Auftraege/Ausschreibungen) gescheitert !\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { 
			int i = 0;
			do {
				if (exists) {
					String [] rowStr = new String[7];
					rowStr[0] = String.valueOf(neu.getInt("Assignment_ID"));
	//				rowStr[1] = neu.getNString("Owner");
	//				rowStr[2] = neu.getNString("PositionList");
	//				rowStr[3] = neu.getNString("OfferHandler");
					rowStr[1] = neu.getNString("Description");
					rowStr[2] = neu.getNString("DateOfCreation");
					rowStr[3] = neu.getNString("Deadline");
					rowStr[4] = neu.getNString("Status");
					rowStr[5] = neu.getNString("Title");
					rowStr[6] = neu.getNString("DueDate");
					hAssignmentMap.put(String.valueOf(i), rowStr);
				}
				i++;
			} while (neu.next());
			con.close(); 
		}
		return hAssignmentMap;
	}
	
	/**
	 * Loads offer list from database table	
	 * 
	 * @return HashMap<String,String[]>		containing every single database entry with a key string for iterations
	 *  
	 * @throws IOException					if output statement is corrupt 
	 * @throws SQLException					if object can't get database connection with a maximum of 3 tries 
	 */
	public HashMap<String,String[]> getOffer(String Assignment_ID) throws SQLException, IOException {
		boolean exists = false;
		ResultSet neu = null;
		HashMap<String,String[]> hOfferMap = new HashMap<>();
		
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("");
				neu = pst.executeQuery("SELECT * FROM " + dbName + "." + offerTable + " WHERE Assignment_ID=\"" + Assignment_ID + "\"");
				exists = neu.first();
		}
		catch (SQLException ex) {
			throw new IOException("Datenbankabfrage (Kategorieliste) gescheitert !\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { 
			int i = 0;
			do {
				if (exists) {
					String [] rowStr = new String[8];
					rowStr[0] = String.valueOf(neu.getInt("Offer_ID"));
					rowStr[1] = String.valueOf(neu.getInt("Assignment_ID"));
					rowStr[2] = neu.getNString("Company_ID");
					rowStr[3] = String.valueOf(neu.getDouble("Price"));
					rowStr[4] = neu.getNString("AmountOfTimeNeeded");
					rowStr[5] = neu.getNString("Description");
					rowStr[6] = neu.getNString("Date");
					rowStr[7] = neu.getNString("Status");	
					hOfferMap.put(String.valueOf(i), rowStr);
				}
				i++;
			} while (neu.next());
			con.close(); 
		}
		
		return hOfferMap;
	}
	
	//TODO DOCU
	public HashMap<String,String[]> getSpecificOffer(String Offer_ID) throws SQLException, IOException {
		boolean exists = false;
		ResultSet neu = null;
		HashMap<String,String[]> hOfferMap = new HashMap<>();
		
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("");
				neu = pst.executeQuery("SELECT * FROM " + dbName + "." + offerTable + " WHERE Offer_ID=\"" + Offer_ID + "\"");
				exists = neu.first();
		}
		catch (SQLException ex) {
			throw new IOException("Datenbankabfrage (Kategorieliste) gescheitert !\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { 
			int i = 0;
			do {
				if (exists) {
					String [] rowStr = new String[8];
					rowStr[0] = String.valueOf(neu.getInt("Offer_ID"));
					rowStr[1] = String.valueOf(neu.getInt("Assignment_ID"));
					rowStr[2] = neu.getNString("Company_ID");
					rowStr[3] = String.valueOf(neu.getDouble("Price"));
					rowStr[4] = neu.getNString("AmountOfTimeNeeded");
					rowStr[5] = neu.getNString("Description");
					rowStr[6] = neu.getNString("Date");
					rowStr[7] = neu.getNString("Status");	
					hOfferMap.put(String.valueOf(i), rowStr);
				}
				i++;
			} while (neu.next());
			con.close(); 
		}
		
		return hOfferMap;
	}
	
	
	/**
	 * Loads position list from database table	
	 * 
	 * @return HashMap<String,String[]>		containing every single database entry with a key string for iterations
	 *  
	 * @throws IOException					if output statement is corrupt 
	 * @throws SQLException					if object can't get database connection with a maximum of 3 tries 
	 */ 
	public HashMap<String,String[]> getPositionList(String Assignment_ID) throws SQLException, IOException {
		boolean exists = false;
		ResultSet neu = null;
		HashMap<String,String[]> hPositionMap = new HashMap<>();
		Connection con = setUpConnection();
		try {	
			PreparedStatement pst = con.prepareStatement("");
			neu = pst.executeQuery("SELECT * FROM " + dbName + "." + positionTable + " WHERE Assignment_ID=\"" + Assignment_ID + "\"");
			exists = neu.first();
		}
		catch (SQLException ex) {
			throw new IOException("Datenbankabfrage (Positionen) gescheitert !\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { 
			int i = 0;
			do {
				if (exists) {
					String [] rowStr = new String[5];
					rowStr[0] = String.valueOf(neu.getInt("Position_ID"));
					rowStr[1] = String.valueOf(neu.getInt("Category_ID"));
					rowStr[2] = String.valueOf(neu.getInt("Assignment_ID"));
					rowStr[3] = neu.getNString("Description");
					rowStr[4] = neu.getNString("Amount");
					hPositionMap.put(String.valueOf(i), rowStr);
				}
				i++;
			} while (neu.next());
			con.close(); 
		}
		return hPositionMap;
	}
	
	/**
	 * Loads user list from database table	
	 * 
	 * @return HashMap<String,String[]>		containing every single database entry with a key string for iterations
	 *  
	 * @throws IOException					if output statement is corrupt 
	 * @throws SQLException					if object can't get database connection with a maximum of 3 tries 
	 */
	public HashMap<String,String[]> getUsers(String Username) throws SQLException, IOException {
		boolean exists = false;
		ResultSet neu = null;
		HashMap<String,String[]> hUserMap = new HashMap<>();
		
		Connection con = setUpConnection();
		try {
				PreparedStatement pst = con.prepareStatement("");
				neu = pst.executeQuery("SELECT * FROM " + dbName + "." + userTable + " WHERE User_ID=\"" + Username + "\"");
				exists = neu.first();
		}
		catch (SQLException ex) {
			throw new IOException("Datenbankabfrage (Kategorieliste) gescheitert !\n" + ex.getMessage()); //TODO write Errortext
		}
		finally { 
			int i = 0;
			do {
				if (exists) {
					String [] rowStr = new String[11];
					rowStr[0] = neu.getNString("User_ID");
					rowStr[1] = neu.getNString("First_Name");
					rowStr[2] = neu.getNString("Last_Name");
					rowStr[3] = neu.getNString("Street");
					rowStr[4] = neu.getNString("Number");
					rowStr[5] = String.valueOf(neu.getInt("Post_Code"));
					rowStr[6] = neu.getNString("City");
					rowStr[7] = neu.getNString("Email");
					rowStr[8] = neu.getNString("Phone");
					rowStr[9] = neu.getNString("Company");
					rowStr[10] = neu.getNString("Gender");
					hUserMap.put(String.valueOf(i), rowStr);
				}
				i++;
			} while (neu.next());
			con.close(); 
		}
		
		return hUserMap;
	}
		


	/**
	 * Creates Position in database table	
	 * TODO params
	 * @return boolean			should return true if successful otherwise false TODO ->evaluate boolean
	 *  
	 * @throws IOException		input or output statement is corrupt 
	 * @throws SQLException		if object can't get database connection with a maximum of 3 tries 
	 */  
	public boolean createPosition(String category_ID, String assignment_ID, String description, String amount) throws SQLException, IOException {
	Connection con = setUpConnection();
	try {	//TODO fix statement to create database structure
			PreparedStatement pst = con.prepareStatement("INSERT INTO " + dbName + "." + positionTable + " " +
														"( Position_ID, Category_ID, Assignment_ID, Description , Amount )" +
														//set NULL for ID for auto_increment and automatically generated id
														" VALUES ( NULL, \"" + category_ID + "\" , \"" + assignment_ID + "\" , \"" + description +
														"\" , \"" + amount + "\" );");
			pst.execute();
	}
	catch (SQLException ex) {
		throw new IOException("Wert konnte nicht eingefuegt werden!\n" + ex.getMessage()); //TODO write Errortext
	}
	con.close();
	return true;
	}
	
	
	/**
	 * Creates Position in database table	
	 * TODO params, untested!
	 * @return boolean			should return true if successful otherwise false TODO ->evaluate boolean
	 *  
	 * @throws IOException		input or output statement is corrupt 
	 * @throws SQLException		if object can't get database connection with a maximum of 3 tries 
	 */
	public boolean deletePosition(String Position_ID) throws SQLException, IOException {
	Connection con = setUpConnection();
	try {	//TODO fix statement to create database structure -> Status -> "Abgelehnt"?
			PreparedStatement pst = con.prepareStatement("DELETE FROM  " + dbName + "." + positionTable + " WHERE Position_ID=\"" + Position_ID +"\";");
			pst.execute();
	}
	catch (SQLException ex) {
		throw new IOException("Tabelle konnte nicht angelegt werden!\n" + ex.getMessage()); //TODO write Errortext
	}
	con.close();
	return true;
	}
	
	
	//GETTER AND SETTER needed TODO?
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
