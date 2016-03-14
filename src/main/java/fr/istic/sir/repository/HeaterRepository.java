package fr.istic.sir.repository;

import fr.istic.sir.domain.Heater;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Heater entity.
 */
public interface HeaterRepository extends JpaRepository<Heater,Long> {

}
