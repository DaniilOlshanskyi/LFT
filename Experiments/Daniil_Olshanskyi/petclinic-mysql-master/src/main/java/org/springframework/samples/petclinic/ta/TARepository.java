package org.springframework.samples.petclinic.ta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TARepository extends JpaRepository<Ta, Integer> {
}
