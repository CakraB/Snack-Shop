import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class BuySnack extends JInternalFrame implements ActionListener, MouseListener {

	JScrollPane sp, sp2;
	JTable tblSnack, tblCart;
	JPanel pnlForm, pnlFormSnack, pnlBtn, pnlTitle, pnlTextField;
	JTextField txtName, txtId;
	JButton btnAdd, btnRemove, btnCheckOut;
	JSpinner inpQty;
	DefaultTableModel dtm;

	public void initialize() {

		// Table
		tblSnack = new JTable();
		tblCart = new JTable();

		// Panel
		pnlForm = new JPanel(new GridLayout(0, 2));
		pnlFormSnack = new JPanel(new GridLayout(2, 0));
		pnlTextField = new JPanel(new GridLayout(2, 0));
		pnlBtn = new JPanel(new GridLayout(2, 2));
		pnlTitle = new JPanel(new GridLayout(0, 2));

		// Text Field
		txtId = new JTextField();
		txtName = new JTextField();

		// JSpinner
		inpQty = new JSpinner();

		// Scroll
		sp = new JScrollPane(tblCart);
		sp2 = new JScrollPane(tblSnack);

		// Button Add to Cart
		btnAdd = new JButton("Add to Cart");
		btnRemove = new JButton("Remove Item");
		btnCheckOut = new JButton("Check Out");

		pnlTextField.add(txtId);
		pnlTextField.add(txtName);

		pnlFormSnack.add(sp2);
		pnlFormSnack.add(pnlTextField);

		pnlTitle.add(new JLabel("Cart"));
		pnlTitle.add(new JLabel("Snack"));

		pnlBtn.add(btnRemove);
		pnlBtn.add(inpQty);
		pnlBtn.add(btnCheckOut);
		pnlBtn.add(btnAdd);

		pnlForm.add(sp);
		pnlForm.add(pnlFormSnack);

		add(pnlTitle, BorderLayout.NORTH);
		add(pnlForm, BorderLayout.CENTER);
		add(pnlBtn, BorderLayout.SOUTH);

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

			while (rs.next()) {
				Vector<Object> data = new Vector<>();

				int Id = rs.getInt(1);
				String Name = rs.getString(2);

				data.add(Id);
				data.add(Name);

				tableData.add(data);
			}

			DefaultTableModel dtm = new DefaultTableModel(tableData, tableDataHeader);
			tblSnack.setModel(dtm);
			txtId.setText("");
			txtName.setText("");
			closeConnection();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertData() {

//		int selectedRow = tblSnack.getSelectedRow();
//		txtId.setText(tblSnack.getValueAt(selectedRow, 1).toString());
//		txtName.setText(tblSnack.getValueAt(selectedRow, 2).toString());

		String snackName = txtName.getText();
		int qty = (int) inpQty.getValue();

		if (snackName.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Snack must be choosen", "Warning", JOptionPane.WARNING_MESSAGE);
		}

		if (qty == 0) {
			JOptionPane.showMessageDialog(this, "Quantity must more than 0", "Warning", JOptionPane.WARNING_MESSAGE);
		}

		try {
			openConnection();
//			String sql = "SELECT UserId FROM USER WHERE UserId=5";
//			ps = connect.prepareStatement(sql);
//			int userId = ps.getInt("UserId");

			String query = "INSERT INTO CART(SnackId, Quantity) VALUES(?,?)";
			ps = connect.prepareStatement(query);

			ps.setInt(1, Integer.parseInt(txtId.getText()));
			ps.setInt(2, qty);

			ps.executeUpdate();
			closeConnection();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteData() {
		try {
			openConnection();
			String query = "DELETE FROM CART WHERE SnackID = ?";
			ps = connect.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(txtId.getText()));
			ps.executeUpdate();
			closeConnection();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public BuySnack() {
		initialize();

		setTitle("Buy Snack");
		setVisible(true);
		setSize(600, 600);
		setClosable(true);
		setResizable(true);

		btnAdd.addActionListener(this);
		btnRemove.addActionListener(this);
		btnCheckOut.addActionListener(this);

		loadDataSnack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd) {
			insertData();
			loadDataSnack();
		} else if (e.getSource() == btnRemove) {
			deleteData();
			loadDataSnack();
		} else if (e.getSource() == btnCheckOut) {

			loadDataSnack();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
