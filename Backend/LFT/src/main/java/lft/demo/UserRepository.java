package lft.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import lft.demo.Profiles;
@Component
public interface UserRepository extends CrudRepository<Profiles, Integer> {
	Profiles findByprofUsername(String profname);

}
