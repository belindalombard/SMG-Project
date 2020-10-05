public class product {
    private String productName, productDescription;
    private int productQty, productID;
    private double productPrice;
    private boolean hide; 


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
    

    public product(String productName, String productDescription, int productQty, double productPrice, boolean hide, int productID)
    {
     	this.productID=productID;
        this.productName=productName;
        this.productDescription=productDescription;
        this.productQty=productQty;
        this.productPrice=productPrice;
	this.hide=hide;
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

    public int getProductQty(){
        return productQty;
    }

    public double getProductPrice(){
        return productPrice;
    }

    public boolean getHide(){
	return hide; 
    }
}
