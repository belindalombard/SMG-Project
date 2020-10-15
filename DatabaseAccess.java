import javax.swing.*;
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

	public DatabaseAccess(JFrame loginview) {
		String url = "jdbc:postgresql://witblitz.zapto.org:20977/smgapp";
		String username = "smgapp";
		String password = "Bi_h0Iu8ie";

		try {
			Class.forName("org.postgresql.Driver");
			db = DriverManager.getConnection(url, username, password);
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "Connection not established. Please relaunch", "ERROR", JOptionPane.ERROR_MESSAGE);
			loginview.dispose();
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
	
				String sql_statement_buyer = "INSERT INTO smg.buyer (name, email_address, phone_number, password, area, national_id, validated) VALUES (?,?,?,?,(SELECT district_id FROM smg.district WHERE name=?),?,?)";

				insert_buyer = db.prepareStatement(sql_statement_buyer);

				insert_buyer.setString(1,name);
				insert_buyer.setString(2,email);
				insert_buyer.setString(3,cellno);
				insert_buyer.setString(4,password);
				insert_buyer.setString(5,district);
				insert_buyer.setString(6,national_id);
				insert_buyer.setBoolean(7,false);
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
		try 
		{
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
				String sql_statement_seller = "INSERT INTO smg.seller (name, email_address, phone_numebr, password, national_id, area, validated) VALUES (?,?,?,?,?,(SELECT district_id FROM smg.district WHERE name=?),?) RETURNING code";

				PreparedStatement insert_seller = db.prepareStatement(sql_statement_seller);

				insert_seller.setString(1,name);
				insert_seller.setString(2,email);
				insert_seller.setString(3,cellno);
				insert_seller.setString(4,password);
				insert_seller.setString(5,ID_Number);
				insert_seller.setString(6,district);
				insert_seller.setBoolean(7,false);				
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
	
	
	//Used by a Seller to add a new product to their shop IF IMAGE has been added
        public boolean AddProductToShop(int seller_code, String name, String description, BigDecimal cost, int quantity_left, boolean visible, byte[] image){
                try{
                        if(checkAndResetConnection()){
                                PreparedStatement insert_product = null;

                                String sql_statement_product = "INSERT INTO smg.product(seller_code, name, description, cost, quantity_left, visible, photo) VALUES (?,?,?,?,?,?,?)";
                                insert_product = db.prepareStatement(sql_statement_product);
                                insert_product.setInt(1, seller_code);
                                insert_product.setString(2, name);
                                insert_product.setString(3, description);
                                insert_product.setBigDecimal(4, cost);
                                insert_product.setInt(5, quantity_left);
                                insert_product.setBoolean(6,visible);
				insert_product.setBytes(7,image);

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
				String check_ex_sql = "select code from smg.seller where email_address=?";
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

	//Get code of user either in the seller or buyer table (specified in parameter) based on their national ID.
	public int getUserCodeBasedOnID(String id, String table){
		try {
			if (checkAndResetConnection()){
				PreparedStatement get_user_code=null;
				if (table.equals("seller"))					
					get_user_code=db.prepareStatement("SELECT code FROM smg.seller WHERE national_id=?");
				else 
					get_user_code=db.prepareStatement("SELECT code FROM smg.buyer WHERE national_id=?");	
				get_user_code.setString(1,id);
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

	//Return email of a buyer based on their unique db code. 
	public String getBuyerEmail(int code){
		try {
			if (checkAndResetConnection()){
				PreparedStatement get_email=null;
				get_email=db.prepareStatement("SELECT email_address FROM smg.buyer WHERE code=?");	
				get_email.setInt(1,code);
				ResultSet rs = get_email.executeQuery();
				get_email.close();
				rs.next();
				return rs.getString(1); 	
			}
			return null;
		}
		catch (Exception e){
			e.printStackTrace();
			return null;		
			}
	}

	//Return email of a seller based on their unique db code. 
	public String getSellerEmail(int code){
		try {
			if (checkAndResetConnection()){
				PreparedStatement get_email=null;
				get_email=db.prepareStatement("SELECT email_address FROM smg.seller WHERE code=?");	
				get_email.setInt(1,code);
				ResultSet rs = get_email.executeQuery();
				get_email.close();
				rs.next();
				return rs.getString(1); 	
			}
			return null;
		}
		catch (Exception e){
			e.printStackTrace();
			return null;		
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
					product add_to_list = new product(rs.getString(3), rs.getString(4), rs.getInt(6), rs.getBigDecimal(5).doubleValue(), rs.getBoolean(7), rs.getInt(1), rs.getBytes(8));
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
				PreparedStatement updateProduct = db.prepareStatement("UPDATE smg.product SET name=?, description=?, cost=?, quantity_left=?, visible=?, photo=? WHERE product_id=?");
				updateProduct.setString(1, P.getProductName());
				updateProduct.setString(2,P.getProductDescription());
				BigDecimal price = new BigDecimal(P.getProductPrice(),MathContext.DECIMAL64);
				updateProduct.setBigDecimal(3,price);
				updateProduct.setInt(4,P.getProductQty());
				updateProduct.setBoolean(5, P.getHide());
				updateProduct.setBytes(6,P.getProductImage());
				updateProduct.setInt(7,P.getProductID());
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

	public DefaultListModel getBuyerAccountsList(){
		String userName;
		String userEmail;
		DefaultListModel userAccounts = new DefaultListModel();
		try{
			if(checkAndResetConnection()){
				PreparedStatement get_buyer_name = db.prepareStatement("SELECT * FROM smg.buyer");

				ResultSet rs = get_buyer_name.executeQuery();
				while(rs.next()){
					userName = rs.getString(2);
					userEmail = rs.getString(3);
					userAccounts.addElement("Buyer : "+userName);
				}
				get_buyer_name.close();
				return userAccounts;
			}
		}
		catch(Exception e){
			e.printStackTrace();

		}
		return userAccounts;
	}

	public DefaultListModel getSellerAccountslist(){
		String userName;
		String userEmail;
		DefaultListModel userAccounts = new DefaultListModel();
		try{
			if(checkAndResetConnection()){
				PreparedStatement get_seller_name = db.prepareStatement("SELECT * FROM smg.seller");

				ResultSet rs = get_seller_name.executeQuery();
//				ResultSetMetaData rrs = get_seller_name.getMetaData();
				while(rs.next()){
					userName = rs.getString(2);
					userEmail = rs.getString(3);
					userAccounts.addElement("Seller : "+userName);
				}
//
//				for(int i = 1; i <= rrs.getColumnCount(); i++){
//					System.out.println(rrs.getColumnLabel(i));
//				}
				get_seller_name.close();
				return userAccounts;
			}
		}
		catch(Exception e){
			e.printStackTrace();

		}
		return userAccounts;
	}

	public DefaultListModel getAccountList(){
		DefaultListModel combine = getBuyerAccountsList();
		for(int i = 0; i < getSellerAccountslist().getSize(); i++){
			combine.addElement(getSellerAccountslist().getElementAt(i));
		}
		return combine;

	}

	public ArrayList getAllUsers(){
		ArrayList<Object> users = new ArrayList<>();
		try{
			if(checkAndResetConnection()){
				PreparedStatement get_sellers = db.prepareStatement("SELECT * FROM smg.seller");
				PreparedStatement get_buyers = db.prepareStatement("SELECT * FROM smg.buyer");
				db.setAutoCommit(false);
				ResultSet rs2 = get_buyers.executeQuery();
				while(rs2.next()){
					buyer buyer = new buyer(rs2.getString(7), rs2.getString(6),
							rs2.getString(2), rs2.getString(5),
							rs2.getString(3), rs2.getString(4),rs2.getBoolean(8));
					users.add(buyer);
				}

				ResultSet rs = get_sellers.executeQuery();
				while(rs.next()){
//					seller x = new se
					seller seller = new seller(rs.getString(6), rs.getString(7),
							rs.getString(2), rs.getString(5),
							rs.getString(3), rs.getString(4), rs.getBoolean(8));
//					System.out.println(rs.getString(7));
					users.add(seller);
				}
				db.commit();
				db.setAutoCommit(true);
				get_buyers.close();
				get_sellers.close();
				return users;
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
		return users;
	}


	public ArrayList<seller> getSellersAtDistrict(String district) {
		ArrayList<seller> sellers = new ArrayList<seller>();
		try{
			if(checkAndResetConnection()){
				db.setAutoCommit(true);
				PreparedStatement get_sellers = db.prepareStatement("SELECT * FROM smg.seller WHERE area=(SELECT district_id FROM smg.district WHERE name=?)");
				get_sellers.setString(1, district);
				ResultSet rs = get_sellers.executeQuery();
				while(rs.next()){
					seller seller = new seller(rs.getString(6), rs.getString(7),
							rs.getString(2), rs.getString(5),
							rs.getString(3), rs.getString(4),rs.getBoolean(8));
					sellers.add(seller);
				}


				get_sellers.close();
				return sellers;
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
		return sellers;
	}

	//Function to get a shop based on seller
	public store getShopFromSeller(seller sell){
		try{
			if(checkAndResetConnection()){
				db.setAutoCommit(true);
				PreparedStatement get_shop = db.prepareStatement("SELECT * FROM smg.shop WHERE seller=(SELECT code FROM smg.seller WHERE email_address=?)");
				get_shop.setString(1, sell.getEmail());
				ResultSet rs = get_shop.executeQuery();
				get_shop.close();
				//get Products in arraylist
				ArrayList<product> productsList = getProductsFromSeller(getUserCode(sell.getEmail(),"seller")); 
				
				//convert arraylist to array
				product[] products = new product[productsList.size()];
				for (int i=0; i<productsList.size(); i++){
					products[i]=productsList.get(i);
				}

				//create the shop object	
				rs.next();
					// shop_id |    shop_name    | bank_account_number |   bank_name   | bank_banch_code |    delivery_method    |        description        | seller  - db entries
					//String storeID,String storeName,String storeLocation,String storeDescription,Array products, String storeAccountNumber, String storeBankName, String storeBankBranch, String deliveryOption - constructor of shop
					store s = new store(rs.getInt(1), rs.getString(2), sell.getResidentialAdr(), rs.getString(7), products, rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)); 

				get_shop.close();
				return s;
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}



	//get the shop id stored in the db based on the seller id. 
	public int getShopID(int seller_id){
		try {
			if (checkAndResetConnection()){
				PreparedStatement getcode = db.prepareStatement("SELECT shop_id FROM smg.shop WHERE seller=?");
				getcode.setInt(1, seller_id);
				ResultSet rs = getcode.executeQuery();
				rs.next();
				int shop_code = rs.getInt(1);
				return shop_code;
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return -1;
	}

	public void removeBuyerAccount(String buyerID){
		try {
			if(checkAndResetConnection()){
				
				db.setAutoCommit(false);

				//get code of buyer to be removed.
				int code = getUserCodeBasedOnID(buyerID,"buyer");
				
				//remove related message entries.
				PreparedStatement remove_messages = db.prepareStatement("DELETE from smg.message WHERE buyer=?");
				remove_messages.setInt(1,code);
				remove_messages.execute();	
				remove_messages.close();

				//remove related product_orders
				PreparedStatement remove_product_orders = db.prepareStatement("DELETE FROM smg.productorder WHERE po_id=(SELECT order_number FROM smg.order WHERE buyer=?)");
				remove_product_orders.setInt(1,code);
				remove_product_orders.execute();
				remove_product_orders.close();
				
				//remove related orders
				PreparedStatement remove_orders = db.prepareStatement("DELETE FROM smg.order WHERE buyer=?");
				remove_orders.setInt(1,code);
				remove_orders.execute();
				remove_orders.close();
						
					
				//remove buyers.
				PreparedStatement remove_buyer = db.prepareStatement("DELETE FROM smg.buyer WHERE national_id=?");
				remove_buyer.setString(1, buyerID);
				remove_buyer.executeUpdate();
				remove_buyer.close();

				db.commit();

			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/** 
	 * Remove the seller account by first removing any foreign key related entries in the database.
	 */
	public void removeSellerAccount(String sellerID){
		try {
			if(checkAndResetConnection()){
				db.setAutoCommit(false);
				//get code of buyer to be removed.
				int code = getUserCodeBasedOnID(sellerID,"seller");
				
				//remove related message entries.
				PreparedStatement remove_messages = db.prepareStatement("DELETE from smg.message WHERE seller=?");
				remove_messages.setInt(1,code);
				remove_messages.execute();	
				remove_messages.close();

				//remove related product_orders
				PreparedStatement remove_product_orders = db.prepareStatement("DELETE FROM smg.productorder WHERE po_id=(SELECT order_number FROM smg.order WHERE seller=?)");
				remove_product_orders.setInt(1,code);
				remove_product_orders.execute();
				remove_product_orders.close();
				
				//remove related orders
				PreparedStatement remove_orders = db.prepareStatement("DELETE FROM smg.order WHERE seller=?");
				remove_orders.setInt(1,code);
				remove_orders.execute();
				remove_orders.close();
			
				//Remove shop of seller	
				PreparedStatement remove_shop = db.prepareStatement("DELETE FROM smg.shop WHERE seller=?");
				remove_shop.setInt(1,code);
				remove_shop.executeUpdate();
				remove_shop.close();

				//Remove products of seller
				PreparedStatement remove_products = db.prepareStatement("DELETE FROM smg.product WHERE seller_code=?");
				remove_products.setInt(1,code);
				remove_products.execute();
				remove_products.close();

				PreparedStatement remove_seller = db.prepareStatement("DELETE FROM smg.seller WHERE code=?");
				remove_seller.setInt(1,code);
				remove_seller.executeUpdate();
				remove_seller.close();

				db.commit();
				db.setAutoCommit(true);

			}
		}
		catch (Exception e){
			e.printStackTrace();
		}


	}

	//Returns if a account has been validated or not. Specify in the table field whether it is a seller or buyer that are being checked. 
	public boolean checkValidated(String ID, String table) {
		try {
			if(checkAndResetConnection()){
				int code = getUserCodeBasedOnID(ID, table);
				PreparedStatement validated = null;
				
				if (table.equals("buyer"))
					validated = db.prepareStatement("SELECT validated FROM smg.buyer WHERE code=?");
				else 
					validated = db.prepareStatement("SELECT validated FROM smg.seller WHERE code=?");
						
				validated.setInt(1,code);
				ResultSet rs = validated.executeQuery();
				validated.close();
				if (rs.next())
					return rs.getBoolean(1);
				else 
					return false;
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	//Set validation of a specific user the database. If table unknown, run the isSellerOrBuyer method to get an answer. 
	public void setValidation(String ID, String table, Boolean validated) {
		try {
			if(checkAndResetConnection()){
				int code = getUserCodeBasedOnID(ID, table);
				PreparedStatement set_validated = null;
				
				if (table.equals("buyer"))
					set_validated = db.prepareStatement("UPDATE smg.buyer SET validated=? WHERE code=?");
				else 
					set_validated = db.prepareStatement("UPDATE smg.seller SET validated=?  WHERE code=?");
						
				set_validated.setBoolean(1, validated);
				set_validated.setInt(2, code);
				set_validated.executeUpdate();
				set_validated.close();
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	//get district of user 
	public String getUserDistrict(String ID, String table){
		try {
			if(checkAndResetConnection()){
				int code = getUserCodeBasedOnID(ID, table);
				PreparedStatement get_district = null;
				
				if (table.equals("buyer"))
					get_district = db.prepareStatement("SELECT name FROM smg.district WHERE district_id=(SELECT area FROM smg.buyer WHERE code=?)");
				else 
					get_district = db.prepareStatement("SELECT name FROM smg.district WHERE district_id=(SELECT area FROM smg.seller WHERE code=?)");
						
				get_district.setInt(1, code);
				ResultSet rs = get_district.executeQuery();
				
				rs.next();
				return rs.getString(1);

			}
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return null; 
	}

	//Fields in message table:   message_id | seller | buyer | message | status | sender | time
	//Method to send message which will insert the message in the database. Returns unique db ID of newly inserted entry.  
	public int sendMessage(String seller_email, String buyer_email, String message, String sender){
		try {
			if(checkAndResetConnection()){
				int seller_code = getUserCode(seller_email, "seller");
				int buyer_code = getUserCode(buyer_email, "buyer");
				PreparedStatement new_message = db.prepareStatement("INSERT INTO smg.message(seller,buyer,message,status,sender,time) VALUES(?,?,?,?,?,?) RETURNING message_id");
				
				new_message.setInt(1,seller_code);
				new_message.setInt(2,buyer_code);
				new_message.setString(3,message);
				new_message.setString(4,"Sent");
				new_message.setString(5,sender);
				new_message.setTimestamp(6,new Timestamp(System.currentTimeMillis()));
								
				new_message.execute();

				ResultSet rs = new_message.getResultSet();
				rs.next();
				
				return rs.getInt(1);

			}
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return -1; 
	}
	
	//Fields in message table:   message_id | seller | buyer | message | status | sender | time
	//Method returning all the messages send/received by a specific seller.
	public ArrayList<message> getMessagesBySeller(String email){
		ArrayList<message> messages = new ArrayList<message>();
		
		try {
			if(checkAndResetConnection()){
				int code = getUserCode(email, "seller");
		
				PreparedStatement get_messages = db.prepareStatement("SELECT * FROM smg.message WHERE seller=?");
				get_messages.setInt(1, code);
				ResultSet rs = get_messages.executeQuery();

				while (rs.next()){
					//Params in message class constr: int message_id, String seller_email, String buyer_email, String message, String sender, String status, Timestamp date_and_time
					message add = new message(rs.getInt(1), getSellerEmail(code), getBuyerEmail(rs.getInt(3)), rs.getString(4), rs.getString(6), rs.getString(5), rs.getTimestamp(7));
					messages.add(add);      
				}
			return messages;		
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return null; 
	}

	//Method returning all the messages send/received by a specific seller.
	public ArrayList<message> getMessagesByBuyer(String email){
		ArrayList<message> messages = new ArrayList<message>();
		
		try {
			if(checkAndResetConnection()){
				int code = getUserCode(email, "buyer");
		
				PreparedStatement get_messages = db.prepareStatement("SELECT * FROM smg.message WHERE buyer=?");
				get_messages.setInt(1, code);
				ResultSet rs = get_messages.executeQuery();

				while (rs.next()){
					//Params in message class constr: int message_id, String seller_email, String buyer_email, String message, String sender, String status, Timestamp date_and_time
					message add = new message(rs.getInt(1), getSellerEmail(rs.getInt(2)), getBuyerEmail(code), rs.getString(4), rs.getString(6), rs.getString(5), rs.getTimestamp(7));
					messages.add(add);      
				}
			return messages;		
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return null; 


	}	
	
	//Returns a seller object
	//Seller constructor params: String sellerID, String residentialAdr,  String name, String password, String email, String contactNumber, boolean validation
	public seller getSeller(String email){
		seller s = null; 	
		try {
			if(checkAndResetConnection()){
				int code = getUserCode(email, "seller");
		
				PreparedStatement get_obj = db.prepareStatement("SELECT * FROM smg.seller WHERE code=?");
				get_obj.setInt(1, code);
				ResultSet rs = get_obj.executeQuery();
				rs.next(); 	
				
				//table fields  code |    name    | email_address  | phone_numebr |            password            |  national_id  | area | validated
				s = new seller(rs.getString(6), rs.getString(7), rs.getString(2), rs.getString(5), rs.getString(3), rs.getString(4), rs.getBoolean(8));
				
			return s;		
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return null; 


	}	
		
	//Returns a buyer object
	//Buyer constructor params: String buyerID, String residentialAdr, String name, String password, String email, String contactNumber, boolean validation
	public buyer getBuyer(String email){
		buyer s = null; 	
		try {
			if(checkAndResetConnection()){
				int code = getUserCode(email, "buyer");
		
				PreparedStatement get_obj = db.prepareStatement("SELECT * FROM smg.buyer WHERE code=?");
				get_obj.setInt(1, code);
				ResultSet rs = get_obj.executeQuery();
				rs.next(); 	
				//buyer table entries:  code |    name    |      email_address       | phone_number |             password             | area |  national_id  | validated 	
				s = new buyer(rs.getString(7), getDistrictName(rs.getInt(6)), rs.getString(2), rs.getString(5), rs.getString(3), rs.getString(4), rs.getBoolean(8));
				
			return s;		
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return null; 


	}	

	/*
	 * Updates a message status to something else
	 */
	public void updateMessageStatus(int messageID, String status){
		try {
			if(checkAndResetConnection()){
				PreparedStatement update_status = db.prepareStatement("UPDATE smg.message SET status=? WHERE message_id=?");
				update_status.setString(1, status);
				update_status.setInt(2, messageID);
				update_status.executeUpdate();
				update_status.close();
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public String getDistrictName(int district_id){
		try {
			if(checkAndResetConnection()){
				PreparedStatement get_district = db.prepareStatement("SELECT name FROM smg.district WHERE district_id=?");
				get_district.setInt(1, district_id);
				ResultSet rs = get_district.executeQuery();
				get_district.close();
				rs.next();
				return rs.getString(1);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	return null;
	}
	

	//Return arraylist of products containing all the products of a specific seller that are VISIBLE to the buyer.  
	public ArrayList<product> getVisibleProductsFromSeller(int seller_id) {
		ArrayList<product> products = new ArrayList<product>();
		try {
			if (checkAndResetConnection()){
				PreparedStatement get_products = db.prepareStatement("SELECT * FROM smg.product WHERE seller_code=? AND visible=false");
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
	
	/**
	 * Get a product object based on a product name
	 */
	public product getProductFromName(String product_name){
		product p=null;
		try {
			if (checkAndResetConnection()){
				db.setAutoCommit(true);
				PreparedStatement get_product = db.prepareStatement("SELECT * FROM smg.product WHERE name=?");
				get_product.setString(1, product_name);
				ResultSet rs = get_product.executeQuery();

				if (rs.next()){
					//String productName, String productDescription, int productQty, double productPrice, boolean hide, int productID
					//product_id | seller_code |       name       | description | cost | quantity_left | visible | photo
					double cost = rs.getBigDecimal(5).doubleValue();	
					p = new product(rs.getString(3), rs.getString(4), rs.getInt(6), cost, rs.getBoolean(7), rs.getInt(1), rs.getBytes(8));  	
				}
				return p;

			}
		}
		catch (Exception e){
		       e.printStackTrace();
		}
		return p;
	}	
			

	public boolean AddOrder(){ return true; }


 	/* 
	 * Returns the buyer's name based on their email address.
	 */
	public String getBuyerName(String email){
		try {
			if (checkAndResetConnection()){
				db.setAutoCommit(true);
				PreparedStatement get_name = db.prepareStatement("SELECT name FROM smg.buyer WHERE email_address=?");
				get_name.setString(1, email);
				ResultSet rs = get_name.executeQuery();

				if (rs.next()){
					return rs.getString(1);
				}

			}
		}
		catch (Exception e){
		       e.printStackTrace();
		}
		return null;
	}	

	/*
	 * Method to search for a shop based on a search criteria and "search by" option
	 */
	public ArrayList<store> searchShops(String search_criteria, String table) {
		ArrayList<store> shops = new ArrayList<store>();
		try{
			if(checkAndResetConnection()){
				db.setAutoCommit(true);
				PreparedStatement get_sellers=null;

				if (table.equals("district")){
					get_sellers = db.prepareStatement("SELECT * FROM smg.seller WHERE area=(SELECT district_id FROM smg.district WHERE UPPER(name) LIKE ?)");
					ArrayList<seller> sellers = new ArrayList<seller>();
					get_sellers.setString(1, "%"+search_criteria.toUpperCase()+"%");
					ResultSet rs = get_sellers.executeQuery();
					while(rs.next()){
						seller _seller = new seller(rs.getString(6), rs.getString(7), rs.getString(2), rs.getString(5), rs.getString(3), rs.getString(4),rs.getBoolean(8));
						sellers.add(_seller);
					}
					for (int k=0; k<sellers.size(); k++)
						shops.add(getShopFromSeller(sellers.get(k)));
					return shops;
				}
				if ((table.equals("shop"))||(table.equals("store"))){
					get_sellers = db.prepareStatement("SELECT seller FROM smg.shop WHERE UPPER(shop_name) LIKE ?");
					get_sellers.setString(1, "%"+search_criteria.toUpperCase()+"%");
					ResultSet rs = get_sellers.executeQuery();
					while (rs.next()){
						int seller_id = rs.getInt(1);
						seller next_seller = getSeller(getSellerEmail(seller_id));
						store next_shop = getShopFromSeller(next_seller);
						shops.add(next_shop);
					}
					return shops;
				}
				if (table.equals("product")){
					get_sellers = db.prepareStatement("SELECT seller_code FROM smg.product WHERE UPPER(name) LIKE ?");
					get_sellers.setString(1, "%"+search_criteria.toUpperCase()+"%");
					ResultSet rs = get_sellers.executeQuery();
					while (rs.next()){
						int seller_id = rs.getInt(1);
						seller next_seller = getSeller(getSellerEmail(seller_id));
						store next_shop = getShopFromSeller(next_seller);
						shops.add(next_shop);
					}
					return shops;

				}

				get_sellers.close();
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
		return shops;
	}
	
	/*
	 * Returns a seller object based on the seller's shop name
	 */
	public seller getSellerByShopName(String shopName) {
		seller s = null;
		try {
			if (checkAndResetConnection()){
				db.setAutoCommit(true);
				PreparedStatement get_seller = db.prepareStatement("SELECT email_address FROM smg.seller WHERE code=(SELECT seller FROM smg.shop WHERE shop_name=?)");
				get_seller.setString(1, shopName);
				ResultSet rs = get_seller.executeQuery();


				if (rs.next()){
					String seller_email = rs.getString(1);
					s = getSeller(seller_email);
				}

			}
		}
		catch (Exception e){
		       e.printStackTrace();
		}
		return s;
	}

}

