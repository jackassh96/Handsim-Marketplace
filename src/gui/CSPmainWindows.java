package gui;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

import processing.Controller;


public class CSPmainWindows extends Shell {
	private Text suchfeld;
	private Text benutzernameTextField;
	private Text vornameTextField;
	private Text nachnameTextField;
	private Text straßeTextField;
	private Text postleitzahlTextField;
	private Text stadtTextField;
	private Text hausnummerTextField;
	private Text unternehmensTextField;
	private Text emailTextField;
	private Text telefonTextField;
	private Table dashboardAufträgeTable;
	private Table dashboardTermineTable;
	private Table unternehmensTable;
	private Table meineAufträgeTable;
	private Label unternehmensLabel;
	private Combo geschlechtCombo;
	private Controller controller;

	/**
	 * Create the shell.
	 */
	public CSPmainWindows() {
		super(Display.getDefault(), SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		controller = Controller.getInstance();
		
		//Upper static navigationbar
		Composite headerContainer = new Composite(this, SWT.NONE);
		headerContainer.setLayoutData(BorderLayout.NORTH);
		headerContainer.setLayout(new BorderLayout(0, 0));
		
		Composite rightHeader = new Composite(headerContainer, SWT.NONE);
		rightHeader.setLayoutData(BorderLayout.EAST);
		rightHeader.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button dashboardButton = new Button(rightHeader, SWT.NONE);
		dashboardButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		dashboardButton.setText("Dashboard");
		
		Button profilButton = new Button(rightHeader, SWT.NONE);
		profilButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		profilButton.setText("Profil");
		
		Button unternehmensButton = new Button(rightHeader, SWT.NONE);
		unternehmensButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		unternehmensButton.setText("Unternehmen");
		
		Button auftragsButton = new Button(rightHeader, SWT.NONE);
		auftragsButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		auftragsButton.setText("Aufträge");
		
		Composite leftHeader = new Composite(headerContainer, SWT.NONE);
		leftHeader.setLayoutData(BorderLayout.WEST);
		
		Label unternehmensLogo = new Label(leftHeader, SWT.NONE);
		unternehmensLogo.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		unternehmensLogo.setBounds(0, 0, 81, 25);
		unternehmensLogo.setText("LOGO");
		
		Composite middleHeader = new Composite(headerContainer, SWT.NONE);
		middleHeader.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		suchfeld = new Text(middleHeader, SWT.BORDER);
		suchfeld.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		suchfeld.setText("Suche");
		
		//Composite for distance controll in every direction
		Composite lowContainer = new Composite(this, SWT.NONE);
		lowContainer.setLayoutData(BorderLayout.SOUTH);
		
		Composite leftContainer = new Composite(this, SWT.NONE);
		leftContainer.setLayoutData(BorderLayout.WEST);
		
		Composite rightContainer = new Composite(this, SWT.NONE);
		rightContainer.setLayoutData(BorderLayout.EAST);
		
		//MainContainer containing the 4 Views of the Mainwindow (all final to access them from ActionHandlers)
		final Composite mainContainer = new Composite(this, SWT.NONE);
		mainContainer.setLayoutData(BorderLayout.CENTER);
		final StackLayout mainStack = new StackLayout();
		mainContainer.setLayout(mainStack);
		
		//DashboardContainer with the tables for quick access - first selected
		final Composite dashboardContainer = new Composite(mainContainer, SWT.NONE);
		mainStack.topControl = dashboardContainer;
		dashboardContainer.setLayout(new BorderLayout(0, 0));
		
		//MiddleContainer containing the two tables of the Dashboard
		Composite dashboardMiddleContainer = new Composite(dashboardContainer, SWT.NONE);
		dashboardMiddleContainer.setLayoutData(BorderLayout.CENTER);
		dashboardMiddleContainer.setLayout(new GridLayout(2, false));
		
		Label dashboardAufträgeLabel = new Label(dashboardMiddleContainer, SWT.NONE);
		dashboardAufträgeLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		dashboardAufträgeLabel.setText("Meine Aufträge");
		
		Label dashboardTermineLabel = new Label(dashboardMiddleContainer, SWT.NONE);
		dashboardTermineLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		dashboardTermineLabel.setText("Nächste Termine");
		
		dashboardAufträgeTable = new Table(dashboardMiddleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		dashboardAufträgeTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		dashboardAufträgeTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		dashboardAufträgeTable.setHeaderVisible(true);
		dashboardAufträgeTable.setLinesVisible(true);
		
		controller.generateTableHeaderMyAssignments(dashboardAufträgeTable);
		controller.generateMyAssignmentTableItemsDashboard(dashboardAufträgeTable);
		
		
		
		dashboardTermineTable = new Table(dashboardMiddleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		dashboardTermineTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		dashboardTermineTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		dashboardTermineTable.setHeaderVisible(true);
		dashboardTermineTable.setLinesVisible(true);

		//Composite for upper distance control
		Composite dashboardTopContainer = new Composite(dashboardContainer, SWT.NONE);
		dashboardTopContainer.setLayoutData(BorderLayout.NORTH);
		
		//Container with the companyview
		final Composite unternehmenContainer = new Composite(mainContainer, SWT.NONE);
		unternehmenContainer.setLayout(new BorderLayout(0, 0));
		
		Composite unternehmenTopContainer = new Composite(unternehmenContainer, SWT.NONE);
		unternehmenTopContainer.setLayoutData(BorderLayout.NORTH);
		
		Composite unternehmenMiddleContainer = new Composite(unternehmenContainer, SWT.NONE);
		unternehmenMiddleContainer.setLayoutData(BorderLayout.CENTER);
		unternehmenMiddleContainer.setLayout(new BorderLayout(0, 0));
		
		unternehmensTable = new Table(unternehmenMiddleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		unternehmensTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		unternehmensTable.setLayoutData(BorderLayout.CENTER);
		unternehmensTable.setHeaderVisible(true);
		unternehmensTable.setLinesVisible(true);
		
		controller.generateTableHeaderCompanyTable(unternehmensTable);
		
		Composite unternehmenMidleHeaderContainer = new Composite(unternehmenMiddleContainer, SWT.NONE);
		unternehmenMidleHeaderContainer.setLayoutData(BorderLayout.NORTH);
		unternehmenMidleHeaderContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label registrierteUnternehmenLabel = new Label(unternehmenMidleHeaderContainer, SWT.NONE);
		registrierteUnternehmenLabel.setFont(SWTResourceManager.getFont("Calibri", 16, SWT.NORMAL));
		registrierteUnternehmenLabel.setText("Registrierte Unternehmen");
		
		Composite unternehmenTableButtonContainer = new Composite(unternehmenMidleHeaderContainer, SWT.NONE);
		
		//Container with the Assignmentview
		final Composite aufträgeContainer = new Composite(mainContainer, SWT.NONE);
		aufträgeContainer.setLayout(new BorderLayout(0, 0));
		
		Composite aufträgeMiddleContainer = new Composite(aufträgeContainer, SWT.NONE);
		aufträgeMiddleContainer.setLayoutData(BorderLayout.CENTER);
		aufträgeMiddleContainer.setLayout(new BorderLayout(0, 0));
		
		Composite aufträgeMidleHeaderContainer = new Composite(aufträgeMiddleContainer, SWT.NONE);
		aufträgeMidleHeaderContainer.setLayoutData(BorderLayout.NORTH);
		aufträgeMidleHeaderContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label meineAufträgeLabel = new Label(aufträgeMidleHeaderContainer, SWT.NONE);
		meineAufträgeLabel.setFont(SWTResourceManager.getFont("Calibri", 16, SWT.NORMAL));
		meineAufträgeLabel.setText("Meine Aufträge");
		
		Composite aufträgeTableButtonContainer = new Composite(aufträgeMidleHeaderContainer, SWT.NONE);
		aufträgeTableButtonContainer.setLayout(new BorderLayout(0, 0));
		
		Composite aufträgeUpperTableButtonContainer = new Composite(aufträgeTableButtonContainer, SWT.NONE);
		aufträgeUpperTableButtonContainer.setLayoutData(BorderLayout.NORTH);
		aufträgeUpperTableButtonContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label aufträgeUpperTableButtonLabel = new Label(aufträgeUpperTableButtonContainer, SWT.NONE);
		
		Composite aufträgeLowerTableButtonContainer = new Composite(aufträgeTableButtonContainer, SWT.NONE);
		aufträgeLowerTableButtonContainer.setLayoutData(BorderLayout.SOUTH);
		aufträgeLowerTableButtonContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button aufträgeNeuButton = new Button(aufträgeLowerTableButtonContainer, SWT.NONE);
		aufträgeNeuButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					new AuftragErstellenPositionenWindow(null, null);
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getStackTrace(), "Fehler!", 2);
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getStackTrace(), "Fehler!", 2);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getStackTrace(), "Fehler!", 2);
				}
			}
		});
		aufträgeNeuButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		aufträgeNeuButton.setText("Neu");
		
		Button aufträgeBearbeitenButton = new Button(aufträgeLowerTableButtonContainer, SWT.NONE);
		aufträgeBearbeitenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		aufträgeBearbeitenButton.setText("Bearbeiten");
		aufträgeBearbeitenButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				for(TableItem item : meineAufträgeTable.getSelection()){
					if(item.getText(4).equals("open")){
						try {
							int antwort = JOptionPane.showOptionDialog(null, "Wenn Sie einen Auftrag bearbeiten wird der bestehende Auftrag gelöscht und alle Angebote gehen verlohren. Wollen Sie diesen Auftrag wirklich löschen?", "Auftrag Löschen", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Ja","Nein"}, "Ja");
							if(antwort == 0){
								controller.deleteAssignment((String) item.getData("id"));
								new AuftragErstellenPositionenWindow(null,(String) item.getData("id"));
							}
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
		
		Button aufträgeLöschenButton = new Button(aufträgeLowerTableButtonContainer, SWT.NONE);
		aufträgeLöschenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int antwort = JOptionPane.showOptionDialog(null, "Wollen Sie diesen Auftrag wirklich löschen?", "Auftrag Löschen", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Ja","Nein"}, "Ja");
				if(antwort == 0){
					for(TableItem item : meineAufträgeTable.getSelection()){
						try {
							controller.deleteAssignment((String) item.getData("id"));
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
						}
					}
				}
			}
		});
		aufträgeLöschenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		aufträgeLöschenButton.setText("Löschen");
		
		meineAufträgeTable = new Table(aufträgeMiddleContainer, SWT.BORDER | SWT.FULL_SELECTION);
		meineAufträgeTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		meineAufträgeTable.setLayoutData(BorderLayout.CENTER);
		meineAufträgeTable.setHeaderVisible(true);
		meineAufträgeTable.setLinesVisible(true);
		
		controller.generateTableHeaderAssignmentTable(meineAufträgeTable);
		
		Composite aufträgeTopContainer = new Composite(aufträgeContainer, SWT.NONE);
		aufträgeTopContainer.setLayoutData(BorderLayout.NORTH);
		
		//Container with Profilview
		final Composite profilContainer = new Composite(mainContainer, SWT.NONE);
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
				if(straßeTextField.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Es wurde keine Straße eingetragen!", "Fehler!", 2);
					return;
				}
				if(hausnummerTextField.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Es wurde keine Hausnummer eingetragen!", "Fehler!", 2);
					return;
				}
				if(!(emailTextField.getText().contains("@") && emailTextField.getText().contains("."))){
					JOptionPane.showMessageDialog(null, "Keine gültige E-Mail-Addresse eingetragen!", "Fehler!", 2);
					return;
				}
				Controller controller = Controller.getInstance();
				
				try {
					controller.updateUser(benutzernameTextField.getText(), vornameTextField.getText(), nachnameTextField.getText(), straßeTextField.getText(), hausnummerTextField.getText(), postleitzahlTextField.getText(), stadtTextField.getText(), emailTextField.getText(), telefonTextField.getText(), unternehmensTextField.getText(), geschlechtCombo.getText());
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
				}
				
			}
		});
		
		Composite profilMiddleContainer = new Composite(profilContainer, SWT.NONE);
		profilMiddleContainer.setLayoutData(BorderLayout.CENTER);
		profilMiddleContainer.setLayout(new GridLayout(2, false));
		
		Label benutzernameLabel = new Label(profilMiddleContainer, SWT.NONE);
		benutzernameLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		benutzernameLabel.setText("Benutzername");
		
		Label stadtLabel = new Label(profilMiddleContainer, SWT.NONE);
		stadtLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		stadtLabel.setText("Stadt");
		
		benutzernameTextField = new Text(profilMiddleContainer, SWT.READ_ONLY);
		benutzernameTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		benutzernameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		benutzernameTextField.setEditable(false);
		
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
		//Empty label to fill the GridLayout
		new Label(profilMiddleContainer, SWT.NONE);
		
		postleitzahlTextField = new Text(profilMiddleContainer, SWT.BORDER);
		postleitzahlTextField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		postleitzahlTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		postleitzahlTextField.setTextLimit(5);
		//Empty label to fill GridLayout
		new Label(profilMiddleContainer, SWT.NONE);
		
		Composite profilTopContainer = new Composite(profilContainer, SWT.NONE);
		profilTopContainer.setLayoutData(BorderLayout.NORTH);
		createContents();
		
		//SelectionListener of the Button of the Header to switch between views
		dashboardButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				mainStack.topControl = dashboardContainer;
				dashboardAufträgeTable.removeAll();
				controller.generateMyAssignmentTableItemsDashboard(dashboardAufträgeTable);
				mainContainer.layout();
			}
		});
		
		profilButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				mainStack.topControl = profilContainer;
				HashMap<String, String> profilInfo = controller.genereateMyProfileHashMap();
				benutzernameTextField.setText(profilInfo.get("id"));
				vornameTextField.setText(profilInfo.get("firstname"));
				nachnameTextField.setText(profilInfo.get("lastname"));
				straßeTextField.setText(profilInfo.get("street"));
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
		
		unternehmensButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				mainStack.topControl = unternehmenContainer;
				unternehmensTable.removeAll();
				controller.generateCompanyTableItems(unternehmensTable);
				mainContainer.layout();
			}
		});
		
		auftragsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				mainStack.topControl = aufträgeContainer;
				meineAufträgeTable.removeAll();
				controller.generateMyAssignmentTableItems(meineAufträgeTable);
				mainContainer.layout();
			}
		});
		
		dashboardAufträgeTable.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Table table = (Table) e.getSource();
				for(TableItem item : table.getSelection()){
					try {
						String auftragsID = (String) item.getData("id");
						new AuftragsansichtWindow(auftragsID);
					} catch (SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
					} catch (IOException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
					}
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
			
		});
		
		meineAufträgeTable.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Table table = (Table) e.getSource();
				for(TableItem item : table.getSelection()){
					try {
						String auftragsID = (String) item.getData("id");
						new AuftragsansichtWindow(auftragsID);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
					}
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
			
		});
		
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
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
			
		});
		
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
