import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Register extends JInternalFrame implements ActionListener {

	JTextField txtName, txtEmail;
	JPasswordField txtPassword, txtConfirmPassword;
	JTextArea txtAddress;
	JComboBox<String> txtDay, txtMonth, txtYear;
	JRadioButton btnMale, btnFemale;
	ButtonGroup groupGender;
	JPanel pnlTitleRegister, pnlFormRegister, pnlDate, pnlGender, pnlBtnRegister;
	JButton btnRegister;

	String days[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
			"19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
	String months[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
	String years[] = { "1975", "1976", "1977", "1988", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986",
			"1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999",
			"2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012",
			"2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021" };

	public void initialize() {

		// Input
		txtName = new JTextField();
		txtEmail = new JTextField();
		txtPassword = new JPasswordField();
		txtConfirmPassword = new JPasswordField();
		txtAddress = new JTextArea();
		btnMale = new JRadioButton("Male");
		btnFemale = new JRadioButton("Female");
		txtDay = new JComboBox<String>(days);
		txtMonth = new JComboBox<String>(months);
		txtYear = new JComboBox<String>(years);

		// Panel
		pnlTitleRegister = new JPanel(new BorderLayout());
		pnlFormRegister = new JPanel(new GridLayout(7, 2));
		pnlDate = new JPanel(new GridLayout(1, 3));
		pnlGender = new JPanel(new GridLayout(1, 2));
		pnlBtnRegister = new JPanel(new BorderLayout());

		// Button
		btnRegister = new JButton("Register");
		groupGender = new ButtonGroup();
		groupGender.add(btnMale);
		groupGender.add(btnFemale);

		// Panel Title
		pnlTitleRegister.add(new JLabel("Register"));

		// Panel Form
		pnlFormRegister.add(new JLabel("Name"));
		pnlFormRegister.add(txtName);
		pnlFormRegister.add(new JLabel("Email"));
		pnlFormRegister.add(txtEmail);
		pnlFormRegister.add(new JLabel("Password"));
		pnlFormRegister.add(txtPassword);
		pnlFormRegister.add(new JLabel("Connfirm Password"));
		pnlFormRegister.add(txtConfirmPassword);
		pnlFormRegister.add(new JLabel("Address"));
		pnlFormRegister.add(txtAddress);
		pnlFormRegister.add(new JLabel("Date of Birth"));
		pnlFormRegister.add(pnlDate);
		pnlFormRegister.add(new JLabel("Gender"));
		pnlFormRegister.add(pnlGender);

		// Panel Date
		pnlDate.add(txtDay);
		pnlDate.add(txtMonth);
		pnlDate.add(txtYear);

		// Panel Gender
		pnlGender.add(btnMale);
		pnlGender.add(btnFemale);

		// Panel Button
		pnlBtnRegister.add(btnRegister);

		add(pnlTitleRegister, BorderLayout.NORTH);
		add(pnlFormRegister, BorderLayout.CENTER);
		add(pnlBtnRegister, BorderLayout.SOUTH);
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

	public void insertUserData() {

		String Name = txtName.getText();
		int lenName = Name.length();
		String Email = txtEmail.getText();
		String Password = String.valueOf(txtPassword.getPassword());
		int lenPassword = Password.length();
		String conPassword = String.valueOf(txtConfirmPassword.getPassword());
		String Address = txtAddress.getText();
		String Day = txtDay.getSelectedItem().toString();
		String Month = txtMonth.getSelectedItem().toString();
		String Year = txtYear.getSelectedItem().toString();
		String Date = Day + "-" + Month + "-" + Year;
		String Gender = "";

		try {
			openConnection();
			String query = "INSERT INTO USER(UserName, UserEmail, UserPassword, UserAddress, UserDOB, UserGender) VALUES(?,?,?,?,?,?)";
			ps = connect.prepareStatement(query);

			if (lenName < 5 || lenName > 30) {
				JOptionPane.showMessageDialog(this, "Name must be 5-30 characters", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
			ps.setString(1, Name);

//			if (!Email.contentEquals("@")) {
//				JOptionPane.showMessageDialog(this, "Email Format is Invalid", "Warning", JOptionPane.WARNING_MESSAGE);
//			}
			ps.setString(2, Email);

			if (lenPassword < 5 || lenName > 20) {
				JOptionPane.showMessageDialog(this, "Password must be 5-30 characters", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}

			if (conPassword.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Confirm Password must be same as password", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
			ps.setString(3, Password);

			ps.setString(4, Address);

			ps.setString(5, Date);

			if (btnMale.isSelected()) {
				Gender = "Male";
			} else if (btnFemale.isSelected()) {
				Gender = "Female";
			}
			if (!(btnMale.isSelected() || btnFemale.isSelected())) {
				JOptionPane.showMessageDialog(this, "Gender must be choosen", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			ps.setString(6, Gender);

			ps.executeUpdate();
			closeConnection();

			JOptionPane.showMessageDialog(this, "Insert Success", "Success", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public Register() {
		initialize();

		setTitle("Register");
		setVisible(true);
		setSize(500, 800);
		setClosable(true);
		setResizable(true);

		btnRegister.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRegister) {
			insertUserData();
		}
	}

}
