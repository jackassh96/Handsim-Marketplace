package gui;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
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
import swing2swt.layout.BorderLayout;

public class NeuerBenutzerWindow extends Shell {
	private Text vornameTextField;
	private Text nachnameTextField;
	private Text straﬂeTextField;
	private Text postleitzahlTextField;
	private Text stadtTextField;
	private Text hausnummerTextField;
	private Text unternehmensTextField;
	private Text emailTextField;
	private Text telefonTextField;
	private Label unternehmensLabel;
	private Combo geschlechtCombo;
	private Text passwortTextField;
	private Text benutzernameTextField;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		
	}

	/**
	 * Create the shell.
	 * @param display
	 */
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
		
		Button profilSpeichernButton = new Button(profilRightRightLowContainer, SWT.NONE);
		profilSpeichernButton.setBounds(0, 0, 105, 35);
		profilSpeichernButton.setText("Speichern");
		profilSpeichernButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if(stadtTextField.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Es wurde keine Stadt eingetragen!", "Fehler!", 2);
					return;
				}
				if(vornameTextField.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Es wurde kein Vorname eingetragen!", "Fehler!", 2);
					return;
				}
				if(nachnameTextField.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Es wurde kein Nachname eingetragen!", "Fehler!", 2);
					return;
				}
				if(telefonTextField.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Es wurde keine Telefonnummer eingetragen!", "Fehler!", 2);
					return;
				}
				if(straﬂeTextField.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Es wurde keine Straﬂe eingetragen!", "Fehler!", 2);
					return;
				}
				if(hausnummerTextField.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Es wurde keine Hausnummer eingetragen!", "Fehler!", 2);
					return;
				}
				if(!(emailTextField.getText().contains("@") && emailTextField.getText().contains("."))){
					JOptionPane.showMessageDialog(null, "Keine g¸ltige E-Mail-Addresse eingetragen!", "Fehler!", 2);
					return;
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
		
		Label straﬂeLabel = new Label(profilMiddleContainer, SWT.NONE);
		straﬂeLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		straﬂeLabel.setText("Straﬂe");
		
		Label telefonLabel = new Label(profilMiddleContainer, SWT.NONE);
		telefonLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		telefonLabel.setText("Telefon");
		
		straﬂeTextField = new Text(profilMiddleContainer, SWT.BORDER);
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
		telefonTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		telefonTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		//VerifyListener filtering letters out
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
		geschlechtCombo.add("M‰nnlich");
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
