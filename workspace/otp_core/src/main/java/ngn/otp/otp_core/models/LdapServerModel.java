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

    @Column(name = "domainname", length = 255)
    private String domainName;

    @Column(name = "searchbase", length = 255)
    private String searchBase;

    @Column(name = "searchfilter", length = 255)
    private String searchFilter;
    
    @Column(name="binddn", length=255)
    private String binddn;

    @Column(name = "serverdescription", length = 255)
    private String serverDescription;

    //0: active directory; 1: ldap
    @Column(name = "servertype")
    private Integer serverType;

    @Column(name = "defaultserver")
    private Boolean defaultServer;

    @Column(name = "principal", length = 255)
    private String principal;

    @Column(name = "credential", length = 255)
    private String credential;
    

    // Constructors, getters, and setters
    public LdapServerModel() {
    	
    }
    

	public LdapServerModel(Long serverId, String serverUrl, String domainName, String searchBase, String searchFilter,
			String binddn, String serverDescription, Integer serverType, Boolean defaultServer, String principal, String credential) {
		super();
		this.serverId = serverId;
		this.serverUrl = serverUrl;
		this.domainName = domainName;
		this.searchBase = searchBase;
		this.searchFilter = searchFilter;
		this.binddn = binddn;
		this.serverDescription = serverDescription;
		this.serverType = serverType;
		this.defaultServer = defaultServer;
		this.principal = principal;
		this.credential = credential;
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

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getSearchBase() {
		return searchBase;
	}

	public void setSearchBase(String searchBase) {
		this.searchBase = searchBase;
	}

	public String getSearchFilter() {
		return searchFilter;
	}

	public void setSearchFilter(String searchFilter) {
		this.searchFilter = searchFilter;
	}

	public String getServerDescription() {
		return serverDescription;
	}

	public void setServerDescription(String serverDescription) {
		this.serverDescription = serverDescription;
	}

	public Integer getServerType() {
		return serverType;
	}

	public void setServerType(Integer serverType) {
		this.serverType = serverType;
	}

	public Boolean getDefaultServer() {
		return defaultServer;
	}

	public void setDefaultServer(Boolean defaultServer) {
		this.defaultServer = defaultServer;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}


	public String getBinddn() {
		return binddn;
	}


	public void setBinddn(String binddn) {
		this.binddn = binddn;
	}
	

    
}

