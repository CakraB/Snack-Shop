import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JInternalFrame implements ActionListener {

	JTextField txtEmail;
	JPasswordField txtPassword;
	JPanel pnlTitleLogin, pnlFormLogin, pnlBtnLogin;
	JButton btnLogin;

	public void initialize() {

		// Input
		txtEmail = new JTextField();
		txtPassword = new JPasswordField();

		// Panel
		pnlTitleLogin = new JPanel(new BorderLayout());
		pnlFormLogin = new JPanel(new GridLayout(2, 2, 5, 5));
		pnlBtnLogin = new JPanel(new BorderLayout());

		// Button Login
		btnLogin = new JButton("Login");

		// Panel Title
		pnlTitleLogin.add(new JLabel("Login"));

		// Panel Form
		pnlFormLogin.add(new JLabel("Email"));
		pnlFormLogin.add(txtEmail);
		pnlFormLogin.add(new JLabel("Password"));
		pnlFormLogin.add(txtPassword);

		// Panel Button
		pnlBtnLogin.add(btnLogin);

		add(pnlTitleLogin, BorderLayout.NORTH);
		add(pnlFormLogin, BorderLayout.CENTER);
		add(pnlBtnLogin, BorderLayout.SOUTH);

	}

	Connection connect;
	PreparedStatement ps;
	ResultSet rs;

	public void openConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost/project_bad_lab", "root", "");
	}

	public void closeConnection() throws Exception {
		ps.close();
		connect.close();
	}

	public void insertLoginData() {

		try {
			openConnection();
			String query = "SELECT RoleId, UserName, UserEmail, UserPassword FROM USER WHERE UserEmail =? AND UserPassword =?";
			ps = connect.prepareStatement(query);

			ps.setString(1, txtEmail.getText());
			ps.setString(2, String.valueOf(txtPassword.getPassword()));

			rs = ps.executeQuery();

			if (rs.next()) {
				String Name = rs.getString("UserName");
				String msg = "" + Name;
				msg += " \n";
				JOptionPane.showMessageDialog(this, "Welcome, " + msg, "Welcome", JOptionPane.INFORMATION_MESSAGE);

				this.dispose();
				int roleId = rs.getInt("RoleId");

				if (roleId == 1) {

					Home userHome = new Home();
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				} else {

					Admin userAdmin = new Admin();
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				}

			} else {
				JOptionPane.showMessageDialog(this, "Invalid Email/Password", "Warning", JOptionPane.WARNING_MESSAGE);
			}

			closeConnection();
			txtEmail.setText("");
			txtPassword.setText("");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	public Login() {
		initialize();

		setTitle("Login");
		setVisible(true);
		setSize(400, 200);
		setClosable(true);
		setResizable(true);

		btnLogin.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) {
			insertLoginData();
		}
	}

}
