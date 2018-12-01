package lft.demo.punishments;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import lft.demo.punishments.Punishments;

public interface PunishmentsRepository extends CrudRepository<Punishments, Integer>{
	
	Punishments findByreportsReportId(int id);

}
