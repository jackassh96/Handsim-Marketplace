package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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

import processing.Controller;
import processing.data.Category;
import processing.data.Position;
import processing.dataBase.dbHandler;

public class AuftragErstellenPositionenWindow extends Shell {

	/**
	 * Create the shell.
	 * @param display
	 * @throws Exception 
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public AuftragErstellenPositionenWindow() throws SQLException, IOException, Exception {
		super(Display.getDefault(), SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		
		Controller controller = Controller.getInstance();
		
		Composite lowContainer = new Composite(this, SWT.NONE);
		lowContainer.setLayoutData(BorderLayout.SOUTH);
		lowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite leftLowContainer = new Composite(lowContainer, SWT.NONE);
		leftLowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite rightLowContainer = new Composite(lowContainer, SWT.NONE);
		rightLowContainer.setLayout(new BorderLayout(0, 0));
		
		Label upperRightLowLabel = new Label(rightLowContainer, SWT.NONE);
		upperRightLowLabel.setLayoutData(BorderLayout.NORTH);
		
		Label lowerRightLowLabel = new Label(rightLowContainer, SWT.NONE);
		lowerRightLowLabel.setLayoutData(BorderLayout.SOUTH);
		
		Composite middleRightLowContainer = new Composite(rightLowContainer, SWT.NONE);
		middleRightLowContainer.setLayoutData(BorderLayout.CENTER);
		middleRightLowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button weiterButton = new Button(middleRightLowContainer, SWT.NONE);
		weiterButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		weiterButton.setText("Weiter");
		
		Button abbrechenButton = new Button(middleRightLowContainer, SWT.NONE);
		abbrechenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		abbrechenButton.setText("Abbrechen");
		
		Composite upperContainer = new Composite(this, SWT.NONE);
		upperContainer.setLayoutData(BorderLayout.NORTH);
		upperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label upperLabel = new Label(upperContainer, SWT.NONE);
		
		Composite mainContainer = new Composite(this, SWT.NONE);
		mainContainer.setLayoutData(BorderLayout.CENTER);
		mainContainer.setLayout(new BorderLayout(0, 0));
		
		Composite upperMainContainer = new Composite(mainContainer, SWT.NONE);
		upperMainContainer.setLayoutData(BorderLayout.NORTH);
		upperMainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite leftUpperMainContainer = new Composite(upperMainContainer, SWT.NONE);
		leftUpperMainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label inputTreeLabel = new Label(leftUpperMainContainer, SWT.NONE);
		inputTreeLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		inputTreeLabel.setText("Mögliche \nAuftragselemente");
		
		Composite rightLeftUpperMainContainer = new Composite(leftUpperMainContainer, SWT.NONE);
		rightLeftUpperMainContainer.setLayout(new BorderLayout());
				
		Label leftMenuLabel = new Label(rightLeftUpperMainContainer, SWT.NONE);
		leftMenuLabel.setLayoutData(BorderLayout.NORTH);
		
		Composite lowerRightLeftUpperMainContainer = new Composite(rightLeftUpperMainContainer, SWT.NONE);
		lowerRightLeftUpperMainContainer.setLayoutData(BorderLayout.SOUTH);
		lowerRightLeftUpperMainContainer.setLayout(new FillLayout());
				
		Button leftKlappenButton = new Button(lowerRightLeftUpperMainContainer, SWT.NONE);
		leftKlappenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		leftKlappenButton.setText("Klappen");
		
		Button einfügenButton = new Button(lowerRightLeftUpperMainContainer, SWT.NONE);
		einfügenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		einfügenButton.setText("Einfügen");
		
		Composite rightUpperMainContainer = new Composite(upperMainContainer, SWT.NONE);
		rightUpperMainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label outputTreeLabel = new Label(rightUpperMainContainer, SWT.NONE);
		outputTreeLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		outputTreeLabel.setText("Ausgewählte \nAuftragselemente");
		
		Composite rightRightUpperMainContainer = new Composite(rightUpperMainContainer, SWT.NONE);
		rightRightUpperMainContainer.setLayout(new BorderLayout());
		
		Label rightMenuLabel = new Label(rightRightUpperMainContainer, SWT.NONE);
		rightMenuLabel.setLayoutData(BorderLayout.NORTH);
		
		Composite lowerRightRightUpperMainContainer = new Composite(rightRightUpperMainContainer, SWT.NONE);
		lowerRightRightUpperMainContainer.setLayoutData(BorderLayout.SOUTH);
		lowerRightRightUpperMainContainer.setLayout(new FillLayout());
		
		Button rightKlappenButton = new Button(lowerRightRightUpperMainContainer, SWT.NONE);
		rightKlappenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		rightKlappenButton.setText("Klappen");
		
		Button löschenButton = new Button(lowerRightRightUpperMainContainer, SWT.NONE);
		löschenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		löschenButton.setText("Löschen");
		
		Composite middleMainContainer = new Composite(mainContainer, SWT.NONE);
		middleMainContainer.setLayoutData(BorderLayout.CENTER);
		middleMainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		final Tree inputTree = new Tree(middleMainContainer, SWT.BORDER);
		inputTree.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		//TODO userdata
		controller = controller.init(null, new dbHandler());
		controller.buildTreeFromMajorCategories(inputTree);
		
		final Tree outputTree = new Tree(middleMainContainer, SWT.BORDER);
		outputTree.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		outputTree.setHeaderVisible(true);
		TreeColumn dienstleistungColumn = new TreeColumn(outputTree, SWT.NONE);
		dienstleistungColumn.setText("Dienstleistung");
		dienstleistungColumn.setWidth(300);
		TreeColumn anzahlColumn = new TreeColumn(outputTree, SWT.NONE);
		anzahlColumn.setText("Anzahl");
		anzahlColumn.setWidth(100);
		
		einfügenButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(inputTree.getSelection().length > 0){
					TreeItem[] selectedItems = inputTree.getSelection();
					for(TreeItem item : selectedItems){
						if(item.getItems().length < 1){
							String menge = JOptionPane.showInputDialog("Bitte gewünschte Anzahl eintragen: ");
							int zusätzlicheMenge;
							try{
								zusätzlicheMenge = Integer.parseInt(menge);
							}catch(NumberFormatException notInt){
								JOptionPane.showMessageDialog(null, "Bitte eine Zahl eingeben", "Fehler!", 2);
								return;
							}
							TreeItem outputItem = getSameTreeItem(item, outputTree);
							if(outputItem.getText(1).equals("")){
								outputItem.setText(new String[]{item.getText(), ""+menge});
							}else{
								int alteMenge = Integer.parseInt(outputItem.getText(1));
								outputItem.setText(new String[]{item.getText(), ""+(alteMenge + zusätzlicheMenge)});
							}
						}
					}
				}
				
			}
		});
		
		leftKlappenButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				for(TreeItem item : inputTree.getItems()){
					expandAll(item);
				}
			}
		});
		
		löschenButton.addSelectionListener(new SelectionAdapter(){
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
		
		rightKlappenButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				for(TreeItem item : outputTree.getItems()){
					expandAll(item);
				}
			}
		});
		
		weiterButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(outputTree.getItems().length > 0){
					((Button)e.getSource()).getShell().setVisible(false);
					ArrayList<TreeItem> items = new ArrayList<TreeItem>();
					for(TreeItem item : outputTree.getItems()){
						getPositionItems(items, item);
					}
					AuftragErstellenInfoWindow nextPage = new AuftragErstellenInfoWindow(((Button)e.getSource()).getShell(),outputTree);
				}else{
					JOptionPane.showMessageDialog(null, "Bitte fügen Sie Services zu Ihrer Auswahl hinzu", "Fehler!", 2);
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
	
	/**
	 * Copys all the contant of an item and its parents into new items that will be attached to the given tree
	 * @param item that should be copyed
	 * @param tree the new Items should be attached to
	 * @return the copy of the item
	 */
	private static TreeItem getSameTreeItem(TreeItem item, Tree tree){
		TreeItem returnItem = null;
		for(TreeItem searchItem : tree.getItems()){
			if(returnItem == null){
				returnItem = idTaken(searchItem, item);
			}
		}
		if(returnItem == null){
			if(((String[])item.getData())[2].equals("-1")){
				returnItem = new TreeItem(tree, SWT.NONE);

			}else{
				returnItem = new TreeItem(getSameTreeItem(item.getParentItem(), tree), SWT.NONE);
			}
			returnItem.setText(new String[]{item.getText()});
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
	private static TreeItem idTaken(TreeItem searchItem, TreeItem toFindItem) {
		if(searchItem.getData().equals(toFindItem.getData())){
			return searchItem;
		}else{
			for(TreeItem child : searchItem.getItems()){
				return idTaken(child, toFindItem);
			}
			return null;
		}
	}
	
	/**
	 * Sets the item an all of its child expanded
	 * @param item to expand
	 */
	private static void expandAll(TreeItem item){
		item.setExpanded(true);
		for(TreeItem child : item.getItems()){
			expandAll(child);
		}
	}
	
	/**
	 * deletes the item from the tree and all its parents that dont have more childs
	 * @param deleteItem is the one to delete
	 */
	private static void deleteAllUnneededItems(TreeItem deleteItem){
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
	private static void getPositionItems(ArrayList<TreeItem> items, TreeItem item) {
		if(item.getItems().length > 1){
			getPositionItems(items, item);
		}else{
			items.add(item);
		}
		
	}
}
