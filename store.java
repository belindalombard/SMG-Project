import java.sql.Array;

public class store {
    private String storeID, storeName, storeLocation, storeDescription, storeAccountNumber, storeBankName, storeBankBranch;
    private Array products;

    public store(String storeID,String storeName,String storeLocation,String storeDescription,Array products, String storeAccountNumber, String storeBankName, String storeBankBranch)
    {
        this.storeID = storeID;
        this.storeName = storeName;
        this.storeLocation =storeLocation;
        this.storeDescription=storeDescription;
        this.products = products;
        this.storeAccountNumber = storeAccountNumber;
        this.storeBankName = storeBankName;
        this.storeBankBranch = storeBankBranch;
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

}
