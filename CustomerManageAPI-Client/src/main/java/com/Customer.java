package com;

import java.sql.*;

public class Customer
{
	private Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
 
			con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/ecustomerdb","root", "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
	
	public String readCustomer()
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
 
			// Prepare the html table to be displayed
	        output = "<table border='1'><tr><th>Customer Account Number</th><th>Customer Name</th><th>NIC</th>"+ "<th>Address</th>" + "<th>Phone Number</th>"+ "<th>Email</th>"+ "<th>Province</th>"+ "<th>Update</th><th>Remove</th></tr>";
	        
	        String query = "select * from electricityaccount";
	        
	        Statement stmt = con.createStatement();
	        
	        ResultSet rs = stmt.executeQuery(query);
	        
	        // iterate through the rows in the result set
	        while (rs.next())
	        {
	          String electricityAcNo = Integer.toString(rs.getInt("electricityAcNo")); 
	          String customerAcNo = rs.getString("customerAcNo");
	          String customerName = rs.getString("customerName");
	          String nic = rs.getString("nic");
	          String address = rs.getString("address");
	          String phonenumber = rs.getString("phonenumber");
	          String email = rs.getString("email");
	          String province = rs.getString("province");
	          
	          
	        // Add a row into the html table
	          
	          output += "<td>" + customerAcNo + "</td>";
	          output += "<td>" + customerName + "</td>";
	          output += "<td>" + nic + "</td>";
	          output += "<td>" + address + "</td>";
	          output += "<td>" + phonenumber + "</td>";
	          output += "<td>" + email + "</td>";
	          output += "<td>" + province + "</td>";
	          
	          //buttons
	          output += "<td><input name='btnUpdate' " + " type='button' value='Update'></td>" + "<td><form method='post' action='customers.jsp'>" + "<input name='btnRemove' " + " type='submit' value='Remove'>" + "<input name='electricityAcNo' type='hidden' " + " value='" + electricityAcNo + "'>" + "</form></td></tr>"; } con.close();
	          
	          //Complete the html table
	          output += "</table>";
	          }
	        
	          catch (Exception e)
	          {
	            output = "Error while reading the customers' details.";
	            System.err.println(e.getMessage());
	          }
	          return output;
	          }
	
	public String insertCustomer(String CustomerAcNo, String CustomerName, String NIC, String Address, String PhoneNumber, String Email, String Province)
	{
		String output = "";
		
		try
		{
		Connection con = connect();
		if (con == null)
		{
		return "Error while connecting to the database";
		}
		// create a prepared statement
		String query = "insert into electricityaccount(`ElectricityAcNo`,`CustomerAcNo`,`CustomerName`,`NIC`,`Address`,`PhoneNumber`,`Email`,`Province`)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		// binding values
		preparedStmt.setInt(1, 0);
		preparedStmt.setString(2, CustomerAcNo);
		preparedStmt.setString(3, CustomerName);
		preparedStmt.setString(4, NIC);
		preparedStmt.setString(5, Address);
		preparedStmt.setString(6, PhoneNumber);
		preparedStmt.setString(7, Email);
		preparedStmt.setString(8, Province);
		 
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newCustomer = readCustomer();
			output = "{\"status\":\"success\", \"data\": \"" +newCustomer + "\"}";
		 }
		
		 catch (Exception e)
		 {
			 output = "{\"status\":\"error\", \"data\":\"Error while inserting the customer.\"}";
			 System.err.println(e.getMessage());
		 }
		
		 return output;
		 
		 }
	
	public String updateCustomer(String ElectricityAcNo, String CustomerAcNo, String CustomerName, String NIC, String Address, String PhoneNumber, String Email, String Province)
    
    { 
	 String output = "";
	 
	 try
	 { 
		 Connection con = connect(); 
		 if (con == null) 
		 {
			 return "Error while connecting to the database for updating."; 
		 }
		 
		 // create a prepared statement
		 String query = "UPDATE electricityaccount SET CustomerAcNo=?,CustomerName=?,NIC=?,Address=?,PhoneNumber=?,Email=?,Province=? WHERE ElectricityAcNo=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 // binding values
		 
		 preparedStmt.setString(1, CustomerAcNo);
		 preparedStmt.setString(2, CustomerName);
		 preparedStmt.setString(3, NIC);
		 preparedStmt.setString(4, Address);
		 preparedStmt.setString(5, PhoneNumber);
		 preparedStmt.setString(6, Email);
		 preparedStmt.setString(7, Province);
		 preparedStmt.setInt(8, Integer.parseInt(ElectricityAcNo)); 
	 
		 // execute the statement
		 preparedStmt.execute(); 
	     con.close();
		
				 String newCustomer = readCustomer();
				 output = "{\"status\":\"success\", \"data\": \"" +
				 newCustomer + "\"}";
		 }
			 
		 catch (Exception e)
		 {
			 output = "{\"status\":\"error\", \"data\": \"Error while updating the customer.\"}";
			 System.err.println(e.getMessage());
		 }
		 
		return output;
	}
		
	public String deleteCustomer(String electricityAcNo){

		String output = "";

		try
		{
			Connection con = connect();

			if (con == null)
			{
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement

			String query = "delete from electricityaccount where electricityAcNo=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setInt(1, Integer.parseInt(electricityAcNo));

			// execute the statement

			preparedStmt.execute();

			con.close();
			 
			 String newCustomer = readCustomer();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newCustomer + "\"}";
		 }
		 
		 catch (Exception e)
		 {
			 output = "{\"status\":\"error\", \"data\": \"Error while deleting the Customer.\"}";
			 System.err.println(e.getMessage());
		 }
		 
		 return output;
		 
		 }
	}
		 