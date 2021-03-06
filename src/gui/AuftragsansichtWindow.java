package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.widgets.Table;

import processing.Controller;

public class AuftragsansichtWindow extends Shell {
	
	private final Controller controller = Controller.getInstance();
	private Composite upperContainer, leftUpperContainer, rightUpperContainer, mainContainer, upperMainContainer, 
	middleUpperMainContainer, upperRightMainContainer, rightUpperMainContainer, upperRightUpperMainContainer, 
	lowerRightUpperMainContainer, lowerMainContainer, lowContainer, rightLowContainer, leftLowContainer, middleLeftLowContainer;
	private Label upperLeftUpperLabel, leistungenLabel, titelLabel, datumLabel, beschreibungLabel, erstelldatumLabel,
	ausschreibungsendeLabel, statusLabel, angeboteLabel, upperLeftLowLabel, lowerLeftLowLabel;
	private Text statusText, erstelldatumField, titelText, dateField, beschreibungText, ausschreibungsendeField;
	private Button bearbeitenButton, schließenButton;
	private Table angeboteTable;
	private Tree auftragsTree;
	private TreeColumn dienstleistungColumn, anzahlColumn, beschreibungColumn;
	private String assignmentID;
	private CSPmainWindows parent;
	private HashMap<String, String> auftragsInfo;
	
