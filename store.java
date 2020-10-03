import java.sql.Array;

public class store {
    private String storeID, storeName, storeLocation, storeDescription, storeAccountNumber, storeBankName, storeBankBranch, deliveryOption;
    private Array products;

    public store(String storeID,String storeName,String storeLocation,String storeDescription,Array products, String storeAccountNumber, String storeBankName, String storeBankBranch, String deliveryOption)
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
    public Array getProducts() {
        return products;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public String getStoreID() {
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
}
