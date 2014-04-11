package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class UnternehmensansichtWindow extends Shell {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			UnternehmensansichtWindow shell = new UnternehmensansichtWindow(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public UnternehmensansichtWindow(Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		
		Composite UpperContainer = new Composite(this, SWT.NONE);
		UpperContainer.setLayoutData(BorderLayout.NORTH);
		UpperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite LeftUpperContainer = new Composite(UpperContainer, SWT.NONE);
		LeftUpperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label UnternehmensnameLabel = new Label(LeftUpperContainer, SWT.NONE);
		UnternehmensnameLabel.setFont(SWTResourceManager.getFont("Calibri", 16, SWT.BOLD));
		UnternehmensnameLabel.setText("Unternehmensname");
		
		Composite RightUpperContainer = new Composite(UpperContainer, SWT.NONE);
		RightUpperContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite LeftRightUpperContainer = new Composite(RightUpperContainer, SWT.NONE);
		
		Composite RightRightUpperContainer = new Composite(RightUpperContainer, SWT.NONE);
		RightRightUpperContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label UnternehmensIDTextLabel = new Label(RightRightUpperContainer, SWT.NONE);
		UnternehmensIDTextLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		UnternehmensIDTextLabel.setText("Unternehmens ID:");
		
		Label UnternehmensIDLabel = new Label(RightRightUpperContainer, SWT.NONE);
		UnternehmensIDLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		UnternehmensIDLabel.setText("Hier ID");
		
		Composite MiddleContainer = new Composite(this, SWT.NONE);
		MiddleContainer.setLayoutData(BorderLayout.CENTER);
		MiddleContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite_1 = new Composite(MiddleContainer, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(composite_1, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label BesitzerNameLabel = new Label(composite, SWT.NONE);
		BesitzerNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		BesitzerNameLabel.setText("Besitzer:");
		
		Label BesitzerLabel = new Label(composite, SWT.NONE);
		BesitzerLabel.setText("Hier Besitzer");
		
		Label TelefonNameLabel = new Label(composite, SWT.NONE);
		TelefonNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		TelefonNameLabel.setText("Telefon:");
		
		Label TelefonLabel = new Label(composite, SWT.NONE);
		TelefonLabel.setText("Hier Telefon");
		
		Label EmailNameLabel = new Label(composite, SWT.NONE);
		EmailNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		EmailNameLabel.setText("E-Mail:");
		
		Label EmailLabel = new Label(composite, SWT.NONE);
		EmailLabel.setText("Hier E-Mail");
		
		Composite composite_2 = new Composite(composite_1, SWT.NONE);
		composite_2.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite AddresseNameContainer = new Composite(composite_2, SWT.NONE);
		AddresseNameContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label StraﬂeNameLabel = new Label(AddresseNameContainer, SWT.NONE);
		StraﬂeNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		StraﬂeNameLabel.setText("Stra\u00DFe:");
		
		Label NummerNameLabel = new Label(AddresseNameContainer, SWT.NONE);
		NummerNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		NummerNameLabel.setText("Nr.:");
		
		Composite AddresseContainer = new Composite(composite_2, SWT.NONE);
		AddresseContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label StraﬂeLabel = new Label(AddresseContainer, SWT.NONE);
		StraﬂeLabel.setText("Hier Stra\u00DFe");
		
		Label NummerLabel = new Label(AddresseContainer, SWT.NONE);
		NummerLabel.setText("Hier Nummer");
		
		Label PostleitzahlNameLabel = new Label(composite_2, SWT.NONE);
		PostleitzahlNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		PostleitzahlNameLabel.setText("Postleitzahl:");
		
		Label PostleitzahlLabel = new Label(composite_2, SWT.NONE);
		PostleitzahlLabel.setText("Hier Postleitzahl");
		
		Label BeschreibungNameLabel = new Label(composite_2, SWT.NONE);
		BeschreibungNameLabel.setFont(SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL));
		BeschreibungNameLabel.setText("Beschreibung:");
		
		Label BeschreibungLabel = new Label(composite_2, SWT.NONE);
		BeschreibungLabel.setText("Hier Beschreibung");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(753, 427);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
