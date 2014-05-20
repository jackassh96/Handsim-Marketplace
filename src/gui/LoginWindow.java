package gui;

import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;

import processing.Controller;
import processing.dataBase.dbHandler;

public class LoginWindow extends Shell {
	private Text passwortTextField;
	private Text benutzernameTextField;
	private dbHandler dbhandler = new dbHandler();
	private Shell Tshell = null;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			LoginWindow shell = new LoginWindow(display);
			shell.setImage(new Image(null, ".\\images\\handsimIcon.png"));
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null,(e.getStackTrace().toString()));
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public LoginWindow(Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		Composite lowerContainer = new Composite(this, SWT.NONE);
		lowerContainer.setLayoutData(BorderLayout.SOUTH);
		lowerContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite leftLowerContainer = new Composite(lowerContainer, SWT.NONE);
		leftLowerContainer.setLayout(new BorderLayout(0, 0));
		
		Label upperLeftLowerLabel = new Label(leftLowerContainer, SWT.NONE);
		upperLeftLowerLabel.setLayoutData(BorderLayout.NORTH);
		
		Label lowerLeftLowerLabel = new Label(leftLowerContainer, SWT.NONE);
		lowerLeftLowerLabel.setLayoutData(BorderLayout.SOUTH);
		
		Button neuButton = new Button(leftLowerContainer, SWT.NONE);
		
		neuButton.setLayoutData(BorderLayout.CENTER);
		neuButton.setText("Neu...");
		
		Composite rightLeftLowerContainer = new Composite(leftLowerContainer, SWT.NONE);
		rightLeftLowerContainer.setLayoutData(BorderLayout.EAST);
		
		Composite leftLeftLowerContainer = new Composite(leftLowerContainer, SWT.NONE);
		leftLeftLowerContainer.setLayoutData(BorderLayout.WEST);
		
		Composite rightLowerContainer = new Composite(lowerContainer, SWT.NONE);
		rightLowerContainer.setLayout(new BorderLayout(0, 0));
		
		Label upperRightLowerLabel = new Label(rightLowerContainer, SWT.NONE);
		upperRightLowerLabel.setLayoutData(BorderLayout.NORTH);
		
		Label lowerRightLowerLabel = new Label(rightLowerContainer, SWT.NONE);
		lowerRightLowerLabel.setLayoutData(BorderLayout.SOUTH);
		
		Composite leftRightLowerContainer = new Composite(rightLowerContainer, SWT.NONE);
		leftRightLowerContainer.setLayoutData(BorderLayout.WEST);
		
		Composite rightRightLowerContainer = new Composite(rightLowerContainer, SWT.NONE);
		rightRightLowerContainer.setLayoutData(BorderLayout.EAST);
		
		Button loginButton = new Button(rightLowerContainer, SWT.NONE);
		
		loginButton.setLayoutData(BorderLayout.CENTER);
		loginButton.setText("Login");
		loginButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
					 login();
			}
		});
		
		Composite leftContainer = new Composite(this, SWT.NONE);
		leftContainer.setLayoutData(BorderLayout.WEST);
		
		Composite rightContainer = new Composite(this, SWT.NONE);
		rightContainer.setLayoutData(BorderLayout.EAST);
		
		Composite middleContainer = new Composite(this, SWT.NONE);
		middleContainer.setLayoutData(BorderLayout.CENTER);
		middleContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label benutzernameLabel = new Label(middleContainer, SWT.NONE);
		benutzernameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		benutzernameLabel.setText("Benutzername:");
		
		benutzernameTextField = new Text(middleContainer, SWT.BORDER);
		benutzernameTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Label passwortLabel = new Label(middleContainer, SWT.NONE);
		passwortLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		passwortLabel.setText("Passwort:");
		
		passwortTextField = new Text(middleContainer, SWT.BORDER | SWT.PASSWORD);
		passwortTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		passwortTextField.addKeyListener(new KeyAdapter(){ 
			public void keyPressed(KeyEvent e){ 
				 if(e.keyCode == SWT.CR){ 
					 login(); 
				 } 
			} 
		});
		
		Label logoLabel = new Label(this, SWT.NONE);
		logoLabel.setFont(SWTResourceManager.getFont("Calibri", 18, SWT.NORMAL));
		logoLabel.setLayoutData(BorderLayout.NORTH);
		logoLabel.setText("HANDSIM-Marketplace");
		
		neuButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LoginWindow.this.dispose();
				new NeuerBenutzerWindow();
				
			}
		});
		
		
		
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 300);

	}
	

	/**
	* Starts the login of the user
	*/
	private void login(){
		boolean isCorrect = false;
	 	if (!(benutzernameTextField.getText().isEmpty()) && !(passwortTextField.getText().isEmpty()) ) {
	 		try {
	 				isCorrect = dbhandler.checkLogInData(benutzernameTextField.getText(), dbhandler.encodePw(passwortTextField.getText()));
	 				if (isCorrect) {
	 					String [] userdata = dbhandler.loadUserData(benutzernameTextField.getText());
	 					Controller neu = Controller.getInstance();
	 					neu.init(userdata, new dbHandler());
	 					LoginWindow.this.dispose();
	 					new CSPmainWindows();
	 				}
	 				else {
	 					JOptionPane.showMessageDialog(null, "Bitte �berpr�fen Sie Ihre Eingaben! \nDer Benutzername und das angegebene Password stimmen nicht �berein.");
	 				}
	 		} catch (Exception e1) {
	 			// TODO FIX ERRORHANDLING
	 			e1.printStackTrace();
	 			JOptionPane.showMessageDialog(null,(e1.getMessage()));
	 		}
	 	}
	 	else {
	 			JOptionPane.showMessageDialog(null, "Bitte �berpr�fen Sie Ihre Eingaben! \nBeide Textfelder (Benutzername und Passwort) m�ssen dabei gef�llt werden.");
	 		}
	 }

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