	/**
	 * Creates a Window that shows all information of a given Assignment
	 * @param parent of the Window
	 * @param assignmentID of the Assignment that should be displayed
	 * @throws SQLException
	 * @throws IOException
	 */
	public AuftragsansichtWindow(Shell parent, final String assignmentID) throws SQLException, IOException {
		super(parent, SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		
		this.parent = (CSPmainWindows) parent;
		this.assignmentID = assignmentID;
		
		auftragsInfo = controller.genereateAssignmentHashMap(assignmentID);
		
		upperContainer = new Composite(this, SWT.NONE);
		upperContainer.setLayoutData(BorderLayout.NORTH);
		upperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		leftUpperContainer = new Composite(upperContainer, SWT.NONE);
		leftUpperContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		upperLeftUpperLabel = new Label(leftUpperContainer, SWT.NONE);
		
		leistungenLabel = new Label(leftUpperContainer, SWT.NONE);
		leistungenLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		leistungenLabel.setText("\tEnthaltene Leistungen");
		
		rightUpperContainer = new Composite(upperContainer, SWT.NONE);
		
		mainContainer = new Composite(this, SWT.NONE);
		mainContainer.setLayoutData(BorderLayout.CENTER);
		mainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		upperMainContainer = new Composite(mainContainer, SWT.NONE);
		upperMainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		auftragsTree = new Tree(upperMainContainer, SWT.BORDER);
		auftragsTree.setHeaderVisible(true);
		dienstleistungColumn = new TreeColumn(auftragsTree, SWT.NONE);
		dienstleistungColumn.setText("Dienstleistung");
		dienstleistungColumn.setWidth(200);
		anzahlColumn = new TreeColumn(auftragsTree, SWT.NONE);
		anzahlColumn.setText("Anzahl");
		anzahlColumn.setWidth(100);
		beschreibungColumn = new TreeColumn(auftragsTree, SWT.NONE);
		beschreibungColumn.setText("Beschreibung");
		beschreibungColumn.setWidth(150);
		auftragsTree.setLinesVisible(true);
		
		controller.builTreeWithPositons(assignmentID, auftragsTree);
		
		middleUpperMainContainer = new Composite(upperMainContainer, SWT.NONE);
		middleUpperMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		upperRightMainContainer = new Composite(middleUpperMainContainer, SWT.NONE);
		upperRightMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		titelLabel = new Label(upperRightMainContainer, SWT.NONE);
		titelLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		titelLabel.setText("Titel des Auftrags");
		
		titelText = new Text(upperRightMainContainer, SWT.BORDER);
		titelText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		titelText.setEditable(false);
		titelText.setText(auftragsInfo.get("title"));
		
		datumLabel = new Label(upperRightMainContainer, SWT.NONE);
		datumLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		datumLabel.setText("Datum der Ausführung");
		
		dateField = new Text(upperRightMainContainer, SWT.BORDER | SWT.DROP_DOWN | SWT.LONG);
		dateField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		dateField.setEditable(false);
		dateField.setText(auftragsInfo.get("duedate"));
		
		beschreibungLabel = new Label(upperRightMainContainer, SWT.NONE);
		beschreibungLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		beschreibungLabel.setText("Zusätzliche Beschreibungen");
		
		beschreibungText = new Text(middleUpperMainContainer, SWT.BORDER);
		beschreibungText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		beschreibungText.setEditable(false);
		beschreibungText.setText(auftragsInfo.get("description"));
		
		rightUpperMainContainer = new Composite(upperMainContainer, SWT.NONE);
		rightUpperMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		upperRightUpperMainContainer = new Composite(rightUpperMainContainer, SWT.NONE);
		upperRightUpperMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		erstelldatumLabel = new Label(upperRightUpperMainContainer, SWT.NONE);
		erstelldatumLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		erstelldatumLabel.setText("Erstelldatum");
		
		erstelldatumField = new Text(upperRightUpperMainContainer, SWT.BORDER | SWT.LONG);
		erstelldatumField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		erstelldatumField.setEditable(false);
		erstelldatumField.setText(auftragsInfo.get("dateofcration"));
		
		ausschreibungsendeLabel = new Label(upperRightUpperMainContainer, SWT.NONE);
		ausschreibungsendeLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		ausschreibungsendeLabel.setText("Ausschreibungsende");
		
		ausschreibungsendeField = new Text(upperRightUpperMainContainer, SWT.BORDER | SWT.LONG);
		ausschreibungsendeField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		ausschreibungsendeField.setEditable(false);
		ausschreibungsendeField.setText(auftragsInfo.get("deadline"));
		
		statusLabel = new Label(upperRightUpperMainContainer, SWT.NONE);
		statusLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		statusLabel.setText("Status");
		
		lowerRightUpperMainContainer = new Composite(rightUpperMainContainer, SWT.NONE);
		lowerRightUpperMainContainer.setLayout(new BorderLayout(0, 0));
		
		statusText = new Text(lowerRightUpperMainContainer, SWT.BORDER);
		statusText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		statusText.setLayoutData(BorderLayout.NORTH);
		statusText.setEditable(false);
		statusText.setText(auftragsInfo.get("status"));
		
		lowerMainContainer = new Composite(mainContainer, SWT.NONE);
		lowerMainContainer.setLayout(new BorderLayout(0, 0));
		
		angeboteLabel = new Label(lowerMainContainer, SWT.NONE);
		angeboteLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		angeboteLabel.setLayoutData(BorderLayout.NORTH);
		angeboteLabel.setText("\tErhaltene Angebote");
		
		angeboteTable = new Table(lowerMainContainer, SWT.BORDER | SWT.FULL_SELECTION);
		angeboteTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		angeboteTable.setLayoutData(BorderLayout.CENTER);
		angeboteTable.setHeaderVisible(true);
		angeboteTable.setLinesVisible(true);
		angeboteTable.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Table table = (Table) e.getSource();
				for(TableItem item : table.getSelection()){
					try {
						String angebotsID = (String) item.getData("id");
						new AngeboteansichtWindow(((Table)e.getSource()).getShell(), angebotsID);
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
		
		controller.generateTableHeaderOfferTable(angeboteTable);
		controller.generateOfferTableItems(angeboteTable, assignmentID);
		
		lowContainer = new Composite(this, SWT.NONE);
		lowContainer.setLayoutData(BorderLayout.SOUTH);
		lowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		rightLowContainer = new Composite(lowContainer, SWT.NONE);
		
		leftLowContainer = new Composite(lowContainer, SWT.NONE);
		leftLowContainer.setLayout(new BorderLayout(0, 0));
		
		upperLeftLowLabel = new Label(leftLowContainer, SWT.NONE);
		upperLeftLowLabel.setLayoutData(BorderLayout.NORTH);
		
		middleLeftLowContainer = new Composite(leftLowContainer, SWT.NONE);
		middleLeftLowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		bearbeitenButton = new Button(middleLeftLowContainer, SWT.NONE);
		bearbeitenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		bearbeitenButton.setText("Bearbeiten");
		bearbeitenButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
					try {
						int antwort = JOptionPane.showOptionDialog(null, "Wenn Sie einen Auftrag bearbeiten wird der bestehende Auftrag gelöscht und alle Angebote gehen verlohren. Wollen Sie diesen Auftrag wirklich löschen?", "Auftrag Löschen", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Ja","Nein"}, "Ja");
						if(antwort == 0){
							int antwortBearbeiten = JOptionPane.showOptionDialog(null, "Wenn Sie einen Auftrag bearbeiten wird der bestehende Auftrag gelöscht und alle Angebote gehen verlohren. Wollen Sie diesen Auftrag wirklich löschen?", "Auftrag Löschen", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Ja","Nein"}, "Ja");
							if(antwortBearbeiten == 0){
								new AuftragErstellenPositionenWindow(((Button)e.getSource()).getShell(), null,assignmentID);
								((Button)e.getSource()).getShell().dispose();
								controller.deleteAssignment(assignmentID);
							}
						}
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
					}
			}
		});
		
		schließenButton = new Button(middleLeftLowContainer, SWT.NONE);
		schließenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		schließenButton.setText("Schließen");
		schließenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((Button)e.getSource()).getShell().dispose();
			}
		});
		
		lowerLeftLowLabel = new Label(leftLowContainer, SWT.NONE);
		lowerLeftLowLabel.setLayoutData(BorderLayout.SOUTH);
		
		createContents();
	}
	
	public void updateContent(){
		try {
			Controller.init(null, null);
			angeboteTable.removeAll();
			controller.generateOfferTableItems(angeboteTable, assignmentID);
			HashMap<String, String> auftragsInfo = controller.genereateAssignmentHashMap(assignmentID);
			statusText.setText(auftragsInfo.get("status"));
			parent.updateContent();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "Fehler!", 2);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e, "Fehler!", 2);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "Fehler!", 2);
		}
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Auftragsansicht");
		setSize(1280, 720);
		this.setImage(new Image(null, ".\\images\\handsimIcon.png"));
		try {
			this.open();
			this.layout();
			while (!this.isDisposed()) {
				if (!this.getDisplay().readAndDispatch()) {
					this.getDisplay().sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
