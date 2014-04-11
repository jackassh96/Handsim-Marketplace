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
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.wb.swt.SWTResourceManager;


public class CSPmainWindows extends Shell {
	private Text Suchfeld;
	private Text BenutzernameTextField;
	private Text VornameTextField;
	private Text NachnameTextField;
	private Text StraßeTextField;
	private Text PostleitzahlTextField;
	private Text StadtTextField;
	private Text HausnummerTextField;
	private Text UnternehmensTextField;
	private Text EmailTextField;
	private Text TelefonTextField;
	private Table DashboardAufträgeTable;
	private Table DashboardTermineTable;
	private Table UnternehmensTable;
	private Table MeineAufträgeTable;

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
		
		
		//Obere statische Leiste zur Navigation zwischen den einzelnen Seiten
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
		AuftragsButton.setText("Aufträge");
		
		Composite LeftHeader = new Composite(HeaderContainer, SWT.NONE);
		LeftHeader.setLayoutData(BorderLayout.WEST);
		
		Label UnternehmensLogo = new Label(LeftHeader, SWT.NONE);
		UnternehmensLogo.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		UnternehmensLogo.setBounds(0, 0, 81, 25);
		UnternehmensLogo.setText("LOGO");
		
		Composite MiddleHeader = new Composite(HeaderContainer, SWT.NONE);
		MiddleHeader.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Suchfeld = new Text(MiddleHeader, SWT.BORDER);
		Suchfeld.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		Suchfeld.setText("Suche");
		
		//Composites zur Abstandshaltung in alle Richtungen
		Composite LowContainer = new Composite(this, SWT.NONE);
		LowContainer.setLayoutData(BorderLayout.SOUTH);
		
		Composite LeftContainer = new Composite(this, SWT.NONE);
		LeftContainer.setLayoutData(BorderLayout.WEST);
		
		Composite RightContainer = new Composite(this, SWT.NONE);
		RightContainer.setLayoutData(BorderLayout.EAST);
		
		//MainContainer beinhaltet die 4 Ansichten des Hauptfensters (alle großen Container final um sie im ActionHandler ansprechen zu können)
		final Composite MainContainer = new Composite(this, SWT.NONE);
		MainContainer.setLayoutData(BorderLayout.CENTER);
		final StackLayout MainStack = new StackLayout();
		MainContainer.setLayout(MainStack);
		
		//DashboardContainer mit den zwei Tabellen für einen schnellen Überblick - wird als erstes angewählt
		final Composite DashboardContainer = new Composite(MainContainer, SWT.NONE);
		MainStack.topControl = DashboardContainer;
		DashboardContainer.setLayout(new BorderLayout(0, 0));
		
		//MiddleContainer beinhaltet die zwei Tabellen der Dashboardansicht
		Composite DashboardMiddleContainer = new Composite(DashboardContainer, SWT.NONE);
		DashboardMiddleContainer.setLayoutData(BorderLayout.CENTER);
		DashboardMiddleContainer.setLayout(new GridLayout(2, false));
		
		Label DashboardAufträgeLabel = new Label(DashboardMiddleContainer, SWT.NONE);
		DashboardAufträgeLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		DashboardAufträgeLabel.setText("Meine Aufträge");
		
		Label DashboardTermineLabel = new Label(DashboardMiddleContainer, SWT.NONE);
		DashboardTermineLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		DashboardTermineLabel.setText("Nächste Termine");
		
