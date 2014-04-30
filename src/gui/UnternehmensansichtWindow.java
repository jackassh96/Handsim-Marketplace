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

	/**
	 * Create the shell.
	 * @param display
	 */
	public UnternehmensansichtWindow(String unternehmensID) {
		super(Display.getDefault(), SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		
		Controller controller = Controller.getInstance();
		HashMap<String, String> unternehmensInfo = controller.genereatCompanyHashMap(unternehmensID);
		
		Composite upperContainer = new Composite(this, SWT.NONE);
		upperContainer.setLayoutData(BorderLayout.NORTH);
		upperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite leftUpperContainer = new Composite(upperContainer, SWT.NONE);
		leftUpperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label unternehmensnameLabel = new Label(leftUpperContainer, SWT.NONE);
		unternehmensnameLabel.setFont(SWTResourceManager.getFont("Calibri", 16, SWT.BOLD));
		unternehmensnameLabel.setText(unternehmensInfo.get("name"));
		
		Composite rightUpperContainer = new Composite(upperContainer, SWT.NONE);
		rightUpperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite leftRightUpperContainer = new Composite(rightUpperContainer, SWT.NONE);
		
		Composite rightRightUpperContainer = new Composite(rightUpperContainer, SWT.NONE);
		rightRightUpperContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label unternehmensIDTextLabel = new Label(rightRightUpperContainer, SWT.NONE);
		unternehmensIDTextLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		unternehmensIDTextLabel.setText("Unternehmens ID:");
		
		Label unternehmensIDLabel = new Label(rightRightUpperContainer, SWT.NONE);
		unternehmensIDLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		unternehmensIDLabel.setText(unternehmensID);
		
		Composite middleContainer = new Composite(this, SWT.NONE);
		middleContainer.setLayoutData(BorderLayout.CENTER);
		middleContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite_1 = new Composite(middleContainer, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(composite_1, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label besitzerNameLabel = new Label(composite, SWT.NONE);
		besitzerNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		besitzerNameLabel.setText("Besitzer:");
		
		Label besitzerLabel = new Label(composite, SWT.NONE);
		besitzerLabel.setText(unternehmensInfo.get("owner"));
		
		Label telefonNameLabel = new Label(composite, SWT.NONE);
		telefonNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		telefonNameLabel.setText("Telefon:");
		
		Label telefonLabel = new Label(composite, SWT.NONE);
		telefonLabel.setText(unternehmensInfo.get("phone"));
		
		Label emailNameLabel = new Label(composite, SWT.NONE);
		emailNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		emailNameLabel.setText("E-Mail:");
		
		Label emailLabel = new Label(composite, SWT.NONE);
		emailLabel.setText(unternehmensInfo.get("email"));
		
		Composite composite_2 = new Composite(composite_1, SWT.NONE);
		composite_2.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite addresseNameContainer = new Composite(composite_2, SWT.NONE);
		addresseNameContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label straﬂeNameLabel = new Label(addresseNameContainer, SWT.NONE);
		straﬂeNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		straﬂeNameLabel.setText("Straﬂe:");
		
		Label nummerNameLabel = new Label(addresseNameContainer, SWT.NONE);
		nummerNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		nummerNameLabel.setText("Nr.:");
		
		Composite addresseContainer = new Composite(composite_2, SWT.NONE);
		addresseContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label straﬂeLabel = new Label(addresseContainer, SWT.NONE);
		straﬂeLabel.setText(unternehmensInfo.get("street"));
		
		Label nummerLabel = new Label(addresseContainer, SWT.NONE);
		nummerLabel.setText(unternehmensInfo.get("number"));
		
		Label postleitzahlNameLabel = new Label(composite_2, SWT.NONE);
		postleitzahlNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		postleitzahlNameLabel.setText("Postleitzahl:");
		
		Label postleitzahlLabel = new Label(composite_2, SWT.NONE);
		postleitzahlLabel.setText(unternehmensInfo.get("postcode"));
		
		Label beschreibungNameLabel = new Label(composite_2, SWT.NONE);
		beschreibungNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		beschreibungNameLabel.setText("Beschreibung:");
		
		Label beschreibungLabel = new Label(composite_2, SWT.NONE);
		beschreibungLabel.setText(unternehmensInfo.get("description"));
		
		createContents();
		
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

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Unternehmensansicht");
		setSize(753, 427);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
