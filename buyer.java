public class buyer {

    private String buyerID, residentialAdr, name,surname, email ,contactNumber, password;
    private boolean validation =false;

    public buyer()
    {}
    public buyer(String buyerID, String residentialAdr, String name, String password, String email, String contactNumber, boolean validation)
    {
        this.buyerID=buyerID;
        this.residentialAdr=residentialAdr;
        this.name=name;
       // this.surname=surname;
        this.password=password;
        this.validation=validation;

        this.email=email;
        this.contactNumber=contactNumber;
    }
    //SET Methods
    public void setUserEmail(String email)
    {
        this.email = email;
    }
    //GET Methods

    public String getBuyerID() {
        return buyerID;
    }

    public String getName() {
        return name;
    }

    /*public String getSurname() {
        return surname;
    }
*/
    public String getEmail() {
        return email;
    }

    public String getResidentialAdr() {
        return residentialAdr;
    }

    public String getPassword() {
        return password;
    }

    public String getContactNumber() {
        return contactNumber;
    }
    public boolean getValidation()
    {
        return validation;
    }
}
