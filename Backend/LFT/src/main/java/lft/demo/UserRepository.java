package lft.demo;

import org.springframework.data.repository.CrudRepository;
import lft.demo.Profiles;

public interface UserRepository extends CrudRepository<Profiles, Integer> {

}
