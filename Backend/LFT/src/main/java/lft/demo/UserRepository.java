package lft.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lft.demo.Profiles;
@Repository
public interface UserRepository extends CrudRepository<Profiles, Integer> {
	Profiles findByprofUsername(String profname);

}
