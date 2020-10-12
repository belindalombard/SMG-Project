import java.sql.Timestamp;

public class message {
	//status can be: created, sent, read. 
	private String message, seller_email, buyer_email, sender, status;
	private Timestamp date_and_time;
	private int message_id;

	//constructor before written to the database - default values are set. 	
	//After this constructor is called and message written to the database, set the status, date_and_time and messageID.
	public message(String seller_email, String buyer_email, String message, String sender) {
		this.seller_email = seller_email;
		this.buyer_email = buyer_email;
		this.message=message;
		this.sender=sender;
		this.status="Created";
		this.date_and_time= null;
		this.message_id=-1;
	}

	//constructor for when the message object is created directly from the database. 
	public message(int message_id, String seller_email, String buyer_email, String message, String sender, String status, Timestamp date_and_time){
		this.seller_email=seller_email;
		this.buyer_email=buyer_email;
		this.message = message;
		this.sender=sender; 
		this.status=status;
		this.date_and_time=date_and_time;
		this.message_id=message_id;

	}

	//Sets status of the message. 
	public void setMessageStatus(String status){
		this.status=status;
	}

	public void setMessageID(int message_id){
		this.message_id=message_id;
	}

	public void setDateAndTime(Timestamp date_and_time){
		this.date_and_time=date_and_time;
	}
	
	public String getSellerEmail(){
		return seller_email;
	}
	
	public String getBuyerEmail(){
		return buyer_email;
	}

	public String getMessage(){
		return message;
	}

	public String getSender(){
		return sender;
	}

	public String getStatus(){
		return status;
	}

	public Timestamp getTimeStamp(){
		return date_and_time;
	}

	public int getMessageID(){
		return message_id;
	}
}
