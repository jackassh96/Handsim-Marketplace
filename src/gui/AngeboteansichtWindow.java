package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JOptionPane;

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
import org.eclipse.swt.graphics.Image;

public class AngeboteansichtWindow extends Shell {
	
	private final Controller controller = Controller.getInstance();
	private Composite upperContainer, leftUpperContainer, rightUpperContainer, middleContainer, leftMiddleContainer,
	rightMiddleContainer, lowerContainer, leftLowerContainer, rightLowerContainer, middleRightLowerContainer;
	private HashMap<String, String> angebotsInfo;
	private Label unternehmensnameTextLabel, preisTextLabel, zeitLabel, zeitTextLabel, datumLabel, datumTextLabel,
	statusLabel, statusTextLabel, beschreibungLabel, upperRightLowerLabel, lowerRightLowerLabel;
	private Button annehmenButton, ablehnenButton, schließenButton;
	private Text beschreibungText;
	private String angebotsID;
	private Shell parent;

	/**
	 * Creating a window to display the information about an Assignment
	 * @param parent of the shell
	 * @param angebotsID, the assignmentID of the Assignment to display
	 * @throws SQLException
	 * @throws IOException
	 */
	public AngeboteansichtWindow(final Shell parent ,final String angebotsID) throws SQLException, IOException {
		super(parent, SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		this.angebotsID = angebotsID;
		this.parent = parent;

		angebotsInfo = controller.genereateOfferHashMap(angebotsID);
		
		upperContainer = new Composite(this, SWT.NONE);
		upperContainer.setLayoutData(BorderLayout.NORTH);
		upperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		leftUpperContainer = new Composite(upperContainer, SWT.NONE);
		leftUpperContainer.setLayout(new BorderLayout(0, 0));
		
		unternehmensnameTextLabel = new Label(leftUpperContainer, SWT.NONE);
		unternehmensnameTextLabel.setFont(SWTResourceManager.getFont("Calibri", 20, SWT.NORMAL));
		unternehmensnameTextLabel.setLayoutData(BorderLayout.CENTER);
		unternehmensnameTextLabel.setText(angebotsInfo.get("company"));
		
		rightUpperContainer = new Composite(upperContainer, SWT.NONE);
		rightUpperContainer.setLayout(new BorderLayout(0, 0));
		
		preisTextLabel = new Label(rightUpperContainer, SWT.NONE);
		preisTextLabel.setFont(SWTResourceManager.getFont("Calibri", 20, SWT.NORMAL));
		preisTextLabel.setLayoutData(BorderLayout.CENTER);
		preisTextLabel.setText(angebotsInfo.get("price"));
		
		middleContainer = new Composite(this, SWT.NONE);
		middleContainer.setLayoutData(BorderLayout.CENTER);
		middleContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		leftMiddleContainer = new Composite(middleContainer, SWT.NONE);
		leftMiddleContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		zeitLabel = new Label(leftMiddleContainer, SWT.NONE);
		zeitLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		zeitLabel.setText("Benötigte Zeit zur Ausführung:");
		
		zeitTextLabel = new Label(leftMiddleContainer, SWT.NONE);
		zeitTextLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		zeitTextLabel.setText(angebotsInfo.get("amountoftimeneeded"));
		
		datumLabel = new Label(leftMiddleContainer, SWT.NONE);
		datumLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		datumLabel.setText("Datum der Ausführung:");
		
		datumTextLabel = new Label(leftMiddleContainer, SWT.NONE);
		datumTextLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		datumTextLabel.setText(angebotsInfo.get("date"));
		
		statusLabel = new Label(leftMiddleContainer, SWT.NONE);
		statusLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		statusLabel.setText("Status des Angebots:");
		
		statusTextLabel = new Label(leftMiddleContainer, SWT.NONE);
		statusTextLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		statusTextLabel.setText(angebotsInfo.get("status"));
		
		rightMiddleContainer = new Composite(middleContainer, SWT.NONE);
		rightMiddleContainer.setLayout(new BorderLayout(0, 0));
		
		beschreibungLabel = new Label(rightMiddleContainer, SWT.NONE);
		beschreibungLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		beschreibungLabel.setLayoutData(BorderLayout.NORTH);
		beschreibungLabel.setText("Zusätzliche Beschreibung:");
		
		beschreibungText = new Text(rightMiddleContainer, SWT.BORDER);
		beschreibungText.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		beschreibungText.setLayoutData(BorderLayout.CENTER);
		beschreibungText.setText(angebotsInfo.get("description"));
		beschreibungText.setEditable(false);
		
		lowerContainer = new Composite(this, SWT.NONE);
		lowerContainer.setLayoutData(BorderLayout.SOUTH);
		lowerContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		leftLowerContainer = new Composite(lowerContainer, SWT.NONE);
		
		rightLowerContainer = new Composite(lowerContainer, SWT.NONE);
		rightLowerContainer.setLayout(new BorderLayout(0, 0));
		
		upperRightLowerLabel = new Label(rightLowerContainer, SWT.NONE);
		upperRightLowerLabel.setLayoutData(BorderLayout.NORTH);
		
		lowerRightLowerLabel = new Label(rightLowerContainer, SWT.NONE);
		lowerRightLowerLabel.setLayoutData(BorderLayout.SOUTH);
		
		middleRightLowerContainer = new Composite(rightLowerContainer, SWT.NONE);
		middleRightLowerContainer.setLayoutData(BorderLayout.CENTER);
		middleRightLowerContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		if(angebotsInfo.get("status").equals("open")){
			annehmenButton = new Button(middleRightLowerContainer, SWT.NONE);
			annehmenButton.setText("Annehmen");
			annehmenButton.addSelectionListener(getAcceptOfferSelectionAdapter(true));
		
			ablehnenButton = new Button(middleRightLowerContainer, SWT.NONE);
			ablehnenButton.setText("Ablehnen");	
			ablehnenButton.addSelectionListener(getAcceptOfferSelectionAdapter(false));
		}
		
		schließenButton = new Button(middleRightLowerContainer, SWT.NONE);
		schließenButton.setText("Schließen");		
		schließenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((Button)e.getSource()).getShell().dispose();
			}
		});
		
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Angebotsansicht");
		setSize(757, 484);
		this.setImage(new Image(null, ".\\images\\handsimIcon.png"));
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
	 * A Method that returns a SelectionAdapter for an accept or if false for a decline
	 * @param accept if true or decline if false
	 */
	private SelectionAdapter getAcceptOfferSelectionAdapter(final boolean accept){
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					int antwort = 1;
					if(accept){
						antwort = JOptionPane.showOptionDialog(null, "Wenn Sie dieses Angebot annehmen werden alle anderen Angebote abgeleht. Wollen Sie wirklich fortfahren?", "Angebot Annehmen", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Ja","Nein"}, "Ja");
					}else{
						antwort = JOptionPane.showOptionDialog(null, "Wenn Sie dieses Angebot ablehnen kann dies nicht rückgängig gemacht werden. Wollen Sie wirklich fortfahren?", "Angebot Annehmen", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Ja","Nein"}, "Ja");
					}
					if(antwort == 0){
						if(accept){
							controller.acceptOffer(angebotsID);
						}else{
							controller.declineOffer(angebotsID);
						}
						AuftragsansichtWindow parentWindow = (AuftragsansichtWindow) parent;
						parentWindow.updateContent();
						((Button)e.getSource()).getShell().dispose();
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, e1, "Fehler!", 2);
				}
			}
		};
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
