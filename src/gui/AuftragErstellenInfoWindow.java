package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import processing.Controller;

public class AuftragErstellenInfoWindow extends Shell {
	private Text titelText;
	private Text beschreibungText;
	private Shell previousWindow;

	/**
	 * Create the shell.
	 * @param display
	 */
	public AuftragErstellenInfoWindow(Shell previousPage) {
		super(Display.getDefault(), SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		previousWindow = previousPage;
		
		this.setLocation(previousWindow.getLocation());
		
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
		
		Tree auftragsTree = new Tree(mainContainer, SWT.BORDER);
		
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
		
		DateTime dateField = new DateTime(upperRightMainContainer, SWT.BORDER | SWT.DROP_DOWN | SWT.LONG);
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
		zurückButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AuftragErstellenInfoWindow window =(AuftragErstellenInfoWindow) ((Button)e.getSource()).getShell();
				window.dispose();
				window.getPreviousWindow().open();
			}
		});
		zurückButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		zurückButton.setText("Zurück");
		
		Button erstellenButton = new Button(middleLeftLowContainer, SWT.NONE);
		erstellenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		erstellenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		erstellenButton.setText("Erstellen");
		
		Button abbrechenButton = new Button(middleLeftLowContainer, SWT.NONE);
		abbrechenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AuftragErstellenInfoWindow window =(AuftragErstellenInfoWindow) ((Button)e.getSource()).getShell();
				window.dispose();
				window.getPreviousWindow().dispose();
			}
		});
		abbrechenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		abbrechenButton.setText("Abbrechen");
		
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
	
	public Shell getPreviousWindow(){
		return previousWindow;
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
