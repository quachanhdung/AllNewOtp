package ngn.otp.otp_core.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ldapserver")
public class LdapServerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serverid")
    private Long serverId;

    @Column(name = "serverurl", unique = true, length = 255)
    private String serverUrl;

    
    @Column(name = "searchbase", length = 255)
    private String searchBase;

    @Column(name = "queryfilter", length = 255)
    private String queryFilter;
    
    @Column(name="userdn", length=255)
    private String userDn;
    
    @Column(name="resultattributes", length=255)
    private String resultAttributes;
    

    @Column(name = "serverdescription", length = 255)
    private String serverDescription;
    
    @Column(name = "defaultserver")
    private Boolean defaultServer;

    // Constructors, getters, and setters
    public LdapServerModel() {
    	
    }
    
	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	
	
	public String getSearchBase() {
		return searchBase;
	}

	public void setSearchBase(String searchBase) {
		this.searchBase = searchBase;
	}


	public String getServerDescription() {
		return serverDescription;
	}

	public void setServerDescription(String serverDescription) {
		this.serverDescription = serverDescription;
	}

	
	public Boolean getDefaultServer() {
		return defaultServer;
	}

	public void setDefaultServer(Boolean defaultServer) {
		this.defaultServer = defaultServer;
	}


	public String getQueryFilter() {
		return queryFilter;
	}


	public void setQueryFilter(String queryFilter) {
		this.queryFilter = queryFilter;
	}


	public String getUserDn() {
		return userDn;
	}


	public void setUserDn(String userDn) {
		this.userDn = userDn;
	}

	public String getResultAttributes() {
		return resultAttributes;
	}

	public void setResultAttributes(String resultAttributes) {
		this.resultAttributes = resultAttributes;
	}

	

    
}

