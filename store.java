import java.sql.Array;

public class store {
    private int storeID;
    private String storeName, storeLocation, storeDescription, storeAccountNumber, storeBankName, storeBankBranch, deliveryOption;
    private product[] products;

    public store(int storeID,String storeName,String storeLocation,String storeDescription,product[] products, String storeAccountNumber, String storeBankName, String storeBankBranch, String deliveryOption)
    {
        this.storeID = storeID;
        this.storeName = storeName;
        this.storeLocation =storeLocation;
        this.storeDescription=storeDescription;
        this.products = products;
        this.storeAccountNumber = storeAccountNumber;
        this.storeBankName = storeBankName;
        this.storeBankBranch = storeBankBranch;
	this.deliveryOption = deliveryOption;
    }
    // GET methods
    public product[] getProducts() {
        return products;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public int getStoreID() {
        return storeID;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getAccountNumber(){
	return storeAccountNumber;
    }

    public String getBankName(){
	return storeBankName;
    }

    public String getBranch(){
	return storeBankBranch;
    }

    public String getDelivery(){
	return deliveryOption;
    }

    public void setShopID(int id){
	this.storeID=id; 
    }
}
