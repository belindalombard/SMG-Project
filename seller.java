public class seller {
    private String sellerID;
    private String residentialAdr,  name, email ,contactNumber, password;
    public seller(String sellerID, String residentialAdr,  String name, String password, String email, String contactNumber)
    {
        this.sellerID=sellerID;
        this.residentialAdr=residentialAdr;
        this.name=name;
        //this.surname=surname;
        this.password=password;
        this.email=email;
        this.contactNumber=contactNumber;

    }
    // GET methods
    public String getSellerID()
    {
        return sellerID;
    }
    public String getResidentialAdr()
    {
        return residentialAdr;
    }
    public String getName()
    {
        return name;
    }
    public String getContactNumber()
    {
        return contactNumber;
    }
    /*public String getSurname()
    {
        return surname;
    }*/
    public String getEmail()
    {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
