package ngn.otp.otp_core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ngn.otp.otp_core.models.LdapServerModel;

public interface LdapServerRepo  extends JpaRepository<LdapServerModel, Long>{

}
