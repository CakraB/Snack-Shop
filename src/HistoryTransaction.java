import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class HistoryTransaction extends JInternalFrame {

	JScrollPane sp, sp2;
	JTable tblTransaction, tblDetail;
	JPanel pnlTransaction, pnlDetail, pnlTable, pnlTitle;
	DefaultTableModel dtm;

	public void initialize() {

		// Table
		tblTransaction = new JTable();
		tblDetail = new JTable();

		// Panel
		pnlTitle = new JPanel(new GridLayout(0, 2));
		pnlTransaction = new JPanel(new GridLayout(2, 0));
		pnlDetail = new JPanel(new GridLayout(2, 0));
		pnlTable = new JPanel(new GridLayout(0, 2));

		// Scroll
		sp = new JScrollPane(tblTransaction);
		sp2 = new JScrollPane(tblDetail);
		
		pnlTitle.add(new JLabel("Transaction"));
		pnlTitle.add(new JLabel("Detail"));
		
		pnlTransaction.add(sp);
		pnlDetail.add(sp2);
		
		pnlTable.add(pnlTransaction);
		pnlTable.add(pnlDetail);
		
		add(pnlTitle, BorderLayout.NORTH);
		add(pnlTable, BorderLayout.CENTER);
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
	
	public void loadDataTransaction() {
		try {
			openConnection();
			String query = "SELECT * FROM HEADERTRANSACTION";
			ps = connect.prepareStatement(query);
			rs = ps.executeQuery();

			Vector<Vector<Object>> tableData = new Vector<>();
			Vector<String> tableDataHeader = new Vector<>();
			tableDataHeader.add("TransactionId");
			tableDataHeader.add("UserId");
			tableDataHeader.add("TransactionDate");

			while (rs.next()) {
				Vector<Object> data = new Vector<>();

				int transId = rs.getInt(1);
				int userId = rs.getInt(2);
				String date = rs.getString(3);

				data.add(transId);
				data.add(userId);
				data.add(date);

				tableData.add(data);
			}

			DefaultTableModel dtm = new DefaultTableModel(tableData, tableDataHeader);
			tblTransaction.setModel(dtm);
			closeConnection();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void loadDataDetailTransaction() {
		try {
			openConnection();
			String query = "SELECT * FROM DETAILTRANSACTION";
			ps = connect.prepareStatement(query);
			rs = ps.executeQuery();

			Vector<Vector<Object>> tableData = new Vector<>();
			Vector<String> tableDataHeader = new Vector<>();
			tableDataHeader.add("TransactionId");
			tableDataHeader.add("SnackId");
			tableDataHeader.add("Quantity");

			while (rs.next()) {
				Vector<Object> data = new Vector<>();

				int transId = rs.getInt(1);
				int snackId = rs.getInt(2);
				int qty = rs.getInt(3);

				data.add(transId);
				data.add(snackId);
				data.add(qty);

				tableData.add(data);
			}

			DefaultTableModel dtm = new DefaultTableModel(tableData, tableDataHeader);
			tblDetail.setModel(dtm);
			closeConnection();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public HistoryTransaction() {
		initialize();

		setTitle("History Transaction");
		setVisible(true);
		setSize(600, 400);
		setClosable(true);
		setResizable(true);
		
		loadDataTransaction();
		loadDataDetailTransaction();

	}

}
