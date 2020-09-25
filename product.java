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
}
