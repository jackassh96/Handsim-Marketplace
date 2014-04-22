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

import processing.Controller;

public class AuftragsansichtWindow extends Shell {
	private Text statusText;
	private Table angeboteTable;
	private DateTime erstelldatumField;

	/**
	 * Create the shell.
	 */
	public AuftragsansichtWindow(String assignmentID) {
		super(Display.getDefault(), SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		
		Controller controller = Controller.getInstance();
		controller.searchForID(assignmentID);//TODO sollte nicht Assignment kennen
		
		Composite upperContainer = new Composite(this, SWT.NONE);
		upperContainer.setLayoutData(BorderLayout.NORTH);
		upperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite leftUpperContainer = new Composite(upperContainer, SWT.NONE);
		leftUpperContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label upperLeftUpperLabel = new Label(leftUpperContainer, SWT.NONE);
		
		Label leistungenLabel = new Label(leftUpperContainer, SWT.NONE);
		leistungenLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		leistungenLabel.setText("\tEnthaltene Leistungen");
		
		Composite rightUpperContainer = new Composite(upperContainer, SWT.NONE);
		
		Composite mainContainer = new Composite(this, SWT.NONE);
		mainContainer.setLayoutData(BorderLayout.CENTER);
		mainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite upperMainContainer = new Composite(mainContainer, SWT.NONE);
		upperMainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Tree auftragsTree = new Tree(upperMainContainer, SWT.BORDER);
		
		Composite middleUpperMainContainer = new Composite(upperMainContainer, SWT.NONE);
		middleUpperMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite upperRightMainContainer = new Composite(middleUpperMainContainer, SWT.NONE);
		upperRightMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label titelLabel = new Label(upperRightMainContainer, SWT.NONE);
		titelLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		titelLabel.setText("Titel des Auftrags");
		
		Text titelText = new Text(upperRightMainContainer, SWT.BORDER);
		titelText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Label datumLabel = new Label(upperRightMainContainer, SWT.NONE);
		datumLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		datumLabel.setText("Datum der Ausf¸hrung");
		
		DateTime dateField = new DateTime(upperRightMainContainer, SWT.BORDER | SWT.DROP_DOWN | SWT.LONG);
		dateField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Label beschreibungLabel = new Label(upperRightMainContainer, SWT.NONE);
		beschreibungLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		beschreibungLabel.setText("Zus‰tzliche Beschreibungen");
		
		Text beschreibungText = new Text(middleUpperMainContainer, SWT.BORDER);
		beschreibungText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Composite rightUpperMainContainer = new Composite(upperMainContainer, SWT.NONE);
		rightUpperMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite upperRightUpperMainContainer = new Composite(rightUpperMainContainer, SWT.NONE);
		upperRightUpperMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label erstelldatumLabel = new Label(upperRightUpperMainContainer, SWT.NONE);
		erstelldatumLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		erstelldatumLabel.setText("Erstelldatum");
		
		erstelldatumField = new DateTime(upperRightUpperMainContainer, SWT.BORDER | SWT.LONG);
		erstelldatumField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Label ausschreibungsendeLabel = new Label(upperRightUpperMainContainer, SWT.NONE);
		ausschreibungsendeLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		ausschreibungsendeLabel.setText("Ausschreibungsende");
		
		DateTime ausschreibungsendeField = new DateTime(upperRightUpperMainContainer, SWT.BORDER | SWT.LONG);
		ausschreibungsendeField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Label statusLabel = new Label(upperRightUpperMainContainer, SWT.NONE);
		statusLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		statusLabel.setText("Status");
		
		Composite lowerRightUpperMainContainer = new Composite(rightUpperMainContainer, SWT.NONE);
		lowerRightUpperMainContainer.setLayout(new BorderLayout(0, 0));
		
		statusText = new Text(lowerRightUpperMainContainer, SWT.BORDER);
		statusText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		statusText.setLayoutData(BorderLayout.NORTH);
		
		Composite lowerMainContainer = new Composite(mainContainer, SWT.NONE);
		lowerMainContainer.setLayout(new BorderLayout(0, 0));
		
		Label angeboteLabel = new Label(lowerMainContainer, SWT.NONE);
		angeboteLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		angeboteLabel.setLayoutData(BorderLayout.NORTH);
		angeboteLabel.setText("\tErhaltene Angebote");
		
		angeboteTable = new Table(lowerMainContainer, SWT.BORDER | SWT.FULL_SELECTION);
		angeboteTable.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		angeboteTable.setLayoutData(BorderLayout.CENTER);
		angeboteTable.setHeaderVisible(true);
		angeboteTable.setLinesVisible(true);
		
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
		
		Button bearbeitenButton = new Button(middleLeftLowContainer, SWT.NONE);
		bearbeitenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		bearbeitenButton.setText("Bearbeiten");
		
		Button schlieﬂenButton = new Button(middleLeftLowContainer, SWT.NONE);
		schlieﬂenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((Button)e.getSource()).getShell().dispose();
			}
		});
		schlieﬂenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		schlieﬂenButton.setText("Schlie\u00DFen");
		
		Label lowerLeftLowLabel = new Label(leftLowContainer, SWT.NONE);
		lowerLeftLowLabel.setLayoutData(BorderLayout.SOUTH);
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
