package Sion_Chat_M.Sion_Chat_M.Repository;

import Sion_Chat_M.Sion_Chat_M.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
