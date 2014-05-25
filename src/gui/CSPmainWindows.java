package gui;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;

import processing.Controller;


public class CSPmainWindows extends Shell {
	
	//Unterst�tzung
	private final Controller controller = Controller.getInstance();
	
	//Hauptfenster
	private StackLayout mainStack;
	private Composite mainContainer, lowContainer, leftContainer, rightContainer;
	
	//Header
	private Composite headerContainer, rightHeader, leftHeader, middleHeader;
	private Button dashboardButton, profilButton, unternehmensButton, auftragsButton;
	private Label unternehmensLogo, middleHeaderLabel;
	
	//Dashboard
	private Composite dashboardContainer, dashboardMiddleContainer, dashboardTopContainer;
	private Label dashboardAuftr�geLabel, dashboardTermineLabel;
	private Table dashboardAuftr�geTable, dashboardTermineTable;
	
	//UnternehmenslistenAnsicht
	private Composite unternehmenContainer, unternehmenTopContainer, unternehmenMiddleContainer,
	unternehmenMidleHeaderContainer, unternehmenTableButtonContainer;
	private Label registrierteUnternehmenLabel;
	private Table unternehmensTable;
	
	//ProfilAnsicht
	private Composite profilContainer, profilLowContainer, profilLeftLowContainer, profilRightLowContainer,
	profilRightRightLowContainer, profilMiddleContainer, profilTopContainer;
	private Label benutzernameLabel, stadtLabel, vornameLabel, nachnameLabel, emailLabel, stra�eLabel, telefonLabel, 
	hausnummerLabel, geschlechtLabel, postleitzahlLabel, unternehmensLabel;
	private Text benutzernameTextField,vornameTextField,nachnameTextField,stra�eTextField,postleitzahlTextField,
	stadtTextField,hausnummerTextField,unternehmensTextField,telefonTextField, emailTextField;
	private Button profilSpeichernButton;
	private Combo geschlechtCombo;	
	
	//AuftragslistenAnsicht
	private Composite auftr�geContainer, auftr�geMiddleContainer, auftr�geMidleHeaderContainer, auftr�geTableButtonContainer,
	auftr�geUpperTableButtonContainer, auftr�geLowerTableButtonContainer, auftr�geTopContainer;
	private Label meineAuftr�geLabel, auftr�geUpperTableButtonLabel;
	private Button auftr�geNeuButton, auftr�geBearbeitenButton, auftr�geL�schenButton;
	private Table meineAuftr�geTable;
	private Composite rightMiddleContainer;

