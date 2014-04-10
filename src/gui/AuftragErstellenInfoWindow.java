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

public class AuftragErstellenInfoWindow extends Shell {
	private Text TitelText;
	private Text BeschreibungText;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			AuftragErstellenInfoWindow shell = new AuftragErstellenInfoWindow(
					display);
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
	public AuftragErstellenInfoWindow(Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new BorderLayout(0, 0));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(BorderLayout.NORTH);
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.CENTER);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Tree tree = new Tree(composite_1, SWT.BORDER);
		
		Composite composite_4 = new Composite(composite_1, SWT.NONE);
		composite_4.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label TitelLabel = new Label(composite_4, SWT.NONE);
		TitelLabel.setText("Titel");
		
		TitelText = new Text(composite_4, SWT.BORDER);
		
		Label BeschreibungLabel = new Label(composite_4, SWT.NONE);
		BeschreibungLabel.setText("Beschreibung");
		
		BeschreibungText = new Text(composite_4, SWT.BORDER);
		
		Label lblNewLabel_2 = new Label(composite_4, SWT.NONE);
		lblNewLabel_2.setText("DueDate");
		
		DateTime dateTime = new DateTime(composite_4, SWT.BORDER | SWT.DROP_DOWN | SWT.LONG);
		
		Composite composite_2 = new Composite(this, SWT.NONE);
		composite_2.setLayoutData(BorderLayout.SOUTH);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Auftrag Erstellen");
		setSize(817, 668);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
