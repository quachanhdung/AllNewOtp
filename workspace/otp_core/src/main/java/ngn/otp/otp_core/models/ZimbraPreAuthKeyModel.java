package ngn.otp.otp_core.models;

import jakarta.persistence.*;

@Entity
@Table(name = "zimbrapreauthkey")
public class ZimbraPreAuthKeyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Lob
    @Column(name = "preauthkey")
    private byte[] preAuthKey;
    
    public ZimbraPreAuthKeyModel() {
    	
    }
	public ZimbraPreAuthKeyModel(Integer id, byte[] preAuthKey) {
		super();
		this.id = id;
		this.preAuthKey = preAuthKey;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public byte[] getPreAuthKey() {
		return preAuthKey;
	}
	public void setPreAuthKey(byte[] preAuthKey) {
		this.preAuthKey = preAuthKey;
	}


    
}

