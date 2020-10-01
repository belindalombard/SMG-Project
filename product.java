public class product {
    private String productID,productName, productDescription;
    private int productQty;
    private double productPrice;

    public product(String productID, String productName, String productDescription, int productQty, double productPrice)
    {
        this.productID=productID;
        this.productName=productName;
        this.productDescription=productDescription;
        this.productQty=productQty;
        this.productPrice=productPrice;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public String getProductID(){
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
}
