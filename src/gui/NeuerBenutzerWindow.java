package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import processing.dataBase.dbHandler;
import sun.security.pkcs.EncodingException;
import swing2swt.layout.BorderLayout;

public class NeuerBenutzerWindow extends Shell {
	
	
	private Text vornameTextField, nachnameTextField, straﬂeTextField, postleitzahlTextField, stadtTextField, 
	hausnummerTextField, unternehmensTextField, emailTextField, telefonTextField, passwortTextField, benutzernameTextField;
	private Label unternehmensLabel, benutzernameLabel, passwortLabel, vornameLabel, nachnameLabel, emailLabel,
	straﬂeLabel, telefonLabel, hausnummerLabel, geschlechtLabel, postleitzahlLabel, stadtLabel;
	private Combo geschlechtCombo;
	private Composite profilContainer, profilLowContainer, profilRightLowContainer, 
	profilRightRightLowContainer, profilMiddleContainer, profilTopContainer, profilLeftLowContainer;
	private Button profilSpeichernButton, profilAbbrechenButton;
	
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		
	}

	/**
	 *  TODOs:
	 *  - abbrechen button
	 *  - check benutzereingabe -> Fehlermeldung entsprechnd implementieren
	 *  - create User in db
	 *  - close window open loginwindow
	 *  
	 */
	
	/**
	 * Create the shell.
	 * @param display
	 */
	//static-access is the easiest way to force log in window to open if window is closed by user without using buttons
	@SuppressWarnings("static-access")
	public NeuerBenutzerWindow() {
		super(Display.getDefault(), SWT.SHELL_TRIM);
		setLayout(new FillLayout());
		
		profilContainer = new Composite(this, SWT.NONE);
		profilContainer.setLayout(new BorderLayout(0, 0));
		
		profilLowContainer = new Composite(profilContainer, SWT.NONE);
		profilLowContainer.setLayoutData(BorderLayout.SOUTH);
		profilLowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		profilLeftLowContainer = new Composite(profilLowContainer, SWT.NONE);
		
		
		profilRightLowContainer = new Composite(profilLowContainer, SWT.NONE);
		profilRightLowContainer.setLayout(new BorderLayout(0, 0));
		
		profilRightRightLowContainer = new Composite(profilRightLowContainer, SWT.NONE);
		profilRightRightLowContainer.setLayoutData(BorderLayout.EAST);
		
		profilAbbrechenButton = new Button(profilRightRightLowContainer, SWT.NONE);
		profilAbbrechenButton.setToolTipText("Benutzererstellung abbrechen");
		profilAbbrechenButton.setBounds(0, 0, 105, 35);
		profilAbbrechenButton.setText("Abbrechen");
		profilAbbrechenButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
					NeuerBenutzerWindow.this.dispose();
			}
		});
		
		profilSpeichernButton = new Button(profilRightRightLowContainer, SWT.NONE);
		profilSpeichernButton.setToolTipText("Neuen Benutzer speichern");
		profilSpeichernButton.setBounds(105, 0, 105, 35);
		profilSpeichernButton.setText("Speichern");
		profilSpeichernButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				boolean boolVorname = false, boolNachname = false, boolStraﬂe = false, boolPlz = false, boolStadt = false, boolNummer = false,
						boolUnternehmen = false, boolEmail = false, boolTelefon = false, boolGeschlecht = false, boolPasswort = false,boolBenutzername = false;
				String errmsg = "Es ist ein Fehler aufgetreten, \nbitte ¸berpr¸fen Sie folgende Felder:\n\n";
				
				
				if(stadtTextField.getText().isEmpty()){
					boolStadt = true;
					errmsg += String.format("%20s", "Stadt") + "\n";
				}
				if(vornameTextField.getText().isEmpty()){
					boolVorname = true;
					errmsg += String.format("%20s", "Vorname") + "\n";
				}
				if(nachnameTextField.getText().isEmpty()){
					boolNachname = true;
					errmsg += String.format("%20s", "Nachname") + "\n";
				}
				if(telefonTextField.getText().isEmpty()){
					boolTelefon = true;
					errmsg += String.format("%20s", "Telefon") + "\n";
				}
				if(straﬂeTextField.getText().isEmpty()){
					boolStraﬂe = true;
					errmsg += String.format("%20s", "Straﬂe") + "\n";
				}
				if(hausnummerTextField.getText().isEmpty()){
					boolNummer = true;
					errmsg += String.format("%20s", "Hausnummer") + "\n";
				}
				if(!(emailTextField.getText().contains("@") && emailTextField.getText().contains("."))){
					boolEmail = true;
					errmsg += String.format("%20s", "Email") + "\n";
				}
				if(unternehmensTextField.getText().isEmpty()){
					boolUnternehmen = true;
					errmsg += String.format("%20s", "Unternehmen") + "\n";
				}
				if(postleitzahlTextField.getText().isEmpty()){
					boolPlz = true;
					errmsg += String.format("%20s", "Postleitzahl") + "\n";
				}
				if(passwortTextField.getText().isEmpty()){
					boolPasswort = true;
					errmsg += String.format("%20s", "Passwort") + "\n";
				}
				if(geschlechtCombo.getText().isEmpty() || ((!geschlechtCombo.getText().equals("M‰nnlich")) && (!geschlechtCombo.getText().equals("Weiblich")))){
					boolGeschlecht = true;
					errmsg += String.format("%20s", "Geschlecht") + "\n";
				}
				if(benutzernameTextField.getText().isEmpty()){
					boolBenutzername = true;
					errmsg += String.format("%20s", "\tBenutzername") + "\n";
				}
				else {
					dbHandler dbH = new dbHandler();
					try {
						boolean exists = dbH.checkUserExistence(benutzernameTextField.getText());
						if (exists) errmsg += String.format("%20s", "\tBenutzername (existiert bereits!)") + "\n";
					} catch (Exception e1) {
						//TODO
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
				//if any field was filled incorrect create error message
				if (boolBenutzername || boolEmail || boolGeschlecht || boolNachname || boolNummer || boolPasswort ||
					boolPlz || boolStadt || boolStraﬂe || boolTelefon || boolUnternehmen || boolVorname) {
					errmsg += "\n\n";
					JOptionPane.showMessageDialog(null, errmsg);
				}
				else {
					dbHandler dbH = new dbHandler();
					try {
						dbH.createUser(benutzernameTextField.getText(), dbH.encodePw(passwortTextField.getText()), vornameTextField.getText(), nachnameTextField.getText(),
									   straﬂeTextField.getText(), hausnummerTextField.getText(), postleitzahlTextField.getText(), stadtTextField.getText(),
									   emailTextField.getText(), telefonTextField.getText(), unternehmensTextField.getText(), geschlechtCombo.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (EncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Benutzer wurde erfolgreich angelegt.\n");
					NeuerBenutzerWindow.this.dispose();
				}
			}
		});
		
		profilMiddleContainer = new Composite(profilContainer, SWT.NONE);
		profilMiddleContainer.setLayoutData(BorderLayout.CENTER);
		profilMiddleContainer.setLayout(new GridLayout(2, false));
		
		benutzernameLabel = new Label(profilMiddleContainer, SWT.NONE);
		benutzernameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		benutzernameLabel.setText("Benutzername");
		
		passwortLabel = new Label(profilMiddleContainer, SWT.NONE);
		passwortLabel.setText("Passwort");
		
		benutzernameTextField = new Text(profilMiddleContainer, SWT.BORDER);
		benutzernameTextField.setToolTipText("Hier einmaligen Benutzernamen eintragen");
		benutzernameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		passwortTextField = new Text(profilMiddleContainer, SWT.BORDER | SWT.PASSWORD);
		passwortTextField.setToolTipText("Hier gew\u00FCnschtes Passwort eintragen");
		passwortTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		vornameLabel = new Label(profilMiddleContainer, SWT.NONE);
		vornameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		vornameLabel.setText("Vorname");
		
		unternehmensLabel = new Label(profilMiddleContainer, SWT.NONE);
		unternehmensLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		unternehmensLabel.setText("Unternehmen");
		
		vornameTextField = new Text(profilMiddleContainer, SWT.BORDER);
		vornameTextField.setToolTipText("Hier Ihren Vornamen eintragen");
		vornameTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		vornameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		//VerifyListener filtering digits out
		vornameTextField.addVerifyListener(new VerifyListener() {
	        public void verifyText(VerifyEvent e) {
            	if(Character.isDigit(e.character)){
            		e.doit = false;
            	}else{
            		e.doit = true;
            	}
        	}
        });
		
		unternehmensTextField = new Text(profilMiddleContainer, SWT.BORDER);
		unternehmensTextField.setToolTipText("Hier Ihr Unternehmen eintragen");
		unternehmensTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		unternehmensTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		nachnameLabel = new Label(profilMiddleContainer, SWT.NONE);
		nachnameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		nachnameLabel.
		setText("Nachname");
		
		emailLabel = new Label(profilMiddleContainer, SWT.NONE);
		emailLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		emailLabel.setText("E-Mail");
		
		nachnameTextField = new Text(profilMiddleContainer, SWT.BORDER);
		nachnameTextField.setToolTipText("Hier Ihren Nachnamen eintragen");
		nachnameTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		nachnameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		//VerifyListener filtering digits out
		nachnameTextField.addVerifyListener(new VerifyListener() {
	        public void verifyText(VerifyEvent e) {
            	if(Character.isDigit(e.character)){
            		e.doit = false;
            	}else{
            		e.doit = true;
            	}
        	}
        });
		
		emailTextField = new Text(profilMiddleContainer, SWT.BORDER);
		emailTextField.setToolTipText("Hier Ihre E-Mail-Adresse eintragen");
		emailTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		emailTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		straﬂeLabel = new Label(profilMiddleContainer, SWT.NONE);
		straﬂeLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		straﬂeLabel.setText("Straﬂe");
		
		telefonLabel = new Label(profilMiddleContainer, SWT.NONE);
		telefonLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		telefonLabel.setText("Telefon");
		
		straﬂeTextField = new Text(profilMiddleContainer, SWT.BORDER);
		straﬂeTextField.setToolTipText("Hier Ihren Stra\u00DFennamen eintragen");
		straﬂeTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		straﬂeTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		//VerifyListener filtering digits out
		straﬂeTextField.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				if(Character.isDigit(e.character)){
					e.doit = false;
		        }else{
		            e.doit = true;
		        }
		    }
		});
		
		telefonTextField = new Text(profilMiddleContainer, SWT.BORDER);
		telefonTextField.setToolTipText("Hier Ihre Telefonnummer eintragen");
		telefonTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		telefonTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		//VerifyListener filtering letters out
		//TODO needed for phone number???
		telefonTextField.addVerifyListener(new VerifyListener() {
	        public void verifyText(VerifyEvent e) {
	            	if(Character.isAlphabetic(e.character)){
	            		e.doit = false;
	            	}else{
	            		e.doit = true;
	            	}
	        	}
	        });
		
		hausnummerLabel = new Label(profilMiddleContainer, SWT.NONE);
		hausnummerLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		hausnummerLabel.setText("Hausnummer");
		
		geschlechtLabel = new Label(profilMiddleContainer, SWT.NONE);
		geschlechtLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		geschlechtLabel.setText("Geschlecht");
		
		hausnummerTextField = new Text(profilMiddleContainer, SWT.BORDER);
		hausnummerTextField.setToolTipText("Hier Ihre Hausnummer eintragen");
		hausnummerTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		hausnummerTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		geschlechtCombo = new Combo(profilMiddleContainer, SWT.READ_ONLY);
		geschlechtCombo.setToolTipText("Hier Ihr Geschlecht eintragen");
		geschlechtCombo.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		geschlechtCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		geschlechtCombo.add("M‰nnlich");
		geschlechtCombo.add("Weiblich");
		
		postleitzahlLabel = new Label(profilMiddleContainer, SWT.NONE);
		postleitzahlLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		postleitzahlLabel.setText("Postleitzahl");
		
		stadtLabel = new Label(profilMiddleContainer, SWT.NONE);
		stadtLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		stadtLabel.setText("Stadt");
		
		postleitzahlTextField = new Text(profilMiddleContainer, SWT.BORDER);
		postleitzahlTextField.setToolTipText("Hier Ihre Postleitzahl eintragen");
		postleitzahlTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		postleitzahlTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		postleitzahlTextField.setTextLimit(5);
		
		stadtTextField = new Text(profilMiddleContainer, SWT.BORDER);
		stadtTextField.setToolTipText("Hier die Stadt in der Sie leben eintragen");
		stadtTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		stadtTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		stadtTextField.addVerifyListener(new VerifyListener() {
	        public void verifyText(VerifyEvent e) {
            	if(Character.isDigit(e.character)){
            		e.doit = false;
            	}else{
            		e.doit = true;
            	}
        	}
        });
		
		profilTopContainer = new Composite(profilContainer, SWT.NONE);
		profilTopContainer.setLayoutData(BorderLayout.NORTH);
		
		createContents();
		
		this.setImage(new Image(null, ".\\images\\handsimIcon.png"));
		
		try {
			this.open();
			this.layout();
			while (!this.isDisposed()) {
				if (!Display.getDefault().readAndDispatch()) {
					Display.getDefault().sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			new LoginWindow(Display.getDefault()).main(null);
		}
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(966, 580);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
