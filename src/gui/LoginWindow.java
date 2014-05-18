package gui;

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
	private Composite lowerContainer, leftLowerContainer, rightLeftLowerContainer, leftLeftLowerContainer,
	rightLowerContainer, leftRightLowerContainer, rightRightLowerContainer, leftContainer, rightContainer, middleContainer;
	private Label upperLeftLowerLabel, lowerLeftLowerLabel, upperRightLowerLabel, lowerRightLowerLabel, benutzernameLabel,
	passwortLabel, logoLabel;
	private Button neuButton, loginButton;
	
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
		
		lowerContainer = new Composite(this, SWT.NONE);
		lowerContainer.setLayoutData(BorderLayout.SOUTH);
		lowerContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		leftLowerContainer = new Composite(lowerContainer, SWT.NONE);
		leftLowerContainer.setLayout(new BorderLayout(0, 0));
		
		upperLeftLowerLabel = new Label(leftLowerContainer, SWT.NONE);
		upperLeftLowerLabel.setLayoutData(BorderLayout.NORTH);
		
		lowerLeftLowerLabel = new Label(leftLowerContainer, SWT.NONE);
		lowerLeftLowerLabel.setLayoutData(BorderLayout.SOUTH);
		
		neuButton = new Button(leftLowerContainer, SWT.NONE);
		neuButton.setLayoutData(BorderLayout.CENTER);
		neuButton.setText("Neu...");
		neuButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new NeuerBenutzerWindow();
				((Button)e.getSource()).getShell().dispose();
			}
		});
		
		rightLeftLowerContainer = new Composite(leftLowerContainer, SWT.NONE);
		rightLeftLowerContainer.setLayoutData(BorderLayout.EAST);
		
		leftLeftLowerContainer = new Composite(leftLowerContainer, SWT.NONE);
		leftLeftLowerContainer.setLayoutData(BorderLayout.WEST);
		
		rightLowerContainer = new Composite(lowerContainer, SWT.NONE);
		rightLowerContainer.setLayout(new BorderLayout(0, 0));
		
		upperRightLowerLabel = new Label(rightLowerContainer, SWT.NONE);
		upperRightLowerLabel.setLayoutData(BorderLayout.NORTH);
		
		lowerRightLowerLabel = new Label(rightLowerContainer, SWT.NONE);
		lowerRightLowerLabel.setLayoutData(BorderLayout.SOUTH);
		
		leftRightLowerContainer = new Composite(rightLowerContainer, SWT.NONE);
		leftRightLowerContainer.setLayoutData(BorderLayout.WEST);
		
		rightRightLowerContainer = new Composite(rightLowerContainer, SWT.NONE);
		rightRightLowerContainer.setLayoutData(BorderLayout.EAST);
		
		loginButton = new Button(rightLowerContainer, SWT.NONE);
		loginButton.setLayoutData(BorderLayout.CENTER);
		loginButton.setText("Login");
		loginButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				login();
			}
		});
		
		leftContainer = new Composite(this, SWT.NONE);
		leftContainer.setLayoutData(BorderLayout.WEST);
		
		rightContainer = new Composite(this, SWT.NONE);
		rightContainer.setLayoutData(BorderLayout.EAST);
		
		middleContainer = new Composite(this, SWT.NONE);
		middleContainer.setLayoutData(BorderLayout.CENTER);
		middleContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		benutzernameLabel = new Label(middleContainer, SWT.NONE);
		benutzernameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		benutzernameLabel.setText("Benutzername:");
		
		benutzernameTextField = new Text(middleContainer, SWT.BORDER);
		benutzernameTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		passwortLabel = new Label(middleContainer, SWT.NONE);
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
		
		logoLabel = new Label(this, SWT.NONE);
		logoLabel.setFont(SWTResourceManager.getFont("Calibri", 18, SWT.NORMAL));
		logoLabel.setLayoutData(BorderLayout.NORTH);
		logoLabel.setText("HANDSIM-Marketplace");

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
					JOptionPane.showMessageDialog(null, "Bitte überprüfen Sie Ihre Eingaben! \nDer Benutzername und das angegebene Password stimmen nicht überein.");
				}
			} catch (Exception e1) {
				// TODO FIX ERRORHANDLING
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null,(e1.getMessage()));
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Bitte überprüfen Sie Ihre Eingaben! \nBeide Textfelder (Benutzername und Passwort) müssen dabei gefüllt werden.");
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
