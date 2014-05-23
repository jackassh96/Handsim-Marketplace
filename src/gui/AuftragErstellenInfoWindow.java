package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;

import processing.Controller;
import processing.helper.DatumFull;

public class AuftragErstellenInfoWindow extends Shell {
	
	private final Controller controller = Controller.getInstance();
	private Composite upperContainer, leftUpperContainer, rightUpperContainer, mainContainer, rightMainContainer, 
	upperRightMainContainer, lowContainer, rightLowContainer, leftLowContainer, middleLeftLowContainer;
	private Label upperLeftUpperLabel, ihreAuswahlLabel, titelLabel, datumLabel, beschreibungLabel, upperLeftLowLabel
	, lowerLeftLowLabel;
	private Text titelText, beschreibungText;
	private DateTime dateField;
	private Tree auftragsTree;
	private TreeColumn dienstleistungColumn, anzahlColumn, beschreibungColumn;
	private Button zurückButton, erstellenButton, abbrechenButton;

	/**
	 * Creates a window to add more information to a given List of the selected services to make an complied Assignment
	 * @param parent of the Window to return to if necessary
	 * @param outputItems that had been selected from this new Assignment
	 * @param assignmentID if it is an already existing Assignment that will be edited
	 */
	public AuftragErstellenInfoWindow(Shell parent, final ArrayList<TreeItem> outputItems, final String assignmentID) {
		super(parent, SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		
		upperContainer = new Composite(this, SWT.NONE);
		upperContainer.setLayoutData(BorderLayout.NORTH);
		upperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		leftUpperContainer = new Composite(upperContainer, SWT.NONE);
		leftUpperContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		upperLeftUpperLabel = new Label(leftUpperContainer, SWT.NONE);
		
		ihreAuswahlLabel = new Label(leftUpperContainer, SWT.NONE);
		ihreAuswahlLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		ihreAuswahlLabel.setText("\tIhre ausgewählten Leistungen");
		
		rightUpperContainer = new Composite(upperContainer, SWT.NONE);
		
		mainContainer = new Composite(this, SWT.NONE);
		mainContainer.setLayoutData(BorderLayout.CENTER);
		mainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		auftragsTree = new Tree(mainContainer, SWT.BORDER);
		auftragsTree.setHeaderVisible(true);
		dienstleistungColumn = new TreeColumn(auftragsTree, SWT.NONE);
		dienstleistungColumn.setText("Dienstleistung");
		dienstleistungColumn.setWidth(300);
		anzahlColumn = new TreeColumn(auftragsTree, SWT.NONE);
		anzahlColumn.setText("Anzahl");
		anzahlColumn.setWidth(100);
		beschreibungColumn = new TreeColumn(auftragsTree, SWT.NONE);
		beschreibungColumn.setText("Beschreibung");
		beschreibungColumn.setWidth(200);
		auftragsTree.setLinesVisible(true);
		for(TreeItem outputItem : outputItems){
			AuftragErstellenPositionenWindow.getSameTreeItem(outputItem, auftragsTree);
		}
		outputItems.get(0).getParent().getShell().dispose();
		
		rightMainContainer = new Composite(mainContainer, SWT.NONE);
		rightMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		upperRightMainContainer = new Composite(rightMainContainer, SWT.NONE);
		upperRightMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		titelLabel = new Label(upperRightMainContainer, SWT.NONE);
		titelLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		titelLabel.setText("Titel des Auftrags");
		
		titelText = new Text(upperRightMainContainer, SWT.BORDER);
		titelText.setToolTipText("Hier Titel des Auftrags eintragen");
		titelText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		datumLabel = new Label(upperRightMainContainer, SWT.NONE);
		datumLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		datumLabel.setText("Datum der Ausführung");
		
		dateField = new DateTime(upperRightMainContainer, SWT.BORDER | SWT.DROP_DOWN | SWT.LONG);
		dateField.setToolTipText("Hier Datum der Ausf\u00FChrung festlegen");
		dateField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		
		beschreibungLabel = new Label(upperRightMainContainer, SWT.NONE);
		beschreibungLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		beschreibungLabel.setText("Zusätzliche Beschreibungen");
		
		beschreibungText = new Text(rightMainContainer, SWT.BORDER);
		beschreibungText.setToolTipText("Hier zus\u00E4tzliche Beschreibungen f\u00FCr den Auftrag eintragen");
		beschreibungText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
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
		
		zurückButton = new Button(middleLeftLowContainer, SWT.NONE);
		zurückButton.setToolTipText("Zur Leistungsauswahl zur\u00FCckkehren");
		zurückButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		zurückButton.setText("Zurück");
		zurückButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					ArrayList<TreeItem> returnItems = new ArrayList<TreeItem>();
					for(TreeItem returnItem : auftragsTree.getItems()){
						AuftragErstellenPositionenWindow.getPositionItems(returnItems, returnItem);
					}
					String[][] dataArray = new String[returnItems.size()][];
					for(int i = 0; i < returnItems.size(); i++){
						dataArray[i] = new String[]{((String[]) returnItems.get(i).getData())[0], returnItems.get(i).getText(1), returnItems.get(i).getText(2)};
					}
					Shell parent = (Shell) ((Button)e.getSource()).getShell().getParent();
					((Button)e.getSource()).getShell().dispose();
					new AuftragErstellenPositionenWindow(parent, dataArray, assignmentID);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
				}
				
			}				
		});
		
		erstellenButton = new Button(middleLeftLowContainer, SWT.NONE);
		erstellenButton.setToolTipText("Auftrag erstellen");
		erstellenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		erstellenButton.setText("Erstellen");
		erstellenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DatumFull date = new DatumFull();
				DatumFull chosenDate = null;
				try {
					//Somehow the DateField returns the Month -1
					chosenDate = new DatumFull(""+dateField.getDay(), ""+(dateField.getMonth()+1), ""+dateField.getYear());
					int result = date.compareTo(chosenDate);
					if(result >= 0){
						JOptionPane.showMessageDialog(null, "Das Erfüllungsdatum muss in der Zukunft liegen!", "Fehler!", 2);
						return;
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
				}
				if(titelText.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Bitte tragen Sie einen Titel für den Auftrag hinzu!", "Fehler!", 2);
					return;
				}
				try {
					if(assignmentID != null){
						int antwort = JOptionPane.showOptionDialog(null, "Wollen Sie den Auftrag in seiner alten Form löschen (dabei werden alle Angebote abgelehnt) oder wollen Sie einen neuen Auftrag in der jetzigen Form anlegen?", "Auftrag Erstellen", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Bearbeiten","Neu"}, "Ja");
						if(antwort == 0){
							controller.deleteAssignment(assignmentID);
						}else if(antwort == -1){
							return;
						}
					}
					String auftragsID = controller.createAssignment(beschreibungText.getText(), date.toMachineString(), titelText.getText(), chosenDate.toMachineString());
					for(TreeItem position : outputItems){
						controller.createPosition(((String[])position.getData())[0], auftragsID, position.getText(2), position.getText(1));
					}
					CSPmainWindows parent = (CSPmainWindows) ((Button)e.getSource()).getShell().getParent();
					parent.updateContent();
					((Button)e.getSource()).getShell().dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getStackTrace(), "Fehler!: ", 2);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, e1.getStackTrace(), "Fehler!: ", 2);
				}
			}
		});
		
		abbrechenButton = new Button(middleLeftLowContainer, SWT.NONE);
		abbrechenButton.setToolTipText("Auftragserstellung abbrechen");
		abbrechenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		abbrechenButton.setText("Abbrechen");
		abbrechenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((Button)e.getSource()).getShell().dispose();
			}
		});
		
		lowerLeftLowLabel = new Label(leftLowContainer, SWT.NONE);
		lowerLeftLowLabel.setLayoutData(BorderLayout.SOUTH);
		
		if(assignmentID != null){
			HashMap<String, String> auftragsInfo = controller.genereateAssignmentHashMap(assignmentID);
			titelText.setText(auftragsInfo.get("title"));
			beschreibungText.setText(auftragsInfo.get("description"));
		}
		
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Auftrag Erstellen");
		setSize(900, 700);
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
