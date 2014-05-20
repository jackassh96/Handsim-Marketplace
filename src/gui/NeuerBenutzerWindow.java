package gui;

import java.io.IOException;
import java.sql.SQLException;

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

import processing.Controller;
import processing.dataBase.dbHandler;
import swing2swt.layout.BorderLayout;

public class NeuerBenutzerWindow extends Shell {
	private Text vornameTextField;
	boolean boolVorname = false;
	private Text nachnameTextField;
	boolean boolNachname = false;
	private Text straßeTextField;
	boolean boolStraße = false;
	private Text postleitzahlTextField;
	boolean boolPlz = false;
	private Text stadtTextField;
	boolean boolStadt = false;
	private Text hausnummerTextField;
	boolean boolNummer = false;
	private Text unternehmensTextField;
	boolean boolUnternehmen = false;
	private Text emailTextField;
	boolean boolEmail = false;
	private Text telefonTextField;
	boolean boolTelefon = false;
	private Label unternehmensLabel;
	private Combo geschlechtCombo;
	boolean boolGeschlecht = false;
	private Text passwortTextField;
	boolean boolPasswort = false;
	private Text benutzernameTextField;
	boolean boolBenutzername = false;
	
	String errmsg = "Es ist ein Fehler aufgetreten, \nbitte überprüfen Sie folgende Felder:\n";

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
		
		final Composite profilContainer = new Composite(this, SWT.NONE);
		profilContainer.setLayout(new BorderLayout(0, 0));
		
		Composite profilLowContainer = new Composite(profilContainer, SWT.NONE);
		profilLowContainer.setLayoutData(BorderLayout.SOUTH);
		profilLowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite profilLeftLowContainer = new Composite(profilLowContainer, SWT.NONE);
		
		Composite profilRightLowContainer = new Composite(profilLowContainer, SWT.NONE);
		profilRightLowContainer.setLayout(new BorderLayout(0, 0));
		
		Composite profilRightRightLowContainer = new Composite(profilRightLowContainer, SWT.NONE);
		profilRightRightLowContainer.setLayoutData(BorderLayout.EAST);
		
