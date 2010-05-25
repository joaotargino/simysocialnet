package beans;

public class Invitation {
	
	private String senderLogin;
	private String receiverLogin;
	private String message;
	private String senderGroup;
	private String receiverGroup;
	
	public String getSenderLogin() {
		return senderLogin;
	}
	
	public void setSenderLogin(String senderLogin) {
		this.senderLogin = senderLogin;
	}
	
	public String getReceiverLogin() {
		return receiverLogin;
	}
	
	public void setReceiverLogin(String receiverLogin) {
		this.receiverLogin = receiverLogin;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getSenderGroup() {
		return senderGroup;
	}
	
	public void setSenderGroup(String senderGroup) {
		this.senderGroup = senderGroup;
	}
	
	public String getReceiverGroup() {
		return receiverGroup;
	}
	
	public void setReceiverGroup(String receiverGroup) {
		this.receiverGroup = receiverGroup;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Invitation) {
			Invitation otherInvitation = (Invitation) obj;
			if (otherInvitation.getReceiverLogin().equals(this.getReceiverLogin()) && 
					otherInvitation.getSenderLogin().equals(this.getSenderLogin())) {
				return true;
			}
		}
		return false;
	}
	
}
