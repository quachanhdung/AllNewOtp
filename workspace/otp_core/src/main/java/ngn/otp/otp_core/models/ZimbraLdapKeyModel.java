package ngn.otp.otp_core.models;

import jakarta.persistence.*;

@Entity
@Table(name = "zimbraldapkey")
public class ZimbraLdapKeyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Lob
    @Column(name = "ldapkey")
    private byte[] ldapKey;

    // Constructors, getters, and setters
    public  ZimbraLdapKeyModel() {
    	
    }
	public ZimbraLdapKeyModel(Integer id, byte[] ldapKey) {
		super();
		this.id = id;
		this.ldapKey = ldapKey;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getLdapKey() {
		return ldapKey;
	}

	public void setLdapKey(byte[] ldapKey) {
		this.ldapKey = ldapKey;
	}


}
