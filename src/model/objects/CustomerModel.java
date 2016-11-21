package model.objects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import beans.Customer;
import model.database.DBManager;

public class CustomerModel {
	Connection connection;

	public CustomerModel() {
		connection = DBManager.getConnection();
		if (connection == null)
			System.exit(1);
	}

	public ArrayList<Customer> getAllCustomer() {
		ArrayList<Customer> allCustomer = new ArrayList<Customer>();
		try {

			String sql = "SELECT * FROM Customers ";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Customer aCustomer = new Customer();
				
				aCustomer.setId_customer(rs.getInt("id_customer"));
				aCustomer.setId_card(rs.getString("id_card").trim());
				aCustomer.setDocumentType(rs.getString("documentType").trim());
				aCustomer.setFirstname(rs.getString("firstname").trim());
				aCustomer.setLastname(rs.getString("lastName").trim());
				aCustomer.setEmail(rs.getString("email").trim());
				aCustomer.setPhone(rs.getString("phone"));
				aCustomer.setBirthdate(rs.getString("birthdate").trim());

				allCustomer.add(aCustomer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return allCustomer;
	}
}
