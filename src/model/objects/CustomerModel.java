package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	/***************************************
	 * Insert or Update a Customer
	 * @param customer
	 * @return the user inserted or updated
	 ***************************************/
	public Customer upsertCustomer(Customer customer) {
		try {
			String sql = null;
			PreparedStatement ps;

			// if the id_customer is not specified, it is an insert
			if (customer.getId_customer() == 0) {
				sql = "INSERT INTO Customers(id_card, documentType, firstname, lastname, email, phone, birthdate) VALUES (?, ?, ?, ?, ?, ?, ?)";
				ps = connection.prepareStatement(sql);
			} else {
				System.out.println("UPDATE Customer...");
				sql = "UPDATE Customers SET id_card=?, documentType=?, firstname=?, lastname=?, email=?, phone=?, birthdate=? WHERE id_customer=?";
				ps = connection.prepareStatement(sql);
				ps.setInt(8, customer.getId_customer());
			}
			ps.setString(1, customer.getId_card());
			ps.setString(2, customer.getDocumentType());
			ps.setString(3, customer.getFirstname());
			ps.setString(4, customer.getLastname());
			ps.setString(5, customer.getEmail());
			ps.setString(6, customer.getPhone());
			ps.setString(7, customer.getBirthdate());
			ps.executeUpdate();
			
			if (customer.getId_customer() == 0) {
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery("SELECT last_insert_rowid()");
				 if (rs.next()) {
					 customer.setId_customer(rs.getInt(1));
				 }
			}
			return customer;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*****************************
	 * GET ALL CUSTOMER
	 * @return List of Customer
	 *****************************/
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

	/*****************************
	 * GET A CUSTOMER
	 * @param id_customer
	 * @return a Customer OR null
	 *****************************/
	public Customer getCustomer(int id_customer) {
		Customer aCustomer = null;
		try {
			String sql = "SELECT * FROM Customers WHERE id_customer = ? Limit 1";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id_customer);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				aCustomer = new Customer();
				aCustomer.setId_customer(rs.getInt("id_customer"));
				aCustomer.setId_card(rs.getString("id_card"));
				aCustomer.setDocumentType(rs.getString("documentType"));
				aCustomer.setFirstname(rs.getString("firstname"));
				aCustomer.setLastname(rs.getString("lastName"));
				aCustomer.setEmail(rs.getString("email"));
				aCustomer.setPhone(rs.getString("phone"));
				aCustomer.setBirthdate(rs.getString("birthdate"));
			} 

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return aCustomer;
	}
}
