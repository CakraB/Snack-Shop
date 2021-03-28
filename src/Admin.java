import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Admin extends JFrame implements ActionListener {

	// Menu
	JMenuBar menuBar;
	JMenu menuUser, menuManage;
	JMenuItem itemLogout, itemExit, itemManageSnack, itemManageUser;

	// InternalFrame
	JDesktopPane desktopPane;
	JInternalFrame intManageSnack = null;
	JInternalFrame intManageUser = null;

	public void initialize() {

		// Menu
		menuBar = new JMenuBar();
		menuUser = new JMenu("User");
		menuManage = new JMenu("Manage");

		itemLogout = new JMenuItem("Logout");
		itemExit = new JMenuItem("Exit");
		itemManageSnack = new JMenuItem("Manage Snack");
		itemManageUser = new JMenuItem("Manage User");

		setJMenuBar(menuBar);
		menuBar.add(menuUser);
		menuBar.add(menuManage);

		menuUser.add(itemLogout);
		menuUser.add(itemExit);

		menuManage.add(itemManageSnack);
		menuManage.add(itemManageUser);

		// InternalFrame
		desktopPane = new JDesktopPane();
		add(desktopPane);
	}
	
	public Admin() {
		initialize();

		itemLogout.addActionListener(this);
		itemExit.addActionListener(this);
		itemManageSnack.addActionListener(this);
		itemManageUser.addActionListener(this);

		setTitle("Main Form");
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itemLogout) {

			this.dispose();

		} else if (e.getSource() == itemExit) {
			
			System.exit(EXIT_ON_CLOSE);
			
		} else if (e.getSource() == itemManageSnack) {
			
			if (intManageSnack == null || intManageSnack.isClosed() || intManageUser.isClosed()) {

				intManageSnack = new ManageSnack();
				desktopPane.add(intManageSnack);

			}
			
		} else if (e.getSource() == itemManageUser) {
			
			if (intManageUser == null || intManageUser.isClosed() || intManageSnack.isClosed()) {

				intManageUser = new ManageUser();
				desktopPane.add(intManageUser);

			}
			
		}

	}

}
