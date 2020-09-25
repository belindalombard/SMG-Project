import java.sql.Array;

public class store {
    private String storeID, storeName, storeLocation, storeDescription;
    private Array products;

    public store(String storeID,String storeName,String storeLocation,String storeDescription,Array products)
    {
        this.storeID = storeID;
        this.storeName = storeName;
        this.storeLocation =storeLocation;
        this.storeDescription=storeDescription;
        this.products = products;
    }
}
