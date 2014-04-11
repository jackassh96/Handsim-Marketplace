package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import swing2swt.layout.BorderLayout;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;

public class AuftragsansichtWindow extends Shell {
	private Text StatusText;
	private Table AngeboteTable;
	private DateTime ErstelldatumField;

	/**
	 * Create the shell.
	 */
	public AuftragsansichtWindow() {
		super(Display.getDefault(), SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		
		Composite UpperContainer = new Composite(this, SWT.NONE);
		UpperContainer.setLayoutData(BorderLayout.NORTH);
		UpperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite LeftUpperContainer = new Composite(UpperContainer, SWT.NONE);
		LeftUpperContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label UpperLeftUpperLabel = new Label(LeftUpperContainer, SWT.NONE);
		
		Label LeistungenLabel = new Label(LeftUpperContainer, SWT.NONE);
		LeistungenLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		LeistungenLabel.setText("\tEnthaltene Leistungen");
		
		Composite RightUpperContainer = new Composite(UpperContainer, SWT.NONE);
		
		Composite MainContainer = new Composite(this, SWT.NONE);
		MainContainer.setLayoutData(BorderLayout.CENTER);
		MainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite UpperMainContainer = new Composite(MainContainer, SWT.NONE);
		UpperMainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Tree AuftragsTree = new Tree(UpperMainContainer, SWT.BORDER);
		
		Composite MiddleUpperMainContainer = new Composite(UpperMainContainer, SWT.NONE);
		MiddleUpperMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite UpperRightMainContainer = new Composite(MiddleUpperMainContainer, SWT.NONE);
		UpperRightMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label TitelLabel = new Label(UpperRightMainContainer, SWT.NONE);
		TitelLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		TitelLabel.setText("Titel des Auftrags");
		
		Text TitelText = new Text(UpperRightMainContainer, SWT.BORDER);
		TitelText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Label DatumLabel = new Label(UpperRightMainContainer, SWT.NONE);
		DatumLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		DatumLabel.setText("Datum der Ausf¸hrung");
		
		DateTime DateField = new DateTime(UpperRightMainContainer, SWT.BORDER | SWT.DROP_DOWN | SWT.LONG);
		DateField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Label BeschreibungLabel = new Label(UpperRightMainContainer, SWT.NONE);
		BeschreibungLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		BeschreibungLabel.setText("Zus‰tzliche Beschreibungen");
		
		Text BeschreibungText = new Text(MiddleUpperMainContainer, SWT.BORDER);
		BeschreibungText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Composite RightUpperMainContainer = new Composite(UpperMainContainer, SWT.NONE);
		RightUpperMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite UpperRightUpperMainContainer = new Composite(RightUpperMainContainer, SWT.NONE);
		UpperRightUpperMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label ErstelldatumLabel = new Label(UpperRightUpperMainContainer, SWT.NONE);
		ErstelldatumLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		ErstelldatumLabel.setText("Erstelldatum");
		
		ErstelldatumField = new DateTime(UpperRightUpperMainContainer, SWT.BORDER | SWT.LONG);
		ErstelldatumField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Label AusschreibungsendeLabel = new Label(UpperRightUpperMainContainer, SWT.NONE);
		AusschreibungsendeLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		AusschreibungsendeLabel.setText("Ausschreibungsende");
		
		DateTime AusschreibungsendeField = new DateTime(UpperRightUpperMainContainer, SWT.BORDER | SWT.LONG);
		AusschreibungsendeField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Label StatusLabel = new Label(UpperRightUpperMainContainer, SWT.NONE);
		StatusLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		StatusLabel.setText("Status");
		
		Composite LowerRightUpperMainContainer = new Composite(RightUpperMainContainer, SWT.NONE);
		LowerRightUpperMainContainer.setLayout(new BorderLayout(0, 0));
		
		StatusText = new Text(LowerRightUpperMainContainer, SWT.BORDER);
		StatusText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		StatusText.setLayoutData(BorderLayout.NORTH);
		
		Composite LowerMainContainer = new Composite(MainContainer, SWT.NONE);
		LowerMainContainer.setLayout(new BorderLayout(0, 0));
		
		Label AngeboteLabel = new Label(LowerMainContainer, SWT.NONE);
		AngeboteLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		AngeboteLabel.setLayoutData(BorderLayout.NORTH);
		AngeboteLabel.setText("\tErhaltene Angebote");
		
		AngeboteTable = new Table(LowerMainContainer, SWT.BORDER | SWT.FULL_SELECTION);
		AngeboteTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		AngeboteTable.setLayoutData(BorderLayout.CENTER);
		AngeboteTable.setHeaderVisible(true);
		AngeboteTable.setLinesVisible(true);
		
		Composite LowContainer = new Composite(this, SWT.NONE);
		LowContainer.setLayoutData(BorderLayout.SOUTH);
		LowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite RightLowContainer = new Composite(LowContainer, SWT.NONE);
		
		Composite LeftLowContainer = new Composite(LowContainer, SWT.NONE);
		LeftLowContainer.setLayout(new BorderLayout(0, 0));
		
		Label UpperLeftLowLabel = new Label(LeftLowContainer, SWT.NONE);
		UpperLeftLowLabel.setLayoutData(BorderLayout.NORTH);
		
		Composite MiddleLeftLowContainer = new Composite(LeftLowContainer, SWT.NONE);
		MiddleLeftLowContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button BearbeitenButton = new Button(MiddleLeftLowContainer, SWT.NONE);
		BearbeitenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		BearbeitenButton.setText("Bearbeiten");
		
		Button SchlieﬂenButton = new Button(MiddleLeftLowContainer, SWT.NONE);
		SchlieﬂenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((Button)e.getSource()).getShell().dispose();
			}
		});
		SchlieﬂenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		SchlieﬂenButton.setText("Schlie\u00DFen");
		
		Label LowerLeftLowLabel = new Label(LeftLowContainer, SWT.NONE);
		LowerLeftLowLabel.setLayoutData(BorderLayout.SOUTH);
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
		setText("SWT Application");
		setSize(1280, 720);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