	/**
	 * Create the shell.
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public CSPmainWindows() throws SQLException, IOException {
		super(Display.getDefault(), SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));

		this.createHeader();
		
		//Main
		lowContainer = new Composite(this, SWT.NONE);
		lowContainer.setLayoutData(BorderLayout.SOUTH);
		
		leftContainer = new Composite(this, SWT.NONE);
		leftContainer.setLayoutData(BorderLayout.WEST);
		
		rightContainer = new Composite(this, SWT.NONE);
		rightContainer.setLayoutData(BorderLayout.EAST);
		
		mainContainer = new Composite(this, SWT.NONE);
		mainContainer.setLayoutData(BorderLayout.CENTER);
		mainStack = new StackLayout();
		mainContainer.setLayout(mainStack);
		
		//Fill StackLayout
		this.createDashboard();
		this.createUnternehmenslistenAnsicht();
		this.createAuftragslistenAnsicht();
		this.createProfilAnsicht();
		
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("HANDSIM-Marketplace");
		setSize(1280, 720);
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
	}
	
	/**
	 * Creates all the components of the Header of the CSPmainWindow
	 */
	private void createHeader(){
		headerContainer = new Composite(this, SWT.BORDER);
		headerContainer.setLayoutData(BorderLayout.NORTH);
		headerContainer.setLayout(new BorderLayout(0, 0));
		
		rightHeader = new Composite(headerContainer, SWT.NONE);
		rightHeader.setLayoutData(BorderLayout.EAST);
		rightHeader.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		dashboardButton = new Button(rightHeader, SWT.NONE);
		dashboardButton.setToolTipText("Wechseln zur Dashboardansicht mit den Schnellzugriffen");
		dashboardButton.setFont(SWTResourceManager.getFont("Calibri", 11, SWT.BOLD));
		dashboardButton.setText("Dashboard");
		dashboardButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				mainStack.topControl = dashboardContainer;
				dashboardAuftr�geTable.removeAll();
				controller.generateMyAssignmentTableItemsDashboard(dashboardAuftr�geTable);
				mainContainer.layout();
			}
		});
		
		profilButton = new Button(rightHeader, SWT.NONE);
		profilButton.setToolTipText("Wechseln zur Ansicht mit den eigenen Profilinformationen zur Bearbeitung");
		profilButton.setFont(SWTResourceManager.getFont("Calibri", 11, SWT.BOLD));
		profilButton.setText("Profil");
		profilButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				mainStack.topControl = profilContainer;
				HashMap<String, String> profilInfo = controller.genereateMyProfileHashMap();
				benutzernameTextField.setText(profilInfo.get("id"));
				vornameTextField.setText(profilInfo.get("firstname"));
				nachnameTextField.setText(profilInfo.get("lastname"));
				stra�eTextField.setText(profilInfo.get("street"));
				hausnummerTextField.setText(profilInfo.get("number"));
				postleitzahlTextField.setText(profilInfo.get("postcode"));
				stadtTextField.setText(profilInfo.get("city"));
				unternehmensTextField.setText(profilInfo.get("company"));
				telefonTextField.setText(profilInfo.get("phone"));
				emailTextField.setText(profilInfo.get("email"));
				geschlechtCombo.setText(profilInfo.get("gender"));
				mainContainer.layout();				
			}
		});
		
		unternehmensButton = new Button(rightHeader, SWT.NONE);
		unternehmensButton.setToolTipText("Wechseln zur Liste mit den in HANDSIM eingetragenen Unternehmen");
		unternehmensButton.setFont(SWTResourceManager.getFont("Calibri", 11, SWT.BOLD));
		unternehmensButton.setText("Unternehmen");
		unternehmensButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				mainStack.topControl = unternehmenContainer;
				unternehmensTable.removeAll();
				controller.generateCompanyTableItems(unternehmensTable);
				mainContainer.layout();
			}
		});
		
		auftragsButton = new Button(rightHeader, SWT.NONE);
		auftragsButton.setToolTipText("Wechseln zur Liste mit den eigenen Auftr\u00E4gen");
		auftragsButton.setFont(SWTResourceManager.getFont("Calibri", 11, SWT.BOLD));
		auftragsButton.setText("Auftr�ge");
		auftragsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				mainStack.topControl = auftr�geContainer;
				meineAuftr�geTable.removeAll();
				controller.generateMyAssignmentTableItems(meineAuftr�geTable);
				mainContainer.layout();
			}
		});
		
		leftHeader = new Composite(headerContainer, SWT.NONE);
		leftHeader.setLayoutData(BorderLayout.WEST);
		
		unternehmensLogo = new Label(leftHeader, SWT.NONE);
		unternehmensLogo.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		unternehmensLogo.setSize(250, 25);
		unternehmensLogo.setText("HANDSIM-Marketplace");
		
		middleHeader = new Composite(headerContainer, SWT.NONE);
		middleHeader.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		middleHeaderLabel = new Label(middleHeader, SWT.NONE);
		
		rightMiddleContainer = new Composite(middleHeader, SWT.NONE);
	}
	
	/**
	 * Creates all the contend of the DashboardView and adds it to the StackLayout
	 * @throws SQLException
	 * @throws IOException
	 */
	private void createDashboard() throws SQLException, IOException{
		dashboardContainer = new Composite(mainContainer, SWT.NONE);
		mainStack.topControl = dashboardContainer;
		dashboardContainer.setLayout(new BorderLayout(0, 0));
		
		//MiddleContainer containing the two tables of the Dashboard
		dashboardMiddleContainer = new Composite(dashboardContainer, SWT.NONE);
		dashboardMiddleContainer.setLayoutData(BorderLayout.CENTER);
		dashboardMiddleContainer.setLayout(new GridLayout(2, false));
		
		dashboardAuftr�geLabel = new Label(dashboardMiddleContainer, SWT.NONE);
		dashboardAuftr�geLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		dashboardAuftr�geLabel.setText("Meine Auftr�ge");
		
		dashboardTermineLabel = new Label(dashboardMiddleContainer, SWT.NONE);
		dashboardTermineLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		dashboardTermineLabel.setText("N�chste Termine");
		
		dashboardAuftr�geTable = new Table(dashboardMiddleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		dashboardAuftr�geTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		dashboardAuftr�geTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		dashboardAuftr�geTable.setHeaderVisible(true);
		dashboardAuftr�geTable.setLinesVisible(true);
		dashboardAuftr�geTable.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Table table = (Table) e.getSource();
				for(TableItem item : table.getSelection()){
					try {
						String auftragsID = (String) item.getData("id");
						new AuftragsansichtWindow(((Table)e.getSource()).getShell(), auftragsID);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
					}
				}
			}
			@Override
			public void mouseDown(MouseEvent e) {}
			@Override
			public void mouseUp(MouseEvent e) {}
		});
		controller.generateTableHeaderMyAssignments(dashboardAuftr�geTable);
		controller.generateMyAssignmentTableItemsDashboard(dashboardAuftr�geTable);
		
		dashboardTermineTable = new Table(dashboardMiddleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		dashboardTermineTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		dashboardTermineTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		dashboardTermineTable.setHeaderVisible(true);
		dashboardTermineTable.setLinesVisible(true);
		
		controller.generateTableHeaderNextOfferTable(dashboardTermineTable);
		controller.generateNextOfferTableItems(dashboardTermineTable);

		//Composite for upper distance control
		dashboardTopContainer = new Composite(dashboardContainer, SWT.NONE);
		dashboardTopContainer.setLayoutData(BorderLayout.NORTH);
		
	}
	
	/**
	 * Creates all the contend of the CompanyListView and adds it to the StackLayout
	 */
	private void createUnternehmenslistenAnsicht(){
		unternehmenContainer = new Composite(mainContainer, SWT.NONE);
		unternehmenContainer.setLayout(new BorderLayout(0, 0));
		
		unternehmenTopContainer = new Composite(unternehmenContainer, SWT.NONE);
		unternehmenTopContainer.setLayoutData(BorderLayout.NORTH);
		
		unternehmenMiddleContainer = new Composite(unternehmenContainer, SWT.NONE);
		unternehmenMiddleContainer.setLayoutData(BorderLayout.CENTER);
		unternehmenMiddleContainer.setLayout(new BorderLayout(0, 0));
		
		unternehmensTable = new Table(unternehmenMiddleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		unternehmensTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		unternehmensTable.setLayoutData(BorderLayout.CENTER);
		unternehmensTable.setHeaderVisible(true);
		unternehmensTable.setLinesVisible(true);
		unternehmensTable.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Table table = (Table) e.getSource();
				for(TableItem item :table.getSelection()){
					String unternehmensID = (String) item.getData("id");
					new UnternehmensansichtWindow(unternehmensID);
				}
			}
			@Override
			public void mouseDown(MouseEvent e) {}
			@Override
			public void mouseUp(MouseEvent e) {}
			
		});
		
		controller.generateTableHeaderCompanyTable(unternehmensTable);
		
		unternehmenMidleHeaderContainer = new Composite(unternehmenMiddleContainer, SWT.NONE);
		unternehmenMidleHeaderContainer.setLayoutData(BorderLayout.NORTH);
		unternehmenMidleHeaderContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		registrierteUnternehmenLabel = new Label(unternehmenMidleHeaderContainer, SWT.NONE);
		registrierteUnternehmenLabel.setFont(SWTResourceManager.getFont("Calibri", 16, SWT.NORMAL));
		registrierteUnternehmenLabel.setText("Registrierte Unternehmen");
		
		unternehmenTableButtonContainer = new Composite(unternehmenMidleHeaderContainer, SWT.NONE);
	}
	
	/**
	 * Creates all the contend of the AssignmentListView and adds it to the StackLayout
	 */
	private void createAuftragslistenAnsicht(){
		auftr�geContainer = new Composite(mainContainer, SWT.NONE);
		auftr�geContainer.setLayout(new BorderLayout(0, 0));
		
		auftr�geMiddleContainer = new Composite(auftr�geContainer, SWT.NONE);
		auftr�geMiddleContainer.setLayoutData(BorderLayout.CENTER);
		auftr�geMiddleContainer.setLayout(new BorderLayout(0, 0));
		
		auftr�geMidleHeaderContainer = new Composite(auftr�geMiddleContainer, SWT.NONE);
		auftr�geMidleHeaderContainer.setLayoutData(BorderLayout.NORTH);
		auftr�geMidleHeaderContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		meineAuftr�geLabel = new Label(auftr�geMidleHeaderContainer, SWT.NONE);
		meineAuftr�geLabel.setFont(SWTResourceManager.getFont("Calibri", 16, SWT.NORMAL));
		meineAuftr�geLabel.setText("Meine Auftr�ge");
		
		auftr�geTableButtonContainer = new Composite(auftr�geMidleHeaderContainer, SWT.NONE);
		auftr�geTableButtonContainer.setLayout(new BorderLayout(0, 0));
		
		auftr�geUpperTableButtonContainer = new Composite(auftr�geTableButtonContainer, SWT.NONE);
		auftr�geUpperTableButtonContainer.setLayoutData(BorderLayout.NORTH);
		auftr�geUpperTableButtonContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		auftr�geUpperTableButtonLabel = new Label(auftr�geUpperTableButtonContainer, SWT.NONE);
		
		auftr�geLowerTableButtonContainer = new Composite(auftr�geTableButtonContainer, SWT.NONE);
		auftr�geLowerTableButtonContainer.setLayoutData(BorderLayout.SOUTH);
		auftr�geLowerTableButtonContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		auftr�geNeuButton = new Button(auftr�geLowerTableButtonContainer, SWT.NONE);
		auftr�geNeuButton.setToolTipText("Neuen Auftrag anlegen");
		auftr�geNeuButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					((Button)e.getSource()).getShell();
					new AuftragErstellenPositionenWindow(((Button)e.getSource()).getShell(), null, null);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getStackTrace(), "Fehler!", 2);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, e1.getStackTrace(), "Fehler!", 2);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getStackTrace(), "Fehler!", 2);
				}
			}
		});
		auftr�geNeuButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		auftr�geNeuButton.setText("Neu");
		
		auftr�geBearbeitenButton = new Button(auftr�geLowerTableButtonContainer, SWT.NONE);
		auftr�geBearbeitenButton.setToolTipText("Bestehenden ausgew\u00E4hlten Auftrag bearbeiten");
		auftr�geBearbeitenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		auftr�geBearbeitenButton.setText("Bearbeiten");
		auftr�geBearbeitenButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				for(TableItem item : meineAuftr�geTable.getSelection()){
					if(item.getText(4).equals("open")){
						try {
							new AuftragErstellenPositionenWindow(((Button)e.getSource()).getShell(), null,(String) item.getData("id"));
							CSPmainWindows main = (CSPmainWindows) ((Button)e.getSource()).getShell();
							main.updateContent();
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1.getStackTrace(), "Fehler!", 2);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, e1.getStackTrace(), "Fehler!", 2);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getStackTrace(), "Fehler!", 2);
						}
					}
				}
			}
		});
		
		auftr�geL�schenButton = new Button(auftr�geLowerTableButtonContainer, SWT.NONE);
		auftr�geL�schenButton.setToolTipText("Ausgew\u00E4hlten Auftrag l\u00F6schen");
		auftr�geL�schenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for(TableItem item : meineAuftr�geTable.getSelection()){
					try {
						if(item.getText(4).equals("open")){
							controller.deleteAssignment((String) item.getData("id"));
							CSPmainWindows main = (CSPmainWindows) ((Button)e.getSource()).getShell();
							main.updateContent();
						}
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
					}
				}
			}
		});
		auftr�geL�schenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		auftr�geL�schenButton.setText("L�schen");
		
		meineAuftr�geTable = new Table(auftr�geMiddleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		meineAuftr�geTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		meineAuftr�geTable.setLayoutData(BorderLayout.CENTER);
		meineAuftr�geTable.setHeaderVisible(true);
		meineAuftr�geTable.setLinesVisible(true);
		meineAuftr�geTable.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Table table = (Table) e.getSource();
				for(TableItem item : table.getSelection()){
					try {
						String auftragsID = (String) item.getData("id");
						new AuftragsansichtWindow(((Table)e.getSource()).getShell(), auftragsID);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
					}
				}
			}
			@Override
			public void mouseDown(MouseEvent e) {}
			@Override
			public void mouseUp(MouseEvent e) {}
		});
		controller.generateTableHeaderAssignmentTable(meineAuftr�geTable);
		
		auftr�geTopContainer = new Composite(auftr�geContainer, SWT.NONE);
		auftr�geTopContainer.setLayoutData(BorderLayout.NORTH);
	}
	
	/**
	 * Creates all the contend of the Profileview and adds it to the StackLayout
	 */
	private void createProfilAnsicht(){
		profilContainer = new Composite(mainContainer, SWT.NONE);
		profilContainer.setLayout(new BorderLayout(0, 0));
		
		profilLowContainer = new Composite(profilContainer, SWT.NONE);
		profilLowContainer.setLayoutData(BorderLayout.SOUTH);
		profilLowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		profilLeftLowContainer = new Composite(profilLowContainer, SWT.NONE);
		
		profilRightLowContainer = new Composite(profilLowContainer, SWT.NONE);
		profilRightLowContainer.setLayout(new BorderLayout(0, 0));
		
		profilRightRightLowContainer = new Composite(profilRightLowContainer, SWT.NONE);
		profilRightRightLowContainer.setLayoutData(BorderLayout.EAST);
		
		profilSpeichernButton = new Button(profilRightRightLowContainer, SWT.NONE);
		profilSpeichernButton.setToolTipText("Neue Profildaten speichern und alte \u00FCberschreiben");
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
				if(stra�eTextField.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Es wurde keine Stra�e eingetragen!", "Fehler!", 2);
					return;
				}
				if(hausnummerTextField.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Es wurde keine Hausnummer eingetragen!", "Fehler!", 2);
					return;
				}
				if(!(emailTextField.getText().contains("@") && emailTextField.getText().contains("."))){
					JOptionPane.showMessageDialog(null, "Keine g�ltige E-Mail-Addresse eingetragen!", "Fehler!", 2);
					return;
				}
				Controller controller = Controller.getInstance();
				try {
					controller.updateUser(benutzernameTextField.getText(), vornameTextField.getText(), nachnameTextField.getText(), stra�eTextField.getText(), hausnummerTextField.getText(), postleitzahlTextField.getText(), stadtTextField.getText(), emailTextField.getText(), telefonTextField.getText(), unternehmensTextField.getText(), geschlechtCombo.getText());
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
				}
				
			}
		});
		
		profilMiddleContainer = new Composite(profilContainer, SWT.NONE);
		profilMiddleContainer.setLayoutData(BorderLayout.CENTER);
		profilMiddleContainer.setLayout(new GridLayout(2, false));
		
		benutzernameLabel = new Label(profilMiddleContainer, SWT.NONE);
		benutzernameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		benutzernameLabel.setText("Benutzername");
		
		stadtLabel = new Label(profilMiddleContainer, SWT.NONE);
		stadtLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		stadtLabel.setText("Stadt");
		
		benutzernameTextField = new Text(profilMiddleContainer, SWT.READ_ONLY);
		benutzernameTextField.setToolTipText("Der Benutzername muss einmalig sein");
		benutzernameTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		benutzernameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		benutzernameTextField.setEditable(false);
		
		stadtTextField = new Text(profilMiddleContainer, SWT.BORDER);
		stadtTextField.setToolTipText("Hier die Stadt in der Sie leben eintragen");
		stadtTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		stadtTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		stadtTextField.addVerifyListener(getDigitListener());
		
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
		vornameTextField.addVerifyListener(getDigitListener());
		
		unternehmensTextField = new Text(profilMiddleContainer, SWT.BORDER);
		unternehmensTextField.setToolTipText("Hier Ihr Unternehmen eintragen");
		unternehmensTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		unternehmensTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		nachnameLabel = new Label(profilMiddleContainer, SWT.NONE);
		nachnameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		nachnameLabel.setText("Nachname");
		
		emailLabel = new Label(profilMiddleContainer, SWT.NONE);
		emailLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		emailLabel.setText("E-Mail");
		
		nachnameTextField = new Text(profilMiddleContainer, SWT.BORDER);
		nachnameTextField.setToolTipText("Hier Ihren Nachnamen eintragen");
		nachnameTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		nachnameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		nachnameTextField.addVerifyListener(getDigitListener());
		
		emailTextField = new Text(profilMiddleContainer, SWT.BORDER);
		emailTextField.setToolTipText("Hier Ihre E-Mail-Adresse eintragen");
		emailTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		emailTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		stra�eLabel = new Label(profilMiddleContainer, SWT.NONE);
		stra�eLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		stra�eLabel.setText("Stra�e");
		
		telefonLabel = new Label(profilMiddleContainer, SWT.NONE);
		telefonLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		telefonLabel.setText("Telefon");
		
		stra�eTextField = new Text(profilMiddleContainer, SWT.BORDER);
		stra�eTextField.setToolTipText("Hier Ihre Stra\u00DFe eintragen");
		stra�eTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		stra�eTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		stra�eTextField.addVerifyListener(getDigitListener());
		
		telefonTextField = new Text(profilMiddleContainer, SWT.BORDER);
		telefonTextField.setToolTipText("Hier Ihre Telefonnummer eintragen");
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
		geschlechtCombo.setToolTipText("Hier Ihr Geschlecht ausw\u00E4hlen");
		geschlechtCombo.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		geschlechtCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		geschlechtCombo.add("M�nnlich");
		geschlechtCombo.add("Weiblich");
		
		postleitzahlLabel = new Label(profilMiddleContainer, SWT.NONE);
		postleitzahlLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		postleitzahlLabel.setText("Postleitzahl");
		//Empty label to fill the GridLayout
		new Label(profilMiddleContainer, SWT.NONE);
		
		postleitzahlTextField = new Text(profilMiddleContainer, SWT.BORDER);
		postleitzahlTextField.setToolTipText("Hier Ihre Postleitzahl eintragen");
		postleitzahlTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		postleitzahlTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		postleitzahlTextField.setTextLimit(5);
		//Empty label to fill GridLayout
		new Label(profilMiddleContainer, SWT.NONE);
		
		profilTopContainer = new Composite(profilContainer, SWT.NONE);
		profilTopContainer.setLayoutData(BorderLayout.NORTH);
	}

	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * A Method that triggers the CSPmainWindow to update its tables
	 */
	public void updateContent(){
		try {
			controller.init(null, null);
			mainStack.topControl = auftr�geContainer;
			meineAuftr�geTable.removeAll();
			controller.generateMyAssignmentTableItems(meineAuftr�geTable);
			mainContainer.layout();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
		}
	}
	
	/**
	 * Creates a VerifyListener that doesnt allows digits to be typed in
	 * @return the VerifyListener that filters digits out
	 */
	public VerifyListener getDigitListener(){
		return new VerifyListener() {
	        public void verifyText(VerifyEvent e) {
            	if(Character.isAlphabetic(e.character)){
            		e.doit = false;
            	}else{
            		e.doit = true;
            	}
        	}
        };
	}
}
