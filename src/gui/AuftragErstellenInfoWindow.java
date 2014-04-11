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
	private Text TitelText;
	private Text BeschreibungText;
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
		
		Composite UpperContainer = new Composite(this, SWT.NONE);
		UpperContainer.setLayoutData(BorderLayout.NORTH);
		UpperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite LeftUpperContainer = new Composite(UpperContainer, SWT.NONE);
		LeftUpperContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label UpperLeftUpperLabel = new Label(LeftUpperContainer, SWT.NONE);
		
		Label IhreAuswahlLabel = new Label(LeftUpperContainer, SWT.NONE);
		IhreAuswahlLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		IhreAuswahlLabel.setText("Ihre ausgew�hlten Leistungen");
		
		Composite RightUpperContainer = new Composite(UpperContainer, SWT.NONE);
		
		Composite MainContainer = new Composite(this, SWT.NONE);
		MainContainer.setLayoutData(BorderLayout.CENTER);
		MainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Tree AuftragsTree = new Tree(MainContainer, SWT.BORDER);
		
		Composite RightMainContainer = new Composite(MainContainer, SWT.NONE);
		RightMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite UpperRightMainContainer = new Composite(RightMainContainer, SWT.NONE);
		UpperRightMainContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label TitelLabel = new Label(UpperRightMainContainer, SWT.NONE);
		TitelLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		TitelLabel.setText("Titel des Auftrags");
		
		TitelText = new Text(UpperRightMainContainer, SWT.BORDER);
		TitelText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Label DatumLabel = new Label(UpperRightMainContainer, SWT.NONE);
		DatumLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		DatumLabel.setText("Datum der Ausf�hrung");
		
		DateTime DateField = new DateTime(UpperRightMainContainer, SWT.BORDER | SWT.DROP_DOWN | SWT.LONG);
		DateField.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
		Label BeschreibungLabel = new Label(UpperRightMainContainer, SWT.NONE);
		BeschreibungLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		BeschreibungLabel.setText("Zus�tzliche Beschreibungen");
		
		BeschreibungText = new Text(RightMainContainer, SWT.BORDER);
		BeschreibungText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		
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
		
		Button Zur�ckButton = new Button(MiddleLeftLowContainer, SWT.NONE);
		Zur�ckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AuftragErstellenInfoWindow window =(AuftragErstellenInfoWindow) ((Button)e.getSource()).getShell();
				window.dispose();
				window.getPreviousWindow().open();
			}
		});
		Zur�ckButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		Zur�ckButton.setText("Zur�ck");
		
		Button ErstellenButton = new Button(MiddleLeftLowContainer, SWT.NONE);
		ErstellenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		ErstellenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		ErstellenButton.setText("Erstellen");
		
		Button AbbrechenButton = new Button(MiddleLeftLowContainer, SWT.NONE);
		AbbrechenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((Button)e.getSource()).getShell().dispose();
			}
		});
		AbbrechenButton.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		AbbrechenButton.setText("Abbrechen");
		
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
