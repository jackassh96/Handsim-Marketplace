package gui;

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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class LoginWindow extends Shell {
	private Text passwortTextField;
	private Text benutzernameTextField;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			LoginWindow shell = new LoginWindow(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		
		Label logoLabel = new Label(this, SWT.NONE);
		logoLabel.setFont(SWTResourceManager.getFont("Calibri", 18, SWT.NORMAL));
		logoLabel.setLayoutData(BorderLayout.NORTH);
		logoLabel.setText("HANDSIM-Marketplace");
		
		neuButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new NeuerBenutzerWindow();
				((Button)e.getSource()).getShell().dispose();
			}
		});
		
		loginButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
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

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
