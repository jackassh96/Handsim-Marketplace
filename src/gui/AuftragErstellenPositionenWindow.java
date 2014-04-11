package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AuftragErstellenPositionenWindow extends Shell {

	/**
	 * Create the shell.
	 * @param display
	 */
	public AuftragErstellenPositionenWindow() {
		super(Display.getDefault(), SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		
		Composite LowContainer = new Composite(this, SWT.NONE);
		LowContainer.setLayoutData(BorderLayout.SOUTH);
		LowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite LeftLowContainer = new Composite(LowContainer, SWT.NONE);
		LeftLowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite RightLowContainer = new Composite(LowContainer, SWT.NONE);
		RightLowContainer.setLayout(new BorderLayout(0, 0));
		
		Label UpperRightLowLabel = new Label(RightLowContainer, SWT.NONE);
		UpperRightLowLabel.setLayoutData(BorderLayout.NORTH);
		
		Label LowerRightLowLabel = new Label(RightLowContainer, SWT.NONE);
		LowerRightLowLabel.setLayoutData(BorderLayout.SOUTH);
		
		Composite MiddleRightLowContainer = new Composite(RightLowContainer, SWT.NONE);
		MiddleRightLowContainer.setLayoutData(BorderLayout.CENTER);
		MiddleRightLowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button WeiterButton = new Button(MiddleRightLowContainer, SWT.NONE);
		WeiterButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((Button)e.getSource()).getShell().setVisible(false);
				AuftragErstellenInfoWindow nextPage = new AuftragErstellenInfoWindow(((Button)e.getSource()).getShell());
			}
		});
		WeiterButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		WeiterButton.setText("Weiter");
		
		Button AbbrechenButton = new Button(MiddleRightLowContainer, SWT.NONE);
		AbbrechenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((Button)e.getSource()).getShell().dispose();
			}
		});
		AbbrechenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		AbbrechenButton.setText("Abbrechen");
		
		Composite UpperContainer = new Composite(this, SWT.NONE);
		UpperContainer.setLayoutData(BorderLayout.NORTH);
		UpperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label UpperLabel = new Label(UpperContainer, SWT.NONE);
		
		Composite MainContainer = new Composite(this, SWT.NONE);
		MainContainer.setLayoutData(BorderLayout.CENTER);
		MainContainer.setLayout(new BorderLayout(0, 0));
		
		Composite UpperMainContainer = new Composite(MainContainer, SWT.NONE);
		UpperMainContainer.setLayoutData(BorderLayout.NORTH);
		UpperMainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite LeftUpperMainContainer = new Composite(UpperMainContainer, SWT.NONE);
		LeftUpperMainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label InputTreeLabel = new Label(LeftUpperMainContainer, SWT.NONE);
		InputTreeLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		InputTreeLabel.setText("M�gliche \nAuftragselemente");
		
		Composite RightLeftUpperMainContainer = new Composite(LeftUpperMainContainer, SWT.NONE);
		RightLeftUpperMainContainer.setLayout(new BorderLayout());
				
		Label LeftMenuLabel = new Label(RightLeftUpperMainContainer, SWT.NONE);
		LeftMenuLabel.setLayoutData(BorderLayout.NORTH);
		
		Composite LowerRightLeftUpperMainContainer = new Composite(RightLeftUpperMainContainer, SWT.NONE);
		LowerRightLeftUpperMainContainer.setLayoutData(BorderLayout.SOUTH);
		LowerRightLeftUpperMainContainer.setLayout(new FillLayout());
				
		Button LeftKlappenButton = new Button(LowerRightLeftUpperMainContainer, SWT.NONE);
		LeftKlappenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		LeftKlappenButton.setText("Klappen");
		
		Button Einf�genButton = new Button(LowerRightLeftUpperMainContainer, SWT.NONE);
		Einf�genButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		Einf�genButton.setText("Einf�gen");
		
		Composite RightUpperMainContainer = new Composite(UpperMainContainer, SWT.NONE);
		RightUpperMainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label OutputTreeLabel = new Label(RightUpperMainContainer, SWT.NONE);
		OutputTreeLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		OutputTreeLabel.setText("Ausgew�hlte \nAuftragselemente");
		
		Composite RightRightUpperMainContainer = new Composite(RightUpperMainContainer, SWT.NONE);
		RightRightUpperMainContainer.setLayout(new BorderLayout());
		
		Label RightMenuLabel = new Label(RightRightUpperMainContainer, SWT.NONE);
		RightMenuLabel.setLayoutData(BorderLayout.NORTH);
		
		Composite LowerRightRightUpperMainContainer = new Composite(RightRightUpperMainContainer, SWT.NONE);
		LowerRightRightUpperMainContainer.setLayoutData(BorderLayout.SOUTH);
		LowerRightRightUpperMainContainer.setLayout(new FillLayout());
		
		Button RightKlappenButton = new Button(LowerRightRightUpperMainContainer, SWT.NONE);
		RightKlappenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		RightKlappenButton.setText("Klappen");
		
		Button L�schenButton = new Button(LowerRightRightUpperMainContainer, SWT.NONE);
		L�schenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		L�schenButton.setText("L�schen");
		
		Composite MiddleMainContainer = new Composite(MainContainer, SWT.NONE);
		MiddleMainContainer.setLayoutData(BorderLayout.CENTER);
		MiddleMainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Tree InputTree = new Tree(MiddleMainContainer, SWT.BORDER);
		InputTree.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Tree OutputTree = new Tree(MiddleMainContainer, SWT.BORDER);
		OutputTree.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
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
