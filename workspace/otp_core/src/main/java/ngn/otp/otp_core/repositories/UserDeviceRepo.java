package ngn.otp.otp_core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ngn.otp.otp_core.models.UserDeviceId;
import ngn.otp.otp_core.models.UserDeviceModel;

public interface UserDeviceRepo extends JpaRepository<UserDeviceModel, UserDeviceId>{

}
