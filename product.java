import java.io.File;
import java.io.FileInputStream;


public class product {
    private String productName, productDescription;
    private int productQty, productID;
    private double productPrice;
    private boolean hide;
    private byte[] photo =null;  


    //product table in db has: entries product_id | seller_code | name | description | cost | quantity_left | visible | photo
    public product(String productName, String productDescription, int productQty, double productPrice, boolean hide)
    {
        this.productName=productName;
        this.productDescription=productDescription;
        this.productQty=productQty;
        this.productPrice=productPrice;
	this.hide=hide;
  	this.productID = -1; //product not in DB yet.   
    }
    

    //Constructer - product already has a productid. 
    public product(String productName, String productDescription, int productQty, double productPrice, boolean hide, int productID)
    {
     	this.productID=productID;
        this.productName=productName;
        this.productDescription=productDescription;
        this.productQty=productQty;
        this.productPrice=productPrice;
	this.hide=hide;
    }
    
    
    //Constructer - product already has a photo. 
    public product(String productName, String productDescription, int productQty, double productPrice, boolean hide, int productID, byte[] photo)
    {
     	this.productID=productID;
        this.productName=productName;
        this.productDescription=productDescription;
        this.productQty=productQty;
        this.productPrice=productPrice;
	this.hide=hide;
	this.photo=photo;
    }
    
    public void setProductId(int productID) {
	this.productID = productID;	

    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public void setProductPrice(double productPrice){
        this.productPrice = productPrice;
    }

    public void setProductQty(int productQty){
        this.productQty = productQty;
    }

    public void setProductDescription(String productDescription){
        this.productDescription = productDescription;
    }

    /**
     * Set photo byte array of the product.
     */
    public void setPhoto(byte[] photo){
	this.photo=photo;
    }	

    public void setHide(boolean hide){
	this.hide = hide;
    }	

    public int getProductID(){
        return productID;
    }

    public String getProductName(){
        return productName;
    }

    public String getProductDescription(){
        return productDescription;
    }
    
    
    public byte[] getProductImage(){
	return photo;
    }

    public int getProductQty(){
        return productQty;
    }

    public double getProductPrice(){
        return productPrice;
    }

    public boolean getHide(){
	return hide; 
    }

    public byte[] file_to_bytea(String file_path){
	try {
		File file = new File(file_path);
		FileInputStream fs = new FileInputStream(file);
		long bytes = file.length();	
    		byte[] bytea = new byte[(int)bytes];

		fs.read(bytea, 0, (int) bytes);	
    		return bytea;
	}
	catch (Exception e){
		e.printStackTrace();
	}
	return null;

    }
}
