package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;

import processing.Controller;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AngeboteansichtWindow extends Shell {
	private Text beschreibungText;

	/**
	 * Create the shell.
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public AngeboteansichtWindow(String angebotsID) throws SQLException, IOException {
		super(Display.getDefault(), SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		
		Controller controller = Controller.getInstance();
		HashMap<String, String> angebotsInfo = controller.genereateOfferHashMap(null, angebotsID);
		
		Composite upperContainer = new Composite(this, SWT.NONE);
		upperContainer.setLayoutData(BorderLayout.NORTH);
		upperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite leftUpperContainer = new Composite(upperContainer, SWT.NONE);
		leftUpperContainer.setLayout(new BorderLayout(0, 0));
		
		Label unternehmensnamTextLabel = new Label(leftUpperContainer, SWT.NONE);
		unternehmensnamTextLabel.setFont(SWTResourceManager.getFont("Calibri", 20, SWT.NORMAL));
		unternehmensnamTextLabel.setLayoutData(BorderLayout.CENTER);
		unternehmensnamTextLabel.setText(angebotsInfo.get("company"));
		
		Composite rightUpperContainer = new Composite(upperContainer, SWT.NONE);
		rightUpperContainer.setLayout(new BorderLayout(0, 0));
		
		Label preisTextLabel = new Label(rightUpperContainer, SWT.NONE);
		preisTextLabel.setFont(SWTResourceManager.getFont("Calibri", 20, SWT.NORMAL));
		preisTextLabel.setLayoutData(BorderLayout.CENTER);
		preisTextLabel.setText(angebotsInfo.get("price"));
		
		Composite middleContainer = new Composite(this, SWT.NONE);
		middleContainer.setLayoutData(BorderLayout.CENTER);
		middleContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite leftMiddleContainer = new Composite(middleContainer, SWT.NONE);
		leftMiddleContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label zeitLabel = new Label(leftMiddleContainer, SWT.NONE);
		zeitLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		zeitLabel.setText("Benötigte Zeit zur Ausführung:");
		
		Label zeitTextLabel = new Label(leftMiddleContainer, SWT.NONE);
		zeitTextLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		zeitTextLabel.setText(angebotsInfo.get("amountoftimeneeded"));
		
		Label datumLabel = new Label(leftMiddleContainer, SWT.NONE);
		datumLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		datumLabel.setText("Datum der Ausführung:");
		
		Label datumTextLabel = new Label(leftMiddleContainer, SWT.NONE);
		datumTextLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		datumTextLabel.setText(angebotsInfo.get("date"));
		
		Label statusLabel = new Label(leftMiddleContainer, SWT.NONE);
		statusLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		statusLabel.setText("Status des Angebots:");
		
		Label statusTextLabel = new Label(leftMiddleContainer, SWT.NONE);
		statusTextLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		statusTextLabel.setText(angebotsInfo.get("status"));
		
		Composite rightMiddleContainer = new Composite(middleContainer, SWT.NONE);
		rightMiddleContainer.setLayout(new BorderLayout(0, 0));
		
		Label beschreibungLabel = new Label(rightMiddleContainer, SWT.NONE);
		beschreibungLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		beschreibungLabel.setLayoutData(BorderLayout.NORTH);
		beschreibungLabel.setText("Zusätzliche Beschreibung:");
		
		beschreibungText = new Text(rightMiddleContainer, SWT.BORDER);
		beschreibungText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		beschreibungText.setLayoutData(BorderLayout.CENTER);
		beschreibungText.setText(angebotsInfo.get("description"));
		
		Composite lowerContainer = new Composite(this, SWT.NONE);
		lowerContainer.setLayoutData(BorderLayout.SOUTH);
		lowerContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite leftLowerContainer = new Composite(lowerContainer, SWT.NONE);
		
		Composite rightLowerContainer = new Composite(lowerContainer, SWT.NONE);
		rightLowerContainer.setLayout(new BorderLayout(0, 0));
		
		Label upperRightLowerLabel = new Label(rightLowerContainer, SWT.NONE);
		upperRightLowerLabel.setLayoutData(BorderLayout.NORTH);
		
		Label lowerRightLowerLabel = new Label(rightLowerContainer, SWT.NONE);
		lowerRightLowerLabel.setLayoutData(BorderLayout.SOUTH);
		
		Composite middleRightLowerContainer = new Composite(rightLowerContainer, SWT.NONE);
		middleRightLowerContainer.setLayoutData(BorderLayout.CENTER);
		middleRightLowerContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button annehmenButton = new Button(middleRightLowerContainer, SWT.NONE);
		annehmenButton.setText("Annehmen");
		
		Button ablehnenButton = new Button(middleRightLowerContainer, SWT.NONE);
		ablehnenButton.setText("Ablehnen");
		
		Button schließenButton = new Button(middleRightLowerContainer, SWT.NONE);
		schließenButton.setText("Schließen");
		
		annehmenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		
		ablehnenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		
		schließenButton.addSelectionListener(new SelectionAdapter() {
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
				if (!Display.getDefault().readAndDispatch()) {
					Display.getDefault().sleep();
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
		setText("Angebotsansicht");
		setSize(757, 484);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