		DashboardAufträgeTable = new Table(DashboardMiddleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		DashboardAufträgeTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		DashboardAufträgeTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		DashboardAufträgeTable.setHeaderVisible(true);
		DashboardAufträgeTable.setLinesVisible(true);
		
		DashboardTermineTable = new Table(DashboardMiddleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		DashboardTermineTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		DashboardTermineTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		DashboardTermineTable.setHeaderVisible(true);
		DashboardTermineTable.setLinesVisible(true);
		
		//Composite als Abstandshalter nach oben hin
		Composite DashboardTopContainer = new Composite(DashboardContainer, SWT.NONE);
		DashboardTopContainer.setLayoutData(BorderLayout.NORTH);
		
		//Container mit der Unternehmensansicht
		final Composite UnternehmenContainer = new Composite(MainContainer, SWT.NONE);
		UnternehmenContainer.setLayout(new BorderLayout(0, 0));
		
		Composite UnternehmenTopContainer = new Composite(UnternehmenContainer, SWT.NONE);
		UnternehmenTopContainer.setLayoutData(BorderLayout.NORTH);
		
		Composite UnternehmenMiddleContainer = new Composite(UnternehmenContainer, SWT.NONE);
		UnternehmenMiddleContainer.setLayoutData(BorderLayout.CENTER);
		UnternehmenMiddleContainer.setLayout(new BorderLayout(0, 0));
		
		UnternehmensTable = new Table(UnternehmenMiddleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		UnternehmensTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		UnternehmensTable.setLayoutData(BorderLayout.CENTER);
		UnternehmensTable.setHeaderVisible(true);
		UnternehmensTable.setLinesVisible(true);
		
		Composite UnternehmenMidleHeaderContainer = new Composite(UnternehmenMiddleContainer, SWT.NONE);
		UnternehmenMidleHeaderContainer.setLayoutData(BorderLayout.NORTH);
		UnternehmenMidleHeaderContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label RegistrierteUnternehmenLabel = new Label(UnternehmenMidleHeaderContainer, SWT.NONE);
		RegistrierteUnternehmenLabel.setFont(SWTResourceManager.getFont("Calibri", 16, SWT.NORMAL));
		RegistrierteUnternehmenLabel.setText("Registrierte Unternehmen");
		
		Composite UnternehmenTableButtonContainer = new Composite(UnternehmenMidleHeaderContainer, SWT.NONE);
		
		//Container mit der Aufträgeansicht
		final Composite AufträgeContainer = new Composite(MainContainer, SWT.NONE);
		AufträgeContainer.setLayout(new BorderLayout(0, 0));
		
		Composite AufträgeMiddleContainer = new Composite(AufträgeContainer, SWT.NONE);
		AufträgeMiddleContainer.setLayoutData(BorderLayout.CENTER);
		AufträgeMiddleContainer.setLayout(new BorderLayout(0, 0));
		
		Composite AufträgeMidleHeaderContainer = new Composite(AufträgeMiddleContainer, SWT.NONE);
		AufträgeMidleHeaderContainer.setLayoutData(BorderLayout.NORTH);
		AufträgeMidleHeaderContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label MeineAufträgeLabel = new Label(AufträgeMidleHeaderContainer, SWT.NONE);
		MeineAufträgeLabel.setFont(SWTResourceManager.getFont("Calibri", 16, SWT.NORMAL));
		MeineAufträgeLabel.setText("Meine Aufträge");
		
		Composite AufträgeTableButtonContainer = new Composite(AufträgeMidleHeaderContainer, SWT.NONE);
		AufträgeTableButtonContainer.setLayout(new BorderLayout(0, 0));
		
		Composite AufträgeUpperTableButtonContainer = new Composite(AufträgeTableButtonContainer, SWT.NONE);
		AufträgeUpperTableButtonContainer.setLayoutData(BorderLayout.NORTH);
		AufträgeUpperTableButtonContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label AufträgeUpperTableButtonLabel = new Label(AufträgeUpperTableButtonContainer, SWT.NONE);
		
		Composite AufträgeLowerTableButtonContainer = new Composite(AufträgeTableButtonContainer, SWT.NONE);
		AufträgeLowerTableButtonContainer.setLayoutData(BorderLayout.SOUTH);
		AufträgeLowerTableButtonContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button AufträgeNeuButton = new Button(AufträgeLowerTableButtonContainer, SWT.NONE);
		AufträgeNeuButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new AuftragErstellenPositionenWindow();
			}
		});
		AufträgeNeuButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		AufträgeNeuButton.setText("Neu");
		
		Button AufträgeBearbeitenButton = new Button(AufträgeLowerTableButtonContainer, SWT.NONE);
		AufträgeBearbeitenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		AufträgeBearbeitenButton.setText("Bearbeiten");
		
		Button AufträgeLöschenButton = new Button(AufträgeLowerTableButtonContainer, SWT.NONE);
		AufträgeLöschenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		AufträgeLöschenButton.setText("Löschen");
		
