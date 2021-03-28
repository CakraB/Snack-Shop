import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ManageSnack extends JInternalFrame implements ActionListener {

	JScrollPane sp;
	JTable tblSnack;
	JPanel pnlForm, pnlTable, pnlSnack, pnlBtn, pnlManage;
	JTextField txtName, txtId, txtPrice;
	JButton btnInsert, btnUpdate, btnDelete;
	DefaultTableModel dtm;

	public void initialize() {

		// Table
		tblSnack = new JTable();

		// Scroll
		sp = new JScrollPane(tblSnack);

		// Panel
		pnlForm = new JPanel(new GridLayout(3, 2));
		pnlTable = new JPanel(new GridLayout(2, 0));
		pnlSnack = new JPanel(new GridLayout(2, 0));
		pnlBtn = new JPanel(new GridLayout(3, 0));
		pnlManage = new JPanel(new GridLayout(0, 2));

		// Textfield
		txtName = new JTextField();
		txtId = new JTextField();
		txtPrice = new JTextField();

		// Button
		btnInsert = new JButton("Insert");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");

		pnlTable.add(new JLabel("Snack"));
		pnlTable.add(sp);

		pnlForm.add(new JLabel("Id"));
		pnlForm.add(txtId);
		pnlForm.add(new JLabel("Name"));
		pnlForm.add(txtName);
		pnlForm.add(new JLabel("Price"));
		pnlForm.add(txtPrice);

		pnlBtn.add(btnInsert);
		pnlBtn.add(btnUpdate);
		pnlBtn.add(btnDelete);

		pnlSnack.add(pnlForm);
		pnlSnack.add(pnlBtn);

		pnlManage.add(pnlTable);
		pnlManage.add(pnlSnack);

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

	public void loadDataSnack() {
		try {
			openConnection();
			String query = "SELECT * FROM SNACK";
			ps = connect.prepareStatement(query);
			rs = ps.executeQuery();

			Vector<Vector<Object>> tableData = new Vector<>();
			Vector<String> tableDataHeader = new Vector<>();
			tableDataHeader.add("Id");
			tableDataHeader.add("Name");
			tableDataHeader.add("Price");

			while (rs.next()) {
				Vector<Object> data = new Vector<>();

				int Id = rs.getInt(1);
				String Name = rs.getString(2);
				int Price = rs.getInt(3);

				data.add(Id);
				data.add(Name);
				data.add(Price);

				tableData.add(data);
			}

			DefaultTableModel dtm = new DefaultTableModel(tableData, tableDataHeader);
			tblSnack.setModel(dtm);
			closeConnection();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertDataSnack() {

		String Name = txtName.getText();
		int lenName = Name.length();
		int Price = Integer.parseInt(txtPrice.getText());

		try {
			openConnection();
			pst = connect.prepareStatement("SELECT MAX(SnackId)+1 FROM SNACK");
			rst = pst.executeQuery();
			String SnackId = "";
			while (rst.next()) {
				SnackId = rst.getString(1);
			}

			String query = "INSERT INTO SNACK(SnackId, SnackName, SnackPrice) VALUES(?,?,?)";
			ps = connect.prepareStatement(query);

			if (lenName < 5 || lenName > 30) {
				JOptionPane.showMessageDialog(this, "Name must be 5-30 characters", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
			ps.setString(1, Name);

			if (Price <= 0) {
				JOptionPane.showMessageDialog(this, "Price cant be lower or equal to 0", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}

			ps.setInt(2, Price);
			ps.setString(1, SnackId.toString());
			ps.setString(2, txtName.getText());
			ps.setInt(3, Integer.parseInt(txtPrice.getText()));
			ps.executeUpdate();
			closeConnection();

			txtName.setText("");
			txtPrice.setText("");

			JOptionPane.showMessageDialog(this, "Insert Success", "Success", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateDataSnack() {

		String Name = txtName.getText();
		int lenName = Name.length();
		int Price = Integer.parseInt(txtPrice.getText());
		int Id = Integer.parseInt(txtId.getText());

		try {
			openConnection();
			String query = "UPDATE SNACK SET SnackName= ?, SnackPrice= ? WHERE SnackId= ?";
			ps = connect.prepareStatement(query);

			if (lenName < 5 || lenName > 30) {
				JOptionPane.showMessageDialog(this, "Name must be 5-30 characters", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
			ps.setString(1, Name);

			if (Price <= 0) {
				JOptionPane.showMessageDialog(this, "Price cant be lower or equal to 0", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
			ps.setInt(2, Price);

			if (Id <= 0) {
				JOptionPane.showMessageDialog(this, "Snack Must be Choosen", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			ps.setInt(3, Id);
			ps.executeUpdate();
			closeConnection();

			txtId.setText("");
			txtName.setText("");
			txtPrice.setText("");

			JOptionPane.showMessageDialog(this, "Update Success", "Success", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteDataSnack() {

		int Id = Integer.parseInt(txtId.getText());

		try {
			openConnection();
			String query = "DELETE FROM SNACK WHERE SnackId = ?";
			ps = connect.prepareStatement(query);
			if (Id <= 0) {
				JOptionPane.showMessageDialog(this, "Snack Must be Choosen", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			ps.setInt(1, Id);
			ps.executeUpdate();
			closeConnection();

			JOptionPane.showMessageDialog(this, "Delete Success", "Success", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public ManageSnack() {
		initialize();

		setTitle("Manage Snack");
		setVisible(true);
		setSize(800, 600);
		setClosable(true);
		setResizable(true);

		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);

		loadDataSnack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnInsert) {
			insertDataSnack();
			loadDataSnack();
		} else if (e.getSource() == btnUpdate) {
			updateDataSnack();
			loadDataSnack();
		} else if (e.getSource() == btnDelete) {
			deleteDataSnack();
			loadDataSnack();
		}
	}
}
