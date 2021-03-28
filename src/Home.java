import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Home extends JFrame implements ActionListener {

	// Menu
	JMenuBar menuBar;
	JMenu menuUser, menuBuySnack, menuHistoryTransaction;
	JMenuItem itemLogout, itemExit, itemBuySnack, itemHistoryTransaction;

	// InternalFrame
	JDesktopPane desktopPane;
	JInternalFrame intBuySnack = null;
	JInternalFrame intHistoryTransaction = null;

	public void initialize() {

		// Menu
		menuBar = new JMenuBar();
		menuUser = new JMenu("User");
		menuBuySnack = new JMenu("Buy");
		menuHistoryTransaction = new JMenu("Transaction");

		itemLogout = new JMenuItem("Logout");
		itemExit = new JMenuItem("Exit");
		itemBuySnack = new JMenuItem("Buy Snack");
		itemHistoryTransaction = new JMenuItem("History Transaction");

		setJMenuBar(menuBar);

		menuBar.add(menuUser);
		menuBar.add(menuBuySnack);
		menuBar.add(menuHistoryTransaction);

		menuUser.add(itemLogout);
		menuUser.add(itemExit);

		menuBuySnack.add(itemBuySnack);
		menuHistoryTransaction.add(itemHistoryTransaction);

		// InternalFrame
		desktopPane = new JDesktopPane();
		add(desktopPane);
	}

	public Home() {
		initialize();

		setTitle("Main Form");
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		itemLogout.addActionListener(this);
		itemExit.addActionListener(this);
		itemBuySnack.addActionListener(this);
		itemHistoryTransaction.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itemBuySnack) {

			if (intBuySnack == null || intBuySnack.isClosed() || intHistoryTransaction.isClosed()) {

				intBuySnack = new BuySnack();
				desktopPane.add(intBuySnack);

			}

		} else if (e.getSource() == itemHistoryTransaction) {

			if (intHistoryTransaction == null || intHistoryTransaction.isClosed() || intBuySnack.isClosed()) {

				intHistoryTransaction = new HistoryTransaction();
				desktopPane.add(intHistoryTransaction);

			}

		} else if (e.getSource() == itemLogout) {

			this.dispose();

		} else if (e.getSource() == itemExit) {

			System.exit(EXIT_ON_CLOSE);

		}
	}

}
