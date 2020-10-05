import java.sql.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
/** Notes to self - make changes to DB: 


- When recreating db, run this functions
* INSERT INTO smg.buyer(name,email_address,phone_number,password,area,national_id) VALUES ('hi','belindalombard@gmail.com','0663041010','hello1234',54,'9810270065082');
* INSERT INTO smg.seller(name,email_address,phone_number,password,national_id,area) VALUES ('belindal','belindalombard2@gmail.com','0663041010','hello12345',63,'9810270065083');
* \COPY smg.district(name,province) FROM '/home/belinda/Desktop/CSC3003S/SMG_Project/Stage4/data/districts.csv' DELIMITER ',' CSV HEADER;
*/ 

public class DatabaseAccess {
	Connection db;

	//Constructer - it sets the connection to the database
	public DatabaseAccess() {
                String url = "jdbc:postgresql://witblitz.zapto.org:20977/smgapp";
                String username = "smgapp";
                String password = "Bi_h0Iu8ie";

                try {
                        Class.forName("org.postgresql.Driver");
                        db = DriverManager.getConnection(url, username, password);
                }
                catch (SQLException e){
                        e.printStackTrace();
                }
                catch (ClassNotFoundException f){
                        f.printStackTrace();
                }
	}

	
	//Method to test that connection is open. If the connection is open, the buyer can be added. If not, try to set the connection and then add a buyer. 	
	public boolean AddBuyer(String name, String email, String cellno, String password, String district, String national_id){
		try{
			if(checkAndResetConnection()){
                                PreparedStatement insert_buyer = null;

	
				String sql_statement_buyer = "INSERT INTO smg.buyer (name, email_address, phone_number, password, area, national_id) VALUES (?,?,?,?,(SELECT district_id FROM smg.district WHERE name=?),?)";

				insert_buyer = db.prepareStatement(sql_statement_buyer);

				insert_buyer.setString(1,name);
				insert_buyer.setString(2,email);
				insert_buyer.setString(3,cellno);
				insert_buyer.setString(4,password);
				insert_buyer.setString(5,district);
				insert_buyer.setString(6,national_id);
				
				//set the data for newbuyer
				insert_buyer.execute();
				insert_buyer.close(); 
				return true;
			}
		}
		catch (Exception e){
			e.printStackTrace();
			return false;	
		}
		return false;
	}
			
