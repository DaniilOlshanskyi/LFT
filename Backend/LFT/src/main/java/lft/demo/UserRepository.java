package lft.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import lft.demo.Profiles;

public interface UserRepository extends CrudRepository<Profiles, Integer> {
	List<Profiles> findByprofusername(String profname);
}
