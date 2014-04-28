package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import processing.Controller;
import processing.helper.DatumFull;

public class AuftragErstellenInfoWindow extends Shell {
	private Text titelText;
	private Text beschreibungText;

	/**
	 * Create the shell.
	 * @param treeItems 
	 * @param display
	 */
	public AuftragErstellenInfoWindow(final ArrayList<TreeItem> outputItems, final String assignmentID) {
		super(Display.getDefault(), SWT.SHELL_TRIM);
		
		setLayout(new BorderLayout(0, 0));
		
		final Controller controller = Controller.getInstance();
		
		Composite upperContainer = new Composite(this, SWT.NONE);
		upperContainer.setLayoutData(BorderLayout.NORTH);
		upperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite leftUpperContainer = new Composite(upperContainer, SWT.NONE);
		leftUpperContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label upperLeftUpperLabel = new Label(leftUpperContainer, SWT.NONE);
		
		Label ihreAuswahlLabel = new Label(leftUpperContainer, SWT.NONE);
		ihreAuswahlLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		ihreAuswahlLabel.setText("\tIhre ausgewählten Leistungen");
		
		Composite rightUpperContainer = new Composite(upperContainer, SWT.NONE);
		
		Composite mainContainer = new Composite(this, SWT.NONE);
		mainContainer.setLayoutData(BorderLayout.CENTER);
		mainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		final Tree auftragsTree = new Tree(mainContainer, SWT.BORDER);
		auftragsTree.setHeaderVisible(true);
		TreeColumn dienstleistungColumn = new TreeColumn(auftragsTree, SWT.NONE);
		dienstleistungColumn.setText("Dienstleistung");
		dienstleistungColumn.setWidth(300);
		TreeColumn anzahlColumn = new TreeColumn(auftragsTree, SWT.NONE);
		anzahlColumn.setText("Anzahl");
		anzahlColumn.setWidth(100);
		TreeColumn beschreibungColumn = new TreeColumn(auftragsTree, SWT.NONE);
		beschreibungColumn.setText("Beschreibung");
		beschreibungColumn.setWidth(200);
		auftragsTree.setLinesVisible(true);
		for(TreeItem outputItem : outputItems){
			AuftragErstellenPositionenWindow.getSameTreeItem(outputItem, auftragsTree);
		}
		
		Composite rightMainContainer = new Composite(mainContainer, SWT.NONE);
		rightMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite upperRightMainContainer = new Composite(rightMainContainer, SWT.NONE);
		upperRightMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label titelLabel = new Label(upperRightMainContainer, SWT.NONE);
		titelLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		titelLabel.setText("Titel des Auftrags");
		
		titelText = new Text(upperRightMainContainer, SWT.BORDER);
		titelText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Label datumLabel = new Label(upperRightMainContainer, SWT.NONE);
		datumLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		datumLabel.setText("Datum der Ausführung");
		
		final DateTime dateField = new DateTime(upperRightMainContainer, SWT.BORDER | SWT.DROP_DOWN | SWT.LONG) ;
		dateField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		
		Label beschreibungLabel = new Label(upperRightMainContainer, SWT.NONE);
		beschreibungLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		beschreibungLabel.setText("Zusätzliche Beschreibungen");
		
		beschreibungText = new Text(rightMainContainer, SWT.BORDER);
		beschreibungText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Composite lowContainer = new Composite(this, SWT.NONE);
		lowContainer.setLayoutData(BorderLayout.SOUTH);
		lowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite rightLowContainer = new Composite(lowContainer, SWT.NONE);
		
		Composite leftLowContainer = new Composite(lowContainer, SWT.NONE);
		leftLowContainer.setLayout(new BorderLayout(0, 0));
		
		Label upperLeftLowLabel = new Label(leftLowContainer, SWT.NONE);
		upperLeftLowLabel.setLayoutData(BorderLayout.NORTH);
		
		Composite middleLeftLowContainer = new Composite(leftLowContainer, SWT.NONE);
		middleLeftLowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button zurückButton = new Button(middleLeftLowContainer, SWT.NONE);
		zurückButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		zurückButton.setText("Zurück");
		
		Button erstellenButton = new Button(middleLeftLowContainer, SWT.NONE);
		erstellenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		erstellenButton.setText("Erstellen");
		
		Button abbrechenButton = new Button(middleLeftLowContainer, SWT.NONE);
		abbrechenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		abbrechenButton.setText("Abbrechen");
		
		Label lowerLeftLowLabel = new Label(leftLowContainer, SWT.NONE);
		lowerLeftLowLabel.setLayoutData(BorderLayout.SOUTH);
		
		if(assignmentID != null){
			HashMap<String, String> auftragsInfo = controller.genereateAssignmentHashMap(assignmentID);
			titelText.setText(auftragsInfo.get("title"));
			beschreibungText.setText(auftragsInfo.get("description"));
		}
		
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
					((Button)e.getSource()).getShell().dispose();
					new AuftragErstellenPositionenWindow(dataArray, assignmentID);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
				}
				
			}				
		});
		
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
					String auftragsID = controller.createAssignment(beschreibungText.getText(), date.toMachineString(), null, titelText.getText(), chosenDate.toMachineString());
					for(TreeItem position : outputItems){
						controller.createPosition(((String[])position.getData())[0], auftragsID, position.getText(2), position.getText(1));
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
				}
			}
		});
		
		abbrechenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((Button)e.getSource()).getShell().dispose();
			}
		});
		
		createContents();
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

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Auftrag Erstellen");
		setSize(900, 700);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
