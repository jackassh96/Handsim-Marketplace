package gui;

import java.io.IOException;
import java.sql.SQLException;

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
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import processing.Controller;
import processing.data.Category;
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
		weiterButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((Button)e.getSource()).getShell().setVisible(false);
				AuftragErstellenInfoWindow nextPage = new AuftragErstellenInfoWindow(((Button)e.getSource()).getShell());
			}
		});
		weiterButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		weiterButton.setText("Weiter");
		
		Button abbrechenButton = new Button(middleRightLowContainer, SWT.NONE);
		abbrechenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((Button)e.getSource()).getShell().dispose();
			}
		});
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
//		TreeColumn tc = new TreeColumn(InputTree, SWT.NORMAL);
		
		//TODO userdata
		controller = controller.init(null, new dbHandler());
		controller.buildTreeFromMajorCategories(inputTree);
//		test.builTreeWithPositons("12345", InputTree);
//		for (TreeItem t : test.getPositionTreeList()) {
//			System.out.println(t.getText());
//		}
//		
//		for (Category t : test.getNeededCategoryList()) {
//			System.out.println(t.getTitle());
//		}
		
		final Tree outputTree = new Tree(middleMainContainer, SWT.BORDER);
		outputTree.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		einfügenButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selectedItems = inputTree.getSelection();
				for(TreeItem item : selectedItems){
					System.out.println(item.getText());
				}
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