		Button profilAbbrechenButton = new Button(profilRightRightLowContainer, SWT.NONE);
		profilAbbrechenButton.setBounds(0, 0, 105, 35);
		profilAbbrechenButton.setText("Abbrechen");
		profilAbbrechenButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
					NeuerBenutzerWindow.this.dispose();
			}
		});
		
		Button profilSpeichernButton = new Button(profilRightRightLowContainer, SWT.NONE);
		profilSpeichernButton.setBounds(105, 0, 105, 35);
		profilSpeichernButton.setText("Speichern");
		profilSpeichernButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if(stadtTextField.getText().isEmpty()){
					boolStadt = true;
					errmsg += "Stadt\n";
				}
				if(vornameTextField.getText().isEmpty()){
					boolVorname = true;
					errmsg += "Vorname\n";
				}
				if(nachnameTextField.getText().isEmpty()){
					boolNachname = true;
					errmsg += "Nachname\n";
				}
				if(telefonTextField.getText().isEmpty()){
					boolTelefon = true;
					errmsg += "Telefon\n";
				}
				if(straßeTextField.getText().isEmpty()){
					boolStraße = true;
					errmsg += "Straße\n";
				}
				if(hausnummerTextField.getText().isEmpty()){
					boolNummer = true;
					errmsg += "Hausnummer\n";
				}
				if(!(emailTextField.getText().contains("@") && emailTextField.getText().contains("."))){
					boolEmail = true;
					errmsg += "Email\n";
				}
				if(benutzernameTextField.getText().isEmpty()){
					boolBenutzername = true;
					errmsg += "Benutzername\n";
				}
				else {
					dbHandler dbH = new dbHandler();
					try {
						dbH.checkUserExistence(benutzernameTextField.getText());
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
						return;
					}
				}
				//TODO finish check for every field adjust geschlechtscheck
				//if any field was filled incorrect create error message
				if (boolBenutzername || boolEmail || boolGeschlecht || boolNachname || boolNummer || boolNummer || boolPasswort ||
					boolPlz || boolStadt || boolStraße || boolTelefon || boolUnternehmen || boolVorname) {
					JOptionPane.showMessageDialog(null, errmsg);
				}
			}
		});
		
		Composite profilMiddleContainer = new Composite(profilContainer, SWT.NONE);
		profilMiddleContainer.setLayoutData(BorderLayout.CENTER);
		profilMiddleContainer.setLayout(new GridLayout(2, false));
		
		Label benutzernameLabel = new Label(profilMiddleContainer, SWT.NONE);
		benutzernameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		benutzernameLabel.setText("Benutzername");
		
		Label passwortLabel = new Label(profilMiddleContainer, SWT.NONE);
		passwortLabel.setText("Passwort");
		
		benutzernameTextField = new Text(profilMiddleContainer, SWT.BORDER);
		benutzernameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		passwortTextField = new Text(profilMiddleContainer, SWT.BORDER | SWT.PASSWORD);
		passwortTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label vornameLabel = new Label(profilMiddleContainer, SWT.NONE);
		vornameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		vornameLabel.setText("Vorname");
		
		unternehmensLabel = new Label(profilMiddleContainer, SWT.NONE);
		unternehmensLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		unternehmensLabel.setText("Unternehmen");
		
		vornameTextField = new Text(profilMiddleContainer, SWT.BORDER);
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
		unternehmensTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		unternehmensTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label nachnameLabel = new Label(profilMiddleContainer, SWT.NONE);
		nachnameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		nachnameLabel.
		setText("Nachname");
		
		Label emailLabel = new Label(profilMiddleContainer, SWT.NONE);
		emailLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		emailLabel.setText("E-Mail");
		
		nachnameTextField = new Text(profilMiddleContainer, SWT.BORDER);
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
		emailTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		emailTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label straßeLabel = new Label(profilMiddleContainer, SWT.NONE);
		straßeLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		straßeLabel.setText("Straße");
		
		Label telefonLabel = new Label(profilMiddleContainer, SWT.NONE);
		telefonLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		telefonLabel.setText("Telefon");
		
		straßeTextField = new Text(profilMiddleContainer, SWT.BORDER);
		straßeTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		straßeTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		//VerifyListener filtering digits out
		straßeTextField.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				if(Character.isDigit(e.character)){
					e.doit = false;
		        }else{
		            e.doit = true;
		        }
		    }
		});
		
		telefonTextField = new Text(profilMiddleContainer, SWT.BORDER);
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
		
		Label hausnummerLabel = new Label(profilMiddleContainer, SWT.NONE);
		hausnummerLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		hausnummerLabel.setText("Hausnummer");
		
		Label geschlechtLabel = new Label(profilMiddleContainer, SWT.NONE);
		geschlechtLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		geschlechtLabel.setText("Geschlecht");
		
		hausnummerTextField = new Text(profilMiddleContainer, SWT.BORDER);
		hausnummerTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		hausnummerTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		geschlechtCombo = new Combo(profilMiddleContainer, SWT.NONE);
		geschlechtCombo.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		geschlechtCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		geschlechtCombo.add("Männlich");
		geschlechtCombo.add("Weiblich");
		
		Label postleitzahlLabel = new Label(profilMiddleContainer, SWT.NONE);
		postleitzahlLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		postleitzahlLabel.setText("Postleitzahl");
		
		Label stadtLabel = new Label(profilMiddleContainer, SWT.NONE);
		stadtLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		stadtLabel.setText("Stadt");
		
		postleitzahlTextField = new Text(profilMiddleContainer, SWT.BORDER);
		postleitzahlTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		postleitzahlTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		postleitzahlTextField.setTextLimit(5);
		
		stadtTextField = new Text(profilMiddleContainer, SWT.BORDER);
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
		
		Composite profilTopContainer = new Composite(profilContainer, SWT.NONE);
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
