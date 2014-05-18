package gui;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import processing.Controller;

public class UnternehmensansichtWindow extends Shell {

	private final Controller controller = Controller.getInstance();
	private HashMap<String, String> unternehmensInfo;
	private Composite upperContainer, leftUpperContainer, rightUpperContainer, leftRightUpperContainer, 
	rightRightUpperContainer, middleContainer, LeftMiddleContainer, RightMiddleContainer, addresseNameContainer,
	addresseContainer;
	private Label unternehmensnameLabel, unternehmensIDTextLabel, unternehmensIDLabel, besitzerNameLabel, besitzerLabel,
	telefonNameLabel, telefonLabel, emailNameLabel, emailLabel, straﬂeNameLabel, nummerNameLabel, straﬂeLabel,
	nummerLabel, postleitzahlNameLabel, postleitzahlLabel, beschreibungNameLabel, beschreibungLabel;
	
	/**
	 * Create the shell.
	 * @param display
	 */
	public UnternehmensansichtWindow(String unternehmensID) {
		super(Display.getDefault(), SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		
		unternehmensInfo = controller.genereatCompanyHashMap(unternehmensID);
		
		upperContainer = new Composite(this, SWT.NONE);
		upperContainer.setLayoutData(BorderLayout.NORTH);
		upperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		leftUpperContainer = new Composite(upperContainer, SWT.NONE);
		leftUpperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		unternehmensnameLabel = new Label(leftUpperContainer, SWT.NONE);
		unternehmensnameLabel.setFont(SWTResourceManager.getFont("Calibri", 16, SWT.BOLD));
		unternehmensnameLabel.setText(unternehmensInfo.get("name"));
		
		rightUpperContainer = new Composite(upperContainer, SWT.NONE);
		rightUpperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		leftRightUpperContainer = new Composite(rightUpperContainer, SWT.NONE);
		
		rightRightUpperContainer = new Composite(rightUpperContainer, SWT.NONE);
		rightRightUpperContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		unternehmensIDTextLabel = new Label(rightRightUpperContainer, SWT.NONE);
		unternehmensIDTextLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		unternehmensIDTextLabel.setText("Unternehmens ID:");
		
		unternehmensIDLabel = new Label(rightRightUpperContainer, SWT.NONE);
		unternehmensIDLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		unternehmensIDLabel.setText(unternehmensID);
		
		middleContainer = new Composite(this, SWT.NONE);
		middleContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		LeftMiddleContainer = new Composite(middleContainer, SWT.NONE);
		LeftMiddleContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		besitzerNameLabel = new Label(LeftMiddleContainer, SWT.NONE);
		besitzerNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		besitzerNameLabel.setText("Besitzer:");
		
		besitzerLabel = new Label(LeftMiddleContainer, SWT.NONE);
		besitzerLabel.setText(unternehmensInfo.get("owner"));
		
		telefonNameLabel = new Label(LeftMiddleContainer, SWT.NONE);
		telefonNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		telefonNameLabel.setText("Telefon:");
		
		telefonLabel = new Label(LeftMiddleContainer, SWT.NONE);
		telefonLabel.setText(unternehmensInfo.get("phone"));
		
		emailNameLabel = new Label(LeftMiddleContainer, SWT.NONE);
		emailNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		emailNameLabel.setText("E-Mail:");
		
		emailLabel = new Label(LeftMiddleContainer, SWT.NONE);
		emailLabel.setText(unternehmensInfo.get("email"));
		
		RightMiddleContainer = new Composite(middleContainer, SWT.NONE);
		RightMiddleContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		addresseNameContainer = new Composite(RightMiddleContainer, SWT.NONE);
		addresseNameContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		straﬂeNameLabel = new Label(addresseNameContainer, SWT.NONE);
		straﬂeNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		straﬂeNameLabel.setText("Straﬂe:");
		
		nummerNameLabel = new Label(addresseNameContainer, SWT.NONE);
		nummerNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		nummerNameLabel.setText("Nr.:");
		
		addresseContainer = new Composite(RightMiddleContainer, SWT.NONE);
		addresseContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		straﬂeLabel = new Label(addresseContainer, SWT.NONE);
		straﬂeLabel.setText(unternehmensInfo.get("street"));
		
		nummerLabel = new Label(addresseContainer, SWT.NONE);
		nummerLabel.setText(unternehmensInfo.get("number"));
		
		postleitzahlNameLabel = new Label(RightMiddleContainer, SWT.NONE);
		postleitzahlNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		postleitzahlNameLabel.setText("Postleitzahl:");
		
		postleitzahlLabel = new Label(RightMiddleContainer, SWT.NONE);
		postleitzahlLabel.setText(unternehmensInfo.get("postcode"));
		
		beschreibungNameLabel = new Label(RightMiddleContainer, SWT.NONE);
		beschreibungNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		beschreibungNameLabel.setText("Beschreibung:");
		
		beschreibungLabel = new Label(RightMiddleContainer, SWT.NONE);
		beschreibungLabel.setText(unternehmensInfo.get("description"));
		
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Unternehmensansicht");
		setSize(753, 427);
		this.setImage(new Image(null, ".\\images\\handsimIcon.png"));
		try {
			Display display = Display.getDefault();
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

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
