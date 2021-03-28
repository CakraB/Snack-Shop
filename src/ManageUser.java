import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ManageUser extends JInternalFrame implements ActionListener {

	JScrollPane sp;
	JTable tblUser;
	JPanel pnlForm, pnlTable, pnlUser, pnlBtn, pnlManage, pnlDate, pnlGender;
	JTextField txtName, txtId, txtEmail;
	JButton btnInsert, btnUpdate, btnDelete;
	JPasswordField txtPassword;
	JTextArea txtAddress;
	JComboBox<String> txtDay, txtMonth, txtYear;
	JComboBox<String> txtRole;
	JRadioButton btnMale, btnFemale;
	ButtonGroup btnGender;
	DefaultTableModel dtm;

	String days[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
			"19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

	String months[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

	String years[] = { "1975", "1976", "1977", "1988", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986",
			"1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999",
			"2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012",
			"2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021" };

	String roles[] = { "User", "Admin" };

	public void initialize() {

		// Table
		tblUser = new JTable();

		// Scroll
		sp = new JScrollPane(tblUser);

		// Panel
		pnlForm = new JPanel(new GridLayout(8, 2));
		pnlTable = new JPanel(new GridLayout(2, 0));
		pnlUser = new JPanel(new GridLayout(2, 0));
		pnlBtn = new JPanel(new GridLayout(3, 0));
		pnlManage = new JPanel(new GridLayout(0, 2));
		pnlDate = new JPanel(new GridLayout(1, 3));
		pnlGender = new JPanel(new GridLayout(1, 2));

		// Textfield
		txtName = new JTextField();
		txtId = new JTextField();
		txtEmail = new JTextField();

		// TextArea
		txtAddress = new JTextArea();

		// RadioButton
		btnMale = new JRadioButton("Male");
		btnFemale = new JRadioButton("Female");

		// Password
		txtPassword = new JPasswordField();

		// Combo Box
		txtDay = new JComboBox<String>(days);
		txtMonth = new JComboBox<String>(months);
		txtYear = new JComboBox<String>(years);
		txtRole = new JComboBox<String>(roles);

		// Button
		btnInsert = new JButton("Insert");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");

		pnlDate.add(txtDay);
		pnlDate.add(txtMonth);
		pnlDate.add(txtYear);

		pnlGender.add(btnMale);
		pnlGender.add(btnFemale);

		pnlTable.add(new JLabel("User"));
		pnlTable.add(sp);

		pnlForm.add(new JLabel("Id"));
		pnlForm.add(txtId);
		pnlForm.add(new JLabel("Name"));
		pnlForm.add(txtName);
		pnlForm.add(new JLabel("Email"));
		pnlForm.add(txtEmail);
		pnlForm.add(new JLabel("Password"));
		pnlForm.add(txtPassword);
		pnlForm.add(new JLabel("Role"));
		pnlForm.add(txtRole);
		pnlForm.add(new JLabel("Address"));
		pnlForm.add(txtAddress);
		pnlForm.add(new JLabel("DOB"));
		pnlForm.add(pnlDate);
		pnlForm.add(new JLabel("Gender"));
		pnlForm.add(pnlGender);

		pnlBtn.add(btnInsert);
		pnlBtn.add(btnUpdate);
		pnlBtn.add(btnDelete);

		pnlUser.add(pnlForm);
		pnlUser.add(pnlBtn);

		pnlManage.add(pnlTable);
		pnlManage.add(pnlUser);

		add(pnlManage);

	}

	Connection connect;
	PreparedStatement ps, pst;
	ResultSet rs, rst;

	public void openConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost/project_bad_lab", "root", "");
	}

	public void closeConnection() throws Exception {
		ps.close();
		pst.close();
		connect.close();
	}

	public void loadDataUser() {
		try {
			openConnection();
			String query = "SELECT * FROM USER";
			ps = connect.prepareStatement(query);
			rs = ps.executeQuery();

			Vector<Vector<Object>> tableData = new Vector<>();
			Vector<String> tableDataHeader = new Vector<>();
			tableDataHeader.add("Id");
			tableDataHeader.add("Name");
			tableDataHeader.add("Password");
			tableDataHeader.add("Date Of Birth");
			tableDataHeader.add("Email");
			tableDataHeader.add("Address");
			tableDataHeader.add("Role");
			tableDataHeader.add("Gender");

			while (rs.next()) {
				Vector<Object> data = new Vector<>();

				int Id = rs.getInt(1);
				String Name = rs.getString(2);
				String Password = rs.getString(3);
				String Date = rs.getString(4);
				String Email = rs.getString(5);
				String Address = rs.getString(6);
				String Role = rs.getString(7);
				String Gender = rs.getString(8);

				data.add(Id);
				data.add(Name);
				data.add(Password);
				data.add(Date);
				data.add(Email);
				data.add(Address);
				data.add(Role);
				data.add(Gender);

				tableData.add(data);
			}

			DefaultTableModel dtm = new DefaultTableModel(tableData, tableDataHeader);
			tblUser.setModel(dtm);
			closeConnection();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertDataUser() {

		String Name = txtName.getText();
		int lenName = Name.length();
		String Email = txtEmail.getText();
		String Password = String.valueOf(txtPassword.getPassword());
		int lenPassword = Password.length();
		String Address = txtAddress.getText();
		String Day = txtDay.getSelectedItem().toString();
		String Month = txtMonth.getSelectedItem().toString();
		String Year = txtYear.getSelectedItem().toString();
		String Date = Day + "-" + Month + "-" + Year;
		String Gender = "";

		try {
			openConnection();
			pst = connect.prepareStatement("SELECT MAX(UserId)+1 FROM USER");
			rst = pst.executeQuery();
			String UserId = "";
			while (rst.next()) {
				UserId = rst.getString(1);
			}

			String query = "INSERT INTO USER(UserId, UserName, UserPassword, UserDOB, UserEmail, UserAddress,  RoleId, UserGender) VALUES(?,?,?,?,?,?,?)";
			ps = connect.prepareStatement(query);

			ps.setString(1, UserId.toString());

			if (lenName < 5 || lenName > 30) {
				JOptionPane.showMessageDialog(this, "Name must be 5-30 characters", "Warning",
						JOptionPane.WARNING_MESSAGE);
			} else {
				ps.setString(2, Name);
			}

			if (lenPassword < 5 || lenPassword > 20) {
				JOptionPane.showMessageDialog(this, "Password must be 5-20 characters", "Warning",
						JOptionPane.WARNING_MESSAGE);
			} else {
				ps.setString(3, Password);
			}

			ps.setString(4, Date);

			ps.setString(5, Email);

			ps.setString(6, Address);

			ps.setString(7, String.valueOf(txtRole.getSelectedItem()));

//			if (txtRole.getSelectedIndex() == 0) {
//				
//			} else if (txtRole.getSelectedIndex() == 1) {
//
//			}

			if (btnMale.isSelected()) {
				Gender = "Male";
			} else if (btnFemale.isSelected()) {
				Gender = "Female";
			} else if (!(btnMale.isSelected() || btnFemale.isSelected())) {
				JOptionPane.showMessageDialog(this, "Gender must be choosen", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			ps.setString(8, Gender);

			ps.executeUpdate();
			closeConnection();

			JOptionPane.showMessageDialog(this, "Insert Success", "Success", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateDataUser() {

		String UserId = txtId.getText();
		String Name = txtName.getText();
		int lenName = Name.length();
		String Email = txtEmail.getText();
		String Password = String.valueOf(txtPassword.getPassword());
		int lenPassword = Password.length();
		String Role = txtRole.getSelectedItem().toString();
		String Address = txtAddress.getText();
		String Day = txtDay.getSelectedItem().toString();
		String Month = txtMonth.getSelectedItem().toString();
		String Year = txtYear.getSelectedItem().toString();
		String Date = Day + "-" + Month + "-" + Year;
		String Gender = "";

		try {
			openConnection();
			String query = "UPDATE SNACK SET RoleId=?, UserName=?, UserGender=?, UserDOB=?, UserAddress=?, UserEmail=?, UserPassword=? WHERE UserId= ?";
			ps = connect.prepareStatement(query);

			ps.setString(1, Role);

			if (lenName < 5 || lenName > 30) {
				JOptionPane.showMessageDialog(this, "Name must be 5-30 characters", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
			ps.setString(2, Name);

			if (btnMale.isSelected()) {
				Gender = "Male";
			} else if (btnFemale.isSelected()) {
				Gender = "Female";
			}
			if (!(btnMale.isSelected() || btnFemale.isSelected())) {
				JOptionPane.showMessageDialog(this, "Gender must be choosen", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			ps.setString(3, Gender);

			ps.setString(4, Date);

			ps.setString(5, Address);

			ps.setString(6, Email);

			if (lenPassword < 5 || lenPassword > 20) {
				JOptionPane.showMessageDialog(this, "Password must be 5-20 characters", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
			ps.setString(7, Password);

			if (UserId.equals("")) {
				JOptionPane.showMessageDialog(this, "User Must be Choosen", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			ps.setString(8, UserId);

			JOptionPane.showMessageDialog(this, "Update Success", "Success", JOptionPane.INFORMATION_MESSAGE);

			ps.executeUpdate();
			closeConnection();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteDataUser() {

		int Id = Integer.parseInt(txtId.getText());

		try {
			openConnection();
			String query = "DELETE FROM USER WHERE UserId = ?";
			ps = connect.prepareStatement(query);
			if (Id <= 0) {
				JOptionPane.showMessageDialog(this, "User Must be Choosen", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			ps.setInt(1, Id);
			ps.executeUpdate();
			closeConnection();

			JOptionPane.showMessageDialog(this, "Delete Success", "Success", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public ManageUser() {
		initialize();

		setTitle("Manage User");
		setVisible(true);
		setSize(1000, 600);
		setClosable(true);
		setResizable(true);

		loadDataUser();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnInsert) {
			insertDataUser();
			loadDataUser();
		} else if (e.getSource() == btnUpdate) {
			updateDataUser();
			loadDataUser();
		} else if (e.getSource() == btnDelete) {
			deleteDataUser();
			loadDataUser();
		}
	}

}
