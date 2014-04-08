package gui;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

//WER SOLL DENN DEN MIST IMPORTIEREN K??NNEN???

public class MainWindows extends Shell {
	private Text Suchfeld;
	private Text BenutzernameTextField;
	private Text VornameTextField;
	private Text NachnameTextField;
	private Text Stra???eTextField;
	private Text PostleitzahlTextField;
	private Text StadtTextField;
	private Text HausnummerTextField;
	private Text UnternehmensTextField;
	private Text EmailTextField;
	private Text TelefonTextField;
	private Table DashboardAuftr???geTable;
	private Table DashboardTermineTable;
	private Table UnternehmensTable;
	private Table MeineAuftr???geTable;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			CSPmainWindows shell = new CSPmainWindows(display);
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
	public CSPmainWindows(Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		
		Composite HeaderContainer = new Composite(this, SWT.NONE);
		HeaderContainer.setLayoutData(BorderLayout.NORTH);
		HeaderContainer.setLayout(new BorderLayout(0, 0));
		
		Composite RightHeader = new Composite(HeaderContainer, SWT.NONE);
		RightHeader.setLayoutData(BorderLayout.EAST);
		RightHeader.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button DashboardButton = new Button(RightHeader, SWT.NONE);
		DashboardButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		DashboardButton.setText("Dashboard");
		
		Button ProfilButton = new Button(RightHeader, SWT.NONE);
		ProfilButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		ProfilButton.setText("Profil");
		
		Button UnternehmensButton = new Button(RightHeader, SWT.NONE);
		UnternehmensButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		UnternehmensButton.setText("Unternehmen");
		
		Button AuftragsButton = new Button(RightHeader, SWT.NONE);
		AuftragsButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		AuftragsButton.setText("Auftr???ge");
		
		Composite LeftHeader = new Composite(HeaderContainer, SWT.NONE);
		LeftHeader.setLayoutData(BorderLayout.WEST);
		
		Label UnternehmensLogo = new Label(LeftHeader, SWT.NONE);
		UnternehmensLogo.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		UnternehmensLogo.setBounds(0, 0, 81, 25);
		UnternehmensLogo.setText("LOGO");
		
		Composite MidleHeader = new Composite(HeaderContainer, SWT.NONE);
		MidleHeader.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Suchfeld = new Text(MidleHeader, SWT.BORDER);
		Suchfeld.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		Suchfeld.setText("Suche");
		
		Composite LowContainer = new Composite(this, SWT.NONE);
		LowContainer.setLayoutData(BorderLayout.SOUTH);
		
		Composite LeftContainer = new Composite(this, SWT.NONE);
		LeftContainer.setLayoutData(BorderLayout.WEST);
		
		Composite RightContainer = new Composite(this, SWT.NONE);
		RightContainer.setLayoutData(BorderLayout.EAST);
		
		final Composite MainContainer = new Composite(this, SWT.NONE);
		MainContainer.setLayoutData(BorderLayout.CENTER);
		final StackLayout MainStack = new StackLayout();
		MainContainer.setLayout(MainStack);
		
		final Composite DashboardContainer = new Composite(MainContainer, SWT.NONE);
		MainStack.topControl = DashboardContainer;
		DashboardContainer.setLayout(new BorderLayout(0, 0));
		
		Composite DashboardMidleContainer = new Composite(DashboardContainer, SWT.NONE);
		DashboardMidleContainer.setLayoutData(BorderLayout.CENTER);
		DashboardMidleContainer.setLayout(new GridLayout(2, false));
		
		Label DashboardAuftr???geLabel = new Label(DashboardMidleContainer, SWT.NONE);
		DashboardAuftr???geLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		DashboardAuftr???geLabel.setText("Meine Auftr\u00E4ge");
		
		Label DashboardTermineLabel = new Label(DashboardMidleContainer, SWT.NONE);
		DashboardTermineLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		DashboardTermineLabel.setText("N\u00E4chste Termine");
		
		DashboardAuftr???geTable = new Table(DashboardMidleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		DashboardAuftr???geTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		DashboardAuftr???geTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		DashboardAuftr???geTable.setHeaderVisible(true);
		DashboardAuftr???geTable.setLinesVisible(true);
		
		DashboardTermineTable = new Table(DashboardMidleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		DashboardTermineTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		DashboardTermineTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		DashboardTermineTable.setHeaderVisible(true);
		DashboardTermineTable.setLinesVisible(true);
		
		Composite DashboardTopContainer = new Composite(DashboardContainer, SWT.NONE);
		DashboardTopContainer.setLayoutData(BorderLayout.NORTH);
		
		final Composite UnternehmenContainer = new Composite(MainContainer, SWT.NONE);
		UnternehmenContainer.setLayout(new BorderLayout(0, 0));
		
		Composite UnternehmenTopContainer = new Composite(UnternehmenContainer, SWT.NONE);
		UnternehmenTopContainer.setLayoutData(BorderLayout.NORTH);
		
		Composite UnternehmenMidleContainer = new Composite(UnternehmenContainer, SWT.NONE);
		UnternehmenMidleContainer.setLayoutData(BorderLayout.CENTER);
		UnternehmenMidleContainer.setLayout(new BorderLayout(0, 0));
		
		UnternehmensTable = new Table(UnternehmenMidleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		UnternehmensTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		UnternehmensTable.setLayoutData(BorderLayout.CENTER);
		UnternehmensTable.setHeaderVisible(true);
		UnternehmensTable.setLinesVisible(true);
		
		Composite UnternehmenMidleHeaderContainer = new Composite(UnternehmenMidleContainer, SWT.NONE);
		UnternehmenMidleHeaderContainer.setLayoutData(BorderLayout.NORTH);
		UnternehmenMidleHeaderContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label RegistrierteUnternehmenLabel = new Label(UnternehmenMidleHeaderContainer, SWT.NONE);
		RegistrierteUnternehmenLabel.setFont(SWTResourceManager.getFont("Calibri", 16, SWT.NORMAL));
		RegistrierteUnternehmenLabel.setText("Registrierte Unternehmen");
		
		Composite UnternehmenTableButtonContainer = new Composite(UnternehmenMidleHeaderContainer, SWT.NONE);
		
		final Composite Auftr???geContainer = new Composite(MainContainer, SWT.NONE);
		Auftr???geContainer.setLayout(new BorderLayout(0, 0));
		
		Composite Auftr???geMidleContainer = new Composite(Auftr???geContainer, SWT.NONE);
		Auftr???geMidleContainer.setLayoutData(BorderLayout.CENTER);
		Auftr???geMidleContainer.setLayout(new BorderLayout(0, 0));
		
		Composite Auftr???geMidleHeaderContainer = new Composite(Auftr???geMidleContainer, SWT.NONE);
		Auftr???geMidleHeaderContainer.setLayoutData(BorderLayout.NORTH);
		Auftr???geMidleHeaderContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label MeineAuftr???geLabel = new Label(Auftr???geMidleHeaderContainer, SWT.NONE);
		MeineAuftr???geLabel.setFont(SWTResourceManager.getFont("Calibri", 16, SWT.NORMAL));
		MeineAuftr???geLabel.setText("Meine Auftr\u00E4ge");
		
		Composite Auftr???geTableButtonContainer = new Composite(Auftr???geMidleHeaderContainer, SWT.NONE);
		
		MeineAuftr???geTable = new Table(Auftr???geMidleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		MeineAuftr???geTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		MeineAuftr???geTable.setLayoutData(BorderLayout.CENTER);
		MeineAuftr???geTable.setHeaderVisible(true);
		MeineAuftr???geTable.setLinesVisible(true);
		
		Composite Auftr???geTopContainer = new Composite(Auftr???geContainer, SWT.NONE);
		Auftr???geTopContainer.setLayoutData(BorderLayout.NORTH);
		
		final Composite ProfilContainer = new Composite(MainContainer, SWT.NONE);
		ProfilContainer.setLayout(new BorderLayout(0, 0));
		
		Composite ProfilMidleContainer = new Composite(ProfilContainer, SWT.NONE);
		ProfilMidleContainer.setLayoutData(BorderLayout.CENTER);
		ProfilMidleContainer.setLayout(new GridLayout(2, false));
		
		
		Label BenutzernameLabel = new Label(ProfilMidleContainer, SWT.NONE);
		BenutzernameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		BenutzernameLabel.setText("Benutzername");
		
		Label StadtLabel = new Label(ProfilMidleContainer, SWT.NONE);
		StadtLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		StadtLabel.setText("Stadt");
		
		BenutzernameTextField = new Text(ProfilMidleContainer, SWT.READ_ONLY);
		BenutzernameTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		BenutzernameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		StadtTextField = new Text(ProfilMidleContainer, SWT.BORDER);
		StadtTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		StadtTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label VornameLabel = new Label(ProfilMidleContainer, SWT.NONE);
		VornameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		VornameLabel.setText("Vorname");
		
		Label UnternehmensLabel = new Label(ProfilMidleContainer, SWT.NONE);
		UnternehmensLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		UnternehmensLabel.setText("Unternehmen");
		
		VornameTextField = new Text(ProfilMidleContainer, SWT.BORDER);
		VornameTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		VornameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		UnternehmensTextField = new Text(ProfilMidleContainer, SWT.BORDER);
		UnternehmensTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		UnternehmensTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label NachnameLabel = new Label(ProfilMidleContainer, SWT.NONE);
		NachnameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		NachnameLabel.
		setText("Nachname");
		
		Label EmailLabel = new Label(ProfilMidleContainer, SWT.NONE);
		EmailLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		EmailLabel.setText("E-Mail");
		
		NachnameTextField = new Text(ProfilMidleContainer, SWT.BORDER);
		NachnameTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		NachnameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		EmailTextField = new Text(ProfilMidleContainer, SWT.BORDER);
		EmailTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		EmailTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label Stra???eLabel = new Label(ProfilMidleContainer, SWT.NONE);
		Stra???eLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		Stra???eLabel.setText("Stra\u00DFe");
		
		Label TelefonLabel = new Label(ProfilMidleContainer, SWT.NONE);
		TelefonLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		TelefonLabel.setText("Telefon");
		
		Stra???eTextField = new Text(ProfilMidleContainer, SWT.BORDER);
		Stra???eTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		Stra???eTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		TelefonTextField = new Text(ProfilMidleContainer, SWT.BORDER);
		TelefonTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		TelefonTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		TelefonTextField.addVerifyListener(new VerifyListener() {
	        public void verifyText(VerifyEvent e) {
	            	if(Character.isAlphabetic(e.character)){
	            		e.doit = false;
	            	}else{
	            		e.doit = true;
	            	}
	        	}
	        });
		
		Label HausnummerLabel = new Label(ProfilMidleContainer, SWT.NONE);
		HausnummerLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		HausnummerLabel.setText("Hausnummer");
		
		Label GeschlechtLabel = new Label(ProfilMidleContainer, SWT.NONE);
		GeschlechtLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		GeschlechtLabel.setText("Geschlecht");
		
		HausnummerTextField = new Text(ProfilMidleContainer, SWT.BORDER);
		HausnummerTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		HausnummerTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Combo GeschlechtCombo = new Combo(ProfilMidleContainer, SWT.NONE);
		GeschlechtCombo.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		GeschlechtCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GeschlechtCombo.add("M???nnlich");
		GeschlechtCombo.add("Weiblich");
		
		Label PostleitzahlLabel = new Label(ProfilMidleContainer, SWT.NONE);
		PostleitzahlLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		PostleitzahlLabel.setText("Postleitzahl");
		new Label(ProfilMidleContainer, SWT.NONE);
		
		PostleitzahlTextField = new Text(ProfilMidleContainer, SWT.BORDER);
		PostleitzahlTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		PostleitzahlTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		PostleitzahlTextField.setTextLimit(5);
		new Label(ProfilMidleContainer, SWT.NONE);
		
		Composite ProfilTopContainer = new Composite(ProfilContainer, SWT.NONE);
		ProfilTopContainer.setLayoutData(BorderLayout.NORTH);
		createContents();
		
		DashboardButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MainStack.topControl = DashboardContainer;
				MainContainer.layout();
			}
		});
		
		ProfilButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MainStack.topControl = ProfilContainer;
				MainContainer.layout();
			}
		});
		
		UnternehmensButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MainStack.topControl = UnternehmenContainer;
				MainContainer.layout();
			}
		});
		
		AuftragsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MainStack.topControl = Auftr???geContainer;
				MainContainer.layout();
			}
		});
		
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(1280, 720);

	}

	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
