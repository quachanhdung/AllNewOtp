package ngn.otp.otp_core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ngn.otp.otp_core.models.LdapServerModel;

public interface LdapServerRepo  extends JpaRepository<LdapServerModel, Long>{

	List<LdapServerModel> findByDefaultServerTrue();

	List<LdapServerModel> findByOrderByDefaultServerDesc();

}