		MeineAufträgeTable = new Table(AufträgeMiddleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		MeineAufträgeTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		MeineAufträgeTable.setLayoutData(BorderLayout.CENTER);
		MeineAufträgeTable.setHeaderVisible(true);
		MeineAufträgeTable.setLinesVisible(true);
		
		Composite AufträgeTopContainer = new Composite(AufträgeContainer, SWT.NONE);
		AufträgeTopContainer.setLayoutData(BorderLayout.NORTH);
		
		//Container mit der Profilansicht
		final Composite ProfilContainer = new Composite(MainContainer, SWT.NONE);
		ProfilContainer.setLayout(new BorderLayout(0, 0));
		
		Composite ProfilLowContainer = new Composite(ProfilContainer, SWT.NONE);
		ProfilLowContainer.setLayoutData(BorderLayout.SOUTH);
		ProfilLowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite ProfilLeftLowContainer = new Composite(ProfilLowContainer, SWT.NONE);
		
		Composite ProfilRightLowContainer = new Composite(ProfilLowContainer, SWT.NONE);
		ProfilRightLowContainer.setLayout(new BorderLayout(0, 0));
		
		Composite ProfilRightRightLowContainer = new Composite(ProfilRightLowContainer, SWT.NONE);
		ProfilRightRightLowContainer.setLayoutData(BorderLayout.EAST);
		
		Button ProfilSpeichernButton = new Button(ProfilRightRightLowContainer, SWT.NONE);
		ProfilSpeichernButton.setBounds(0, 0, 105, 35);
		ProfilSpeichernButton.setText("Speichern");
		
		Composite ProfilMiddleContainer = new Composite(ProfilContainer, SWT.NONE);
		ProfilMiddleContainer.setLayoutData(BorderLayout.CENTER);
		ProfilMiddleContainer.setLayout(new GridLayout(2, false));
		
		Label BenutzernameLabel = new Label(ProfilMiddleContainer, SWT.NONE);
		BenutzernameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		BenutzernameLabel.setText("Benutzername");
		
		Label StadtLabel = new Label(ProfilMiddleContainer, SWT.NONE);
		StadtLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		StadtLabel.setText("Stadt");
		
		BenutzernameTextField = new Text(ProfilMiddleContainer, SWT.READ_ONLY);
		BenutzernameTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		BenutzernameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		StadtTextField = new Text(ProfilMiddleContainer, SWT.BORDER);
		StadtTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		StadtTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label VornameLabel = new Label(ProfilMiddleContainer, SWT.NONE);
		VornameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		VornameLabel.setText("Vorname");
		
		Label UnternehmensLabel = new Label(ProfilMiddleContainer, SWT.NONE);
		UnternehmensLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		UnternehmensLabel.setText("Unternehmen");
		
		VornameTextField = new Text(ProfilMiddleContainer, SWT.BORDER);
		VornameTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		VornameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		UnternehmensTextField = new Text(ProfilMiddleContainer, SWT.BORDER);
		UnternehmensTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		UnternehmensTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label NachnameLabel = new Label(ProfilMiddleContainer, SWT.NONE);
		NachnameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		NachnameLabel.
		setText("Nachname");
		
		Label EmailLabel = new Label(ProfilMiddleContainer, SWT.NONE);
		EmailLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		EmailLabel.setText("E-Mail");
		
		NachnameTextField = new Text(ProfilMiddleContainer, SWT.BORDER);
		NachnameTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		NachnameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		EmailTextField = new Text(ProfilMiddleContainer, SWT.BORDER);
		EmailTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		EmailTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label StraßeLabel = new Label(ProfilMiddleContainer, SWT.NONE);
		StraßeLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		StraßeLabel.setText("Stra\u00DFe");
		
		Label TelefonLabel = new Label(ProfilMiddleContainer, SWT.NONE);
		TelefonLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		TelefonLabel.setText("Telefon");
		
		StraßeTextField = new Text(ProfilMiddleContainer, SWT.BORDER);
		StraßeTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		StraßeTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		TelefonTextField = new Text(ProfilMiddleContainer, SWT.BORDER);
		TelefonTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		TelefonTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		//VerifyListener um Buchstaben bei der Eingabe zu verbieten
		TelefonTextField.addVerifyListener(new VerifyListener() {
	        public void verifyText(VerifyEvent e) {
	            	if(Character.isAlphabetic(e.character)){
	            		e.doit = false;
	            	}else{
	            		e.doit = true;
	            	}
	        	}
	        });
		
		Label HausnummerLabel = new Label(ProfilMiddleContainer, SWT.NONE);
		HausnummerLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		HausnummerLabel.setText("Hausnummer");
		
		Label GeschlechtLabel = new Label(ProfilMiddleContainer, SWT.NONE);
		GeschlechtLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		GeschlechtLabel.setText("Geschlecht");
		
		HausnummerTextField = new Text(ProfilMiddleContainer, SWT.BORDER);
		HausnummerTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		HausnummerTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Combo GeschlechtCombo = new Combo(ProfilMiddleContainer, SWT.NONE);
		GeschlechtCombo.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		GeschlechtCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GeschlechtCombo.add("Männlich");
		GeschlechtCombo.add("Weiblich");
		
		Label PostleitzahlLabel = new Label(ProfilMiddleContainer, SWT.NONE);
		PostleitzahlLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		PostleitzahlLabel.setText("Postleitzahl");
		//Leeres Label um das GridLayout zu füllen
		new Label(ProfilMiddleContainer, SWT.NONE);
		
		PostleitzahlTextField = new Text(ProfilMiddleContainer, SWT.BORDER);
		PostleitzahlTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		PostleitzahlTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		PostleitzahlTextField.setTextLimit(5);
		//Leeres Label um das GridLayout zu füllen
		new Label(ProfilMiddleContainer, SWT.NONE);
		
		Composite ProfilTopContainer = new Composite(ProfilContainer, SWT.NONE);
		ProfilTopContainer.setLayoutData(BorderLayout.NORTH);
		createContents();
		
		//SelectionListener der Buttons im Header um zwischen den Sichten zu wechseln
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
				MainStack.topControl = AufträgeContainer;
				MainContainer.layout();
			}
		});
		
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("HANDSIM-Marketplace");
		setSize(1280, 720);

	}

	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
