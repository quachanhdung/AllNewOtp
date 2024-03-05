package ngn.otp.otp_core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import ngn.otp.otp_core.models.ZimbraLdapKeyModel;

public interface ZimbraLdapKeyRepo extends JpaRepository<ZimbraLdapKeyModel, Integer>{

}
