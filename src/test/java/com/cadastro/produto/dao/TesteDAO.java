package com.cadastro.produto.dao;

import java.sql.*;

public class TesteDAO {
/*
create database cadastroproduto;

create table produto(
   id INT NOT NULL AUTO_INCREMENT,
   nome VARCHAR(100) NOT NULL,
   data DATE,
   PRIMARY KEY ( id )
);

select * from produto;

*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {	          
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cadastroproduto" +
	    				"?useTimezone=true&serverTimezone=UTC", "root", "root"); 
	            Statement st = conn.createStatement(); 
	            st.executeUpdate("INSERT INTO produto " + "VALUES (null, 'Notebook','2020/5/29')"); 
	            System.err.println("Insert Realizado"); 

	            conn.close(); 
	        } catch (Exception e) { 
	            System.err.println("Exception! "); 
	            System.err.println(e.getMessage()); 
	        } 
	  
	    }
	} 
	
