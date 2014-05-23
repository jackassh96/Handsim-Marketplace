package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Slider;

import swing2swt.layout.FlowLayout;
import swing2swt.layout.BoxLayout;

import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;

import processing.Controller;
import processing.data.Category;
import processing.data.Position;
import processing.dataBase.dbHandler;

public class AuftragErstellenPositionenWindow extends Shell {

	private final Controller controller = Controller.getInstance();
	private Composite lowContainer, leftLowContainer, rightLowContainer, middleRightLowContainer, upperContainer, 
	mainContainer, upperMainContainer, leftUpperMainContainer, rightLeftUpperMainContainer, lowerRightLeftUpperMainContainer,
	rightUpperMainContainer, rightRightUpperMainContainer, lowerRightRightUpperMainContainer, middleMainContainer;
	private Label upperRightLowLabel, lowerRightLowLabel, upperLabel, inputTreeLabel, leftMenuLabel, outputTreeLabel, 
	rightMenuLabel;
	private Button weiterButton, abbrechenButton, leftKlappenButton, leftZuklappenButton, einfugenButton, 
	rightKlappenButton, rightZuklappenButton, loschenButton;
	private Tree inputTree, outputTree;
	private TreeColumn dienstleistungColumn, anzahlColumn, beschreibungColumn;
	Shell parent;
	String[][] dataArray;
	String assignmentID;

