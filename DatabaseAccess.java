import java.sql.*;
import java.math.BigDecimal;

/** Notes to self - make changes to DB: 
 * Change 'Email Address' field in Buyer table to email_address to simplify. 
 * Add ShopName field in the Seller table. 
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
	public boolean AddBuyer(String name, String surname, String email, String cellno, String street_name, String street_number, String district, String password){
		try{
			if(checkAndResetConnection()){
                                PreparedStatement insert_location = null;
                                PreparedStatement insert_buyer = null;

				db.setAutoCommit(false); //default is true. 

				//insert location first.
                                String sql_statement_location = "INSERT INTO smg.location(street_number, street_name, district) VALUES (?,?,(SELECT district_id FROM smg.district WHERE name=?)) RETURNING location_id";
                                insert_location = db.prepareStatement(sql_statement_location);
                                insert_location.setString(1, street_number);
                                insert_location.setString(2, street_name);
                                insert_location.setString(3, district);
                                insert_location.execute();

				//Then insert the buyer linked to the location. 
				ResultSet new_location = insert_location.getResultSet();
				new_location.next();
                                int id_new_location = new_location.getInt(1);
										
	
				String sql_statement_buyer = "INSERT INTO smg.buyer (name, surname, \"EmailAddress\", phone_number, address, password) VALUES (?,?,?,?,?,?)";

				insert_buyer = db.prepareStatement(sql_statement_buyer);

				insert_buyer.setString(1,name);
				insert_buyer.setString(2,surname);
				insert_buyer.setString(3,email);
				insert_buyer.setString(4,cellno);
				insert_buyer.setInt(5,id_new_location);
				insert_buyer.setString(6,password);
				
				//set the data for newbuyer
                                System.out.println("");


				insert_buyer.execute();
				db.commit();
				db.setAutoCommit(true); 	
				insert_location.close();
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
				String sql_statement_find_email = "SELECT * FROM smg.buyer WHERE \"EmailAddress\"=?";
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
				String password_from_db = rs.getString(7);				
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


	public boolean AddSeller(String name, String surname, String email, String cellno, String street_name, String street_number, String district, String password, String ID_Number){
		try{
			if(checkAndResetConnection()){
                                PreparedStatement insert_location = null;
                                PreparedStatement insert_seller = null;

				db.setAutoCommit(false); //default is true. 

				//insert location first.
                                String sql_statement_location = "INSERT INTO smg.location(street_number, street_name, district) VALUES (?,?,(SELECT district_id FROM smg.district WHERE name=?)) RETURNING location_id";
                                insert_location = db.prepareStatement(sql_statement_location);
                                insert_location.setString(1, street_number);
                                insert_location.setString(2, street_name);
                                insert_location.setString(3, district);
                                insert_location.execute();

				//Then insert the seller linked to the location. 
				ResultSet new_location = insert_location.getResultSet();
				new_location.next();
                                int id_new_location = new_location.getInt(1);
									
				String sql_statement_seller = "INSERT INTO smg.seller (name, surname, email_address, phone_numebr, password, national_id, address) VALUES (?,?,?,?,?,?,?)";

				insert_seller = db.prepareStatement(sql_statement_seller);

				insert_seller.setString(1,name);
				insert_seller.setString(2,surname);
				insert_seller.setString(3,email);
				insert_seller.setString(4,cellno);
				insert_seller.setString(5,password);
				insert_seller.setString(6,ID_Number);
				insert_seller.setInt(7,id_new_location);
								
				insert_seller.execute();
				db.commit();
				db.setAutoCommit(true); 	
				insert_location.close();
				insert_seller.close(); 
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
				String password_from_db = rs.getString(6);				
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
	public boolean AddProductToShop(int seller_code, String name, String description, BigDecimal cost, int quantity_left, boolean visible, byte[] photo){
		try{
			if(checkAndResetConnection()){
                                PreparedStatement insert_product = null;
 	
				db.setAutoCommit(false); //default is true. 
	
				//insert product into db.
                                String sql_statement_product = "INSERT INTO smg.product(name, description, cost, quantity_left, visible, photo) VALUES (?,?,?,?,?,?)";
                                insert_product = db.prepareStatement(sql_statement_product);
                                insert_product.setString(1, name);
                                insert_product.setString(2, description);
                                insert_product.setBigDecimal(3, cost);
				insert_product.setInt(4, quantity_left);
				insert_product.setBoolean(5,visible);
				insert_product.setBytes(6,photo);

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
				String check_ex_sql = "SELECT code FROM smg.buyer WHERE \'Email Address\'=?";
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




	}