       //test if connection is set - if it is set, return true. If it is not set, establish a new connection and return true :)           
	public boolean checkAndResetConnection(){
		try {
			if (!db.isClosed()){
				return true;
			}
			else {
				return resetConnection();
			}
	
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return false;

	}

		
	//Reset connection if connection failed for some reason.
	//Returns true if connection successfuly established. 
	public boolean resetConnection() {
                String url = "jdbc:postgresql://witblitz.zapto.org:20977/smgapp";
                String username = "smgapp";
                String password = "Bi_h0Iu8ie";

                try {
                        Class.forName("org.postgresql.Driver");
                        db = DriverManager.getConnection(url, username, password);
                }
                catch (SQLException e){
                        e.printStackTrace();
			return false;
                }
                catch (ClassNotFoundException f){
                        f.printStackTrace();
			return false;
                }
		return true;
        }


	//Method for loggin in of buyer - confirm if the Username and Password exists and matches. 
	public boolean BuyerLogin(String email, String password){
 			try {
				//Declare variable 
				int email_id = 0;

				//First check if the email is in the Database. 
                                PreparedStatement find_buyer_email = null;                              
				String sql_statement_find_email = "SELECT * FROM smg.buyer WHERE email_address=?";
                                find_buyer_email = db.prepareStatement(sql_statement_find_email);
                                find_buyer_email.setString(1, email);
                                ResultSet rs = find_buyer_email.executeQuery();
				if (rs.next()) //Meaning the email address exists in the buyer database.
					email_id = rs.getInt(1); 
				else{ //Meaning the email address is not in the database.
					find_buyer_email.close();	
					return false; //Login failed. 
				}
				//Next, check if the password of the buyer matches with the email. 
				String password_from_db = rs.getString(5);				
				if (password_from_db.equals(password)){
					find_buyer_email.close();
					return true;		
				}			
				
				find_buyer_email.close();
				return false;

                        }
			catch (Exception e){
				e.printStackTrace();
				return false;
			}
                
		}


	public boolean AddSellerAndShop(String name, String email, String cellno,String password, String ID_Number, String district, String shop_name, String acc_number, String bank, String branch, String delivery, String description){
		try{
			if(checkAndResetConnection()){
				db.setAutoCommit(false); //default is true. 

				//First insert the Seller.  			
				String sql_statement_seller = "INSERT INTO smg.seller (name, email_address, phone_numebr, password, national_id, area) VALUES (?,?,?,?,?,(SELECT district_id FROM smg.district WHERE name=?)) RETURNING code";

				PreparedStatement insert_seller = db.prepareStatement(sql_statement_seller);

				insert_seller.setString(1,name);
				insert_seller.setString(2,email);
				insert_seller.setString(3,cellno);
				insert_seller.setString(4,password);
				insert_seller.setString(5,ID_Number);
				insert_seller.setString(6,district);
								
				//Get Resultset to know what the new Seller's code is. 
				insert_seller.execute();
				ResultSet rs = insert_seller.getResultSet();
				
				rs.next();
				int new_seller_code = rs.getInt(1); 
				insert_seller.close();
				
				//Add the shop, linked to the seller. 
				String insert_shop_sql = "INSERT INTO smg.shop(shop_name, bank_account_number, bank_name, bank_banch_code, delivery_method, description, seller) VALUES(?,?,?,?,?,?,?)";
				PreparedStatement insert_shop  = db.prepareStatement(insert_shop_sql);
				insert_shop.setString(1,shop_name);
				insert_shop.setString(2,acc_number);
				insert_shop.setString(3,bank);
				insert_shop.setString(4,branch);
				insert_shop.setString(5,delivery);
				insert_shop.setString(6,description);
				insert_shop.setInt(7,new_seller_code);
				insert_shop.execute();
				insert_shop.close();
				db.commit();
				db.setAutoCommit(true); 	
				return true;
			}
		}
		catch (Exception e){
			e.printStackTrace();
			return false;	
		}
		return false;
	}


	//Method for loggin in of seller- confirm if the Username and Password exists and matches. 
	public boolean SellerLogin(String email, String password){
 			try {
				//Declare variable 
				int email_id = 0;

				//First check if the email is in the Database. 
                                PreparedStatement find_seller_email = null;                              
				String sql_statement_find_email = "SELECT * FROM smg.seller WHERE email_address=?";
                                find_seller_email = db.prepareStatement(sql_statement_find_email);
                                find_seller_email.setString(1, email);
                                ResultSet rs = find_seller_email.executeQuery();
				if (rs.next()) //Meaning the email address exists in the seller database.
					email_id = rs.getInt(1); 
				else{ //Meaning the email address is not in the database.
					find_seller_email.close();	
					return false; //Login failed. 
				}
				//Next, check if the password of the buyer matches with the email. 
				String password_from_db = rs.getString(5);				
				if (password_from_db.equals(password)){
					find_seller_email.close();
					return true;		
				}			
				
				find_seller_email.close();
				return false;

                        }
			catch (Exception e){
				e.printStackTrace();
				return false;
			}
                
		}


	public String[] GetAllDistricts(){
		try{
			if(checkAndResetConnection()){
				//Get the number of entries in the District table
				String count_entries_sql = "SELECT COUNT(*) FROM smg.district";
				PreparedStatement count_entries = db.prepareStatement(count_entries_sql);
				ResultSet rs = count_entries.executeQuery();
				rs.next();
				int number_of_entries = rs.getInt(1); 
				count_entries.close();

				//Initialize the array
				String[] districts = new String[number_of_entries];

				//Get the data from the district table and add it to the array.
				String get_districts_sql = "SELECT * FROM smg.district";
				PreparedStatement get_districts = db.prepareStatement(get_districts_sql);
				ResultSet d = get_districts.executeQuery();
				int k=0;
				while (d.next()){
					districts[k]=d.getString(2);
					k++;
				}
				get_districts.close();
				return districts;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	//Used by a Seller to add a new product to their shop.
	public boolean AddProductToShop(int seller_code, String name, String description, BigDecimal cost, int quantity_left, boolean visible){
		try{
			if(checkAndResetConnection()){
                                PreparedStatement insert_product = null;
 
			
                                String sql_statement_product = "INSERT INTO smg.product(seller_code, name, description, cost, quantity_left, visible, photo) VALUES (?,?,?,?,?,?,null)";
                                insert_product = db.prepareStatement(sql_statement_product);
                                insert_product.setInt(1, seller_code);
				insert_product.setString(2, name);
                                insert_product.setString(3, description);
                                insert_product.setBigDecimal(4, cost);
				insert_product.setInt(5, quantity_left);
				insert_product.setBoolean(6,visible);
				//insert_product.setBytes(7,photo);

				insert_product.execute();
				insert_product.close();
				return true;
			}
		}
		catch (Exception e){
			e.printStackTrace();
			return false;	
		}
		return false;


	}

	//Method to close the connection so that unnecessary resources aren't being taken up. 
	public void CloseConnection(){
		try{
			db.close();
		} catch (Exception e){
			e.printStackTrace();
		}

	}

	//Login when it is unknown whether a Seller or Buyer is signing in. 
	public Boolean Login(String email, String password){
		if (SellerLogin(email,password))
			return true;
		if (BuyerLogin(email,password))
			return true;
		else
			return false; 

	}

	//Returns: 1 if email belongs to seller, 0 if email belongs to buyer, -1 if email belongs to nether 
	public int IsBuyerOrSeller(String email) {
		if (SellerExists(email))  
			return 1;
		if (BuyerExists(email))
			return 0;
		return -1;		

	}
	
	//Method to determine if buyer exists in Database, based on Email Address. 
	public boolean BuyerExists(String email) {
		try {
			if (checkAndResetConnection()){
				String check_ex_sql = "SELECT code FROM smg.buyer WHERE email_address=?";
				PreparedStatement check_existence = db.prepareStatement(check_ex_sql);
				check_existence.setString(1,email);
				ResultSet rs = check_existence.executeQuery();
				if (rs.next()){
					check_existence.close();
					return true;
				}
				check_existence.close();
				return false; 
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}


	//Method to determine if seller exists in Database, based on Email Address. 
	public boolean SellerExists(String email) {
		try {
			if (checkAndResetConnection()){
				String check_ex_sql = "SELECT code FROM smg.seller WHERE email_address=?";
				PreparedStatement check_existence = db.prepareStatement(check_ex_sql);
				check_existence.setString(1,email);
				ResultSet rs = check_existence.executeQuery();
				if (rs.next()){
					check_existence.close();
					return true;
				}
				check_existence.close();
				return false; 
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	//Check if an ID number exists in either the seller or buyer table. Return 1 if it is in the seller table, 0 if it is in the buyer table and -1 if it is in neither. If it returns -2, there is a connection error. 
	public int IDExists(String id) {
		try {
			if (checkAndResetConnection()){
				//check if id number is in buyer table.
				PreparedStatement id_in_buyer=db.prepareStatement("SELECT code FROM smg.buyer WHERE national_id=?");
				id_in_buyer.setString(1,id);
				ResultSet rs = id_in_buyer.executeQuery();
				id_in_buyer.close();
				if (rs.next())
		         		return 1; //id number belongs to buyer table     
				//check if id number is in seller table. 
				PreparedStatement id_in_seller=db.prepareStatement("SELECT code FROM smg.seller WHERE national_id=?");
				id_in_seller.setString(1,id);
				rs = id_in_seller.executeQuery();
				id_in_seller.close();
				if (rs.next())
					return 0; //id number belongs to seller table. 	
				return -1;
			}
			return -2;
		}
		catch (Exception e){
			e.printStackTrace();
			return -2;		
			}
	}

	//Check if an cell number exists in either the seller or buyer table. Return 1 if it is in the seller table, 0 if it is in the buyer table and -1 if it is in neither. If it returns -2, there is a connection error. 
	public int cellExists(String cellno){
		try {
			if (checkAndResetConnection()){
				//check if cell number is in buyer table.
				PreparedStatement cell_in_buyer=db.prepareStatement("SELECT code FROM smg.buyer WHERE phone_number=?");
				cell_in_buyer.setString(1,cellno);
				ResultSet rs = cell_in_buyer.executeQuery();
		                cell_in_buyer.close(); 
				if (rs.next()){
					return 1; //cell number belongs to buyer table.
				}
		              
				//check if id number is in seller table. 
				PreparedStatement cell_in_seller=db.prepareStatement("SELECT code FROM smg.seller WHERE phone_numebr=?");
				cell_in_seller.setString(1,cellno);
				rs = cell_in_seller.executeQuery();
				cell_in_seller.close();
				if (rs.next())
					return 0; //cell number belongs to seller table. 	
				return -1;
			}
			return -2;
		}
		catch (Exception e){
			e.printStackTrace();
			return -2;		
			}
	}
	
	//Check if a shop name exists in either the shop table. Returns boolean. 
	public boolean shopExists(String shopName){
		try {
			if (checkAndResetConnection()){
				PreparedStatement shopexists=db.prepareStatement("SELECT shop_id FROM smg.shop WHERE shop_name=?");
				shopexists.setString(1,shopName);
				ResultSet rs = shopexists.executeQuery();
		                shopexists.close(); 
				if (rs.next()){
					return true;
				}
			}
			return false;
		}
		catch (Exception e){
			e.printStackTrace();
			return false;		
			}
	}

	//Get code of user either in the seller or buyer table (specified in parameter).
	public int getUserCode(String email, String table){
		try {
			if (checkAndResetConnection()){
				PreparedStatement get_user_code=null;
				if (table.equals("seller"))					
					get_user_code=db.prepareStatement("SELECT code FROM smg.seller WHERE email_address=?");
				else 
					get_user_code=db.prepareStatement("SELECT code FROM smg.buyer WHERE email_address=?");	
				get_user_code.setString(1,email);
				ResultSet rs = get_user_code.executeQuery();
		                get_user_code.close(); 
				rs.next();
				return rs.getInt(1); 	
			}
			return 0;
		}
		catch (Exception e){
			e.printStackTrace();
			return 0;		
			}
	}

	//Return arraylist of products containing all the products of a specific seller. 
	public ArrayList<product> getProductsFromSeller(int seller_id) {
		ArrayList<product> products = new ArrayList<product>();
		try {
			if (checkAndResetConnection()){
				PreparedStatement get_products = db.prepareStatement("SELECT * FROM smg.product WHERE seller_code=?");
				get_products.setInt(1,seller_id);		
				ResultSet rs = get_products.executeQuery();
		                get_products.close(); 
				while (rs.next()){
					product add_to_list = new product(rs.getString(3), rs.getString(4), rs.getInt(6), rs.getBigDecimal(5).doubleValue(), rs.getBoolean(7), rs.getInt(1));
					products.add(add_to_list);
				}
			return products;	
				 	
			}
			return products;
		}
		catch (Exception e){
			e.printStackTrace();
			return products;		
			}
	}
			
	//Method to update already existing product to new values (if changed). 
	public boolean updateProduct(product P){
		try {
			if (checkAndResetConnection()){
				PreparedStatement updateProduct = db.prepareStatement("UPDATE smg.product SET name=?, description=?, cost=?, quantity_left=?, visible=? WHERE product_id=?");
				updateProduct.setString(1, P.getProductName());
				updateProduct.setString(2,P.getProductDescription());
				BigDecimal price = new BigDecimal(P.getProductPrice(),MathContext.DECIMAL64);
				updateProduct.setBigDecimal(3,price);
				updateProduct.setInt(4,P.getProductQty());
				updateProduct.setBoolean(5, P.getHide());
				updateProduct.setInt(6,P.getProductID());
				updateProduct.executeUpdate();
				updateProduct.close();		
				return true;
			}
			return false;
		}
		catch (Exception e){
			e.printStackTrace();
			return false;		
			}
	}

	public boolean removeProduct(int product_id){
		try {
			if (checkAndResetConnection()){
				PreparedStatement remove_product = db.prepareStatement("DELETE FROM smg.product WHERE product_id=?");
				remove_product.setInt(1,product_id);		
				remove_product.execute();
				remove_product.close();		
				return true;
			}
			return false;
		}
		catch (Exception e){
			e.printStackTrace();
			return false;		
			}
	}

}