	/**
	 * Creates a Window in witch you can select services to be a part of a new Assignment
	 * @param parent of the window
	 * @param dataArray containing already selected services of the Assignment, if necessary, if not input null
	 * @param assignmentID of an Assignment that should be edited, if necessary, if not input null
	 * @throws SQLException
	 * @throws IOException
	 * @throws Exception
	 */
	public AuftragErstellenPositionenWindow(Shell parent, String[][] dataArray, final String assignmentID) throws SQLException, IOException, Exception {
		super(parent, SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		this.parent = parent;
		this.dataArray = dataArray;
		this.assignmentID = assignmentID;
		
		lowContainer = new Composite(this, SWT.NONE);
		lowContainer.setLayoutData(BorderLayout.SOUTH);
		lowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		leftLowContainer = new Composite(lowContainer, SWT.NONE);
		leftLowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		rightLowContainer = new Composite(lowContainer, SWT.NONE);
		rightLowContainer.setLayout(new BorderLayout(0, 0));
		
		upperRightLowLabel = new Label(rightLowContainer, SWT.NONE);
		upperRightLowLabel.setLayoutData(BorderLayout.NORTH);
		
		lowerRightLowLabel = new Label(rightLowContainer, SWT.NONE);
		lowerRightLowLabel.setLayoutData(BorderLayout.SOUTH);
		
		middleRightLowContainer = new Composite(rightLowContainer, SWT.NONE);
		middleRightLowContainer.setLayoutData(BorderLayout.CENTER);
		middleRightLowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		weiterButton = new Button(middleRightLowContainer, SWT.NONE);
		weiterButton.setToolTipText("Mit der erstellung des Auftrags fortfahren");
		weiterButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		weiterButton.setText("Weiter");
		weiterButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(outputTree.getItems().length > 0){
					ArrayList<TreeItem> items = new ArrayList<TreeItem>();
					for(TreeItem item : outputTree.getItems()){
						getPositionItems(items, item);
					}
					Shell parent = (Shell) ((Button)e.getSource()).getShell().getParent();
					((Button)e.getSource()).getShell().setVisible(false);
					new AuftragErstellenInfoWindow(parent, items, assignmentID);
				}else{
					JOptionPane.showMessageDialog(null, "Bitte fï¿½gen Sie Services zu Ihrer Auswahl hinzu", "Fehler!", 2);
				}
			}
		});
		
		abbrechenButton = new Button(middleRightLowContainer, SWT.NONE);
		abbrechenButton.setToolTipText("Auftragserstellung abbrechen");
		abbrechenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		abbrechenButton.setText("Abbrechen");
		abbrechenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((Button)e.getSource()).getShell().dispose();
			}
		});
		
		upperContainer = new Composite(this, SWT.NONE);
		upperContainer.setLayoutData(BorderLayout.NORTH);
		upperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		upperLabel = new Label(upperContainer, SWT.NONE);
		
		mainContainer = new Composite(this, SWT.NONE);
		mainContainer.setLayoutData(BorderLayout.CENTER);
		mainContainer.setLayout(new BorderLayout(0, 0));
		
		upperMainContainer = new Composite(mainContainer, SWT.NONE);
		upperMainContainer.setLayoutData(BorderLayout.NORTH);
		upperMainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		leftUpperMainContainer = new Composite(upperMainContainer, SWT.NONE);
		leftUpperMainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		inputTreeLabel = new Label(leftUpperMainContainer, SWT.NONE);
		inputTreeLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		inputTreeLabel.setText("Mï¿½gliche \nAuftragselemente");
		
		rightLeftUpperMainContainer = new Composite(leftUpperMainContainer, SWT.NONE);
		rightLeftUpperMainContainer.setLayout(new BorderLayout());
				
		leftMenuLabel = new Label(rightLeftUpperMainContainer, SWT.NONE);
		leftMenuLabel.setLayoutData(BorderLayout.NORTH);
		
		lowerRightLeftUpperMainContainer = new Composite(rightLeftUpperMainContainer, SWT.NONE);
		lowerRightLeftUpperMainContainer.setLayoutData(BorderLayout.SOUTH);
		lowerRightLeftUpperMainContainer.setLayout(new FillLayout());
				
		leftKlappenButton = new Button(lowerRightLeftUpperMainContainer, SWT.NONE);
		leftKlappenButton.setToolTipText("Gesamten Leistungsbaum aufklappen");
		leftKlappenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		leftKlappenButton.setText("Auflappen");
		leftKlappenButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				for(TreeItem item : inputTree.getItems()){
					expandAll(item);
				}
			}
		});
				
		leftZuklappenButton = new Button(lowerRightLeftUpperMainContainer, SWT.NONE);
		leftZuklappenButton.setToolTipText("Gesamten Leistungsbaum zuklappen");
		leftZuklappenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		leftZuklappenButton.setText("Zuklappen");
		leftZuklappenButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				for(TreeItem item : inputTree.getItems()){
					collapseAll(item);
				}
			}
		});
		
		einfugenButton = new Button(lowerRightLeftUpperMainContainer, SWT.NONE);
		einfugenButton.setToolTipText("Ausgew\u00E4hlte Leistung zum Auftrag hinzuf\u00FCgen");
		einfugenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		einfugenButton.setText("Einfï¿½gen");
		einfugenButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(inputTree.getSelection().length > 0){
					TreeItem[] selectedItems = inputTree.getSelection();
					for(TreeItem item : selectedItems){
						if(item.getItems().length < 1){
							//Create a Window to ask for the amount and further informations
							String menge = "not set";
							String beschreibung = "";
							JTextField mengeField =  new JTextField();
							JTextArea beschreibungArea = new JTextArea("",20,10);
							JPanel pnl = new JPanel(new java.awt.BorderLayout());
							JPanel pnl2 = new JPanel(new java.awt.BorderLayout());
							JPanel pnl3 = new JPanel(new java.awt.BorderLayout());
							pnl3.add(pnl, java.awt.BorderLayout.NORTH);
							pnl3.add(pnl2, java.awt.BorderLayout.SOUTH);
							pnl.add(new JLabel("Bitte geben Sie die gewünschte Anzahl an:"), java.awt.BorderLayout.NORTH);
							pnl.add(mengeField, java.awt.BorderLayout.SOUTH);
							pnl2.add(new JLabel("Hier, falls nötig, weitere Beschreibung einfügen:"), java.awt.BorderLayout.NORTH);
							pnl2.add(beschreibungArea, java.awt.BorderLayout.SOUTH);
							boolean inputCheck;
							do{
								inputCheck = false;
								int okCxl = JOptionPane.showConfirmDialog(null,pnl3,"Enter Data",JOptionPane.OK_CANCEL_OPTION);
	                            if (okCxl == JOptionPane.OK_OPTION) {
	                            	menge = mengeField.getText();
	                            	beschreibung = beschreibungArea.getText();
		                            int zusatzlicheMenge;
									try{
										zusatzlicheMenge = Integer.parseInt(menge);
										TreeItem outputItem = getSameTreeItem(item, outputTree);
										if(outputItem.getText(1).equals("")){
											outputItem.setText(new String[]{item.getText(), ""+menge, beschreibung});
										}else{
											int alteMenge = Integer.parseInt(outputItem.getText(1));
											outputItem.setText(new String[]{item.getText(), ""+(alteMenge + zusatzlicheMenge), beschreibung});
										}
									}catch(NumberFormatException notInt){
										JOptionPane.showMessageDialog(null, "Bitte eine Zahl eingeben", "Fehler!", 2);
										inputCheck = true;
									}
	                            }
							}while(inputCheck);
						}
					}
				}
				
			}
		});
		
		rightUpperMainContainer = new Composite(upperMainContainer, SWT.NONE);
		rightUpperMainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		outputTreeLabel = new Label(rightUpperMainContainer, SWT.NONE);
		outputTreeLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		outputTreeLabel.setText("Ausgewï¿½hlte \nAuftragselemente");
		
		rightRightUpperMainContainer = new Composite(rightUpperMainContainer, SWT.NONE);
		rightRightUpperMainContainer.setLayout(new BorderLayout());
		
		rightMenuLabel = new Label(rightRightUpperMainContainer, SWT.NONE);
		rightMenuLabel.setLayoutData(BorderLayout.NORTH);
		
		lowerRightRightUpperMainContainer = new Composite(rightRightUpperMainContainer, SWT.NONE);
		lowerRightRightUpperMainContainer.setLayoutData(BorderLayout.SOUTH);
		lowerRightRightUpperMainContainer.setLayout(new FillLayout());
		
		rightKlappenButton = new Button(lowerRightRightUpperMainContainer, SWT.NONE);
		rightKlappenButton.setToolTipText("Gesamten Leistungsbaum aufklappen");
		rightKlappenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		rightKlappenButton.setText("Aufklappen");
		rightKlappenButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				for(TreeItem item : outputTree.getItems()){
					expandAll(item);
				}
			}
		});
		
		rightZuklappenButton = new Button(lowerRightRightUpperMainContainer, SWT.NONE);
		rightZuklappenButton.setToolTipText("Gesamten Leistungsbaum zuklappen");
		rightZuklappenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		rightZuklappenButton.setText("Zuklappen");
		rightZuklappenButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				for(TreeItem item : outputTree.getItems()){
					collapseAll(item);
				}
			}
		});
		
		loschenButton = new Button(lowerRightRightUpperMainContainer, SWT.NONE);
		loschenButton.setToolTipText("Ausgew\u00E4hlte Leistung aus dem Auftrag entfernen");
		loschenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		loschenButton.setText("Lï¿½schen");
		loschenButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if(outputTree.getSelection().length > 0){
					TreeItem[] selectedItems = outputTree.getSelection();
					for(TreeItem item : selectedItems){
						if(item.getItems().length < 1){
							deleteAllUnneededItems(item);
						}
					}
				}
				
			}
		});
		
		middleMainContainer = new Composite(mainContainer, SWT.NONE);
		middleMainContainer.setLayoutData(BorderLayout.CENTER);
		middleMainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		inputTree = new Tree(middleMainContainer, SWT.BORDER);
		inputTree.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		controller.buildTreeFromMajorCategories(inputTree);
		
		outputTree = new Tree(middleMainContainer, SWT.BORDER);
		outputTree.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		outputTree.setHeaderVisible(true);
		dienstleistungColumn = new TreeColumn(outputTree, SWT.NONE);
		dienstleistungColumn.setText("Dienstleistung");
		dienstleistungColumn.setWidth(300);
		anzahlColumn = new TreeColumn(outputTree, SWT.NONE);
		anzahlColumn.setText("Anzahl");
		anzahlColumn.setWidth(100);
		beschreibungColumn = new TreeColumn(outputTree, SWT.NONE);
		beschreibungColumn.setText("Beschreibung");
		beschreibungColumn.setWidth(200);
		outputTree.setLinesVisible(true);
		
		if(dataArray != null){
			for(String[] singleData : dataArray){
				for(TreeItem item : inputTree.getItems()){
					TreeItem tempItem = idTaken(item, singleData[0]);
					if(tempItem != null){
						TreeItem foundItem = getSameTreeItem(tempItem, outputTree);
						foundItem.setText(new String[]{item.getText(), singleData[1], singleData[2]});
					}
				}
			}
			
		}else if(assignmentID != null){
			controller.builTreeWithPositons(assignmentID, outputTree);
		}

		createContents();
	}	

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Auftrag Erstellen");
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
	
	/**
	 * Copys all the contant of an item and its parents into new items that will be attached to the given tree
	 * @param item that should be copyed
	 * @param tree the new Items should be attached to
	 * @return the copy of the item
	 */
	protected static TreeItem getSameTreeItem(TreeItem item, Tree tree){
		TreeItem returnItem = null;
		for(TreeItem searchItem : tree.getItems()){
			if(returnItem == null){
				returnItem = idTaken(searchItem, ((String[])item.getData())[0]);
			}
		}
		if(returnItem == null){
			if(((String[])item.getData())[2].equals("-1")){
				returnItem = new TreeItem(tree, SWT.NONE);

			}else{
				returnItem = new TreeItem(getSameTreeItem(item.getParentItem(), tree), SWT.NONE);
			}
			returnItem.setText(new String[]{item.getText(), item.getText(1), item.getText(2)});
			returnItem.setData(item.getData());
		}
		return returnItem;
	}

	/**
	 * Searches for a specified Item by comparing its data with a given Item and its childs
	 * @param searchItem is the item and the parent of the items that will be compared to the one that is to find
	 * @param toFindItem is the one that is searched for
	 * @return null if no one is found or the found item
	 */
	protected static TreeItem idTaken(TreeItem searchItem, String toFindID) {
		if(((String[])searchItem.getData())[0].equals(toFindID)){
			return searchItem;
		}else{
			for(TreeItem child : searchItem.getItems()){
				TreeItem tempItem = idTaken(child, toFindID);
				if(tempItem != null){
					return tempItem;
				}
			}
			return null;
		}
	}
	
	/**
	 * Sets the item and all of its childs expanded
	 * @param item to expand
	 */
	protected static void expandAll(TreeItem item){
		item.setExpanded(true);
		for(TreeItem child : item.getItems()){
			expandAll(child);
		}
	}
	
	/**
	 * Sets the item and all of its childs collapsed
	 * @param item to collapse
	 */
	protected static void collapseAll(TreeItem item){
		item.setExpanded(false);
		for(TreeItem child : item.getItems()){
			collapseAll(child);
		}
	}
	
	/**
	 * deletes the item from the tree and all its parents that dont have more childs
	 * @param deleteItem is the one to delete
	 */
	protected static void deleteAllUnneededItems(TreeItem deleteItem){
		if(deleteItem.getItems().length == 0){
			TreeItem parent = deleteItem.getParentItem();
			deleteItem.dispose();
			if(parent != null){
				deleteAllUnneededItems(parent);
			}
		}
	}

	/**
	 * Adds all the child items that have no more childs to a given ArrayList of TreeItem
	 * @param items ArrayList to add all found childs with no more childs to
	 * @param item to check
	 */
	protected static void getPositionItems(ArrayList<TreeItem> items, TreeItem item) {
		if(item.getItems().length > 0){
			for(TreeItem child : item.getItems())
			getPositionItems(items, child);
		}else{
			items.add(item);
		}
		
	}

}
