import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Main extends JFrame implements ActionListener {

	// Menu
	JMenuBar menuBar;
	JMenu menuUser;
	JMenuItem itemLogin, itemRegister, itemExit, itemLogout, itemManageStock, itemManageUser;
	JPanel pnlBackground;

	// InternalFrame
	JDesktopPane desktopPane;
	JInternalFrame intLoginFrame = null;
	JInternalFrame intRegisterFrame = null;

	BufferedImage imgBackground;

	public void initialize() {

		// Menu
		menuBar = new JMenuBar();
		menuUser = new JMenu("User");

		itemLogin = new JMenuItem("Login");
		itemRegister = new JMenuItem("Register");
		itemExit = new JMenuItem("Exit");

		setJMenuBar(menuBar);
		menuBar.add(menuUser);

		menuUser.add(itemLogin);
		menuUser.add(itemRegister);
		menuUser.add(itemExit);

		// InternalFrame
		desktopPane = new JDesktopPane();
		add(desktopPane);
	}

	public Main() {
		initialize();

		itemLogin.addActionListener(this);
		itemRegister.addActionListener(this);
		itemExit.addActionListener(this);

		setTitle("Main Form");
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itemLogin) {
			if (intLoginFrame == null || intLoginFrame.isClosed() || intRegisterFrame.isClosed()) {

				intLoginFrame = new Login();
				desktopPane.add(intLoginFrame);

			}

		} else if (e.getSource() == itemRegister) {
			if (intRegisterFrame == null || intRegisterFrame.isClosed() || intLoginFrame.isClosed()) {

				intRegisterFrame = new Register();
				desktopPane.add(intRegisterFrame);

			}

		} else if (e.getSource() == itemExit) {

			System.exit(EXIT_ON_CLOSE);

		}
	}

}
