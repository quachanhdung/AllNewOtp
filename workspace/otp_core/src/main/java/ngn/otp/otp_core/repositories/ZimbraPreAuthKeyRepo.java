package ngn.otp.otp_core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import ngn.otp.otp_core.models.ZimbraPreAuthKeyModel;

public interface ZimbraPreAuthKeyRepo extends JpaRepository<ZimbraPreAuthKeyModel, Integer>{

}

