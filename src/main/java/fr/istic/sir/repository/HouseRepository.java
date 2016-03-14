package fr.istic.sir.repository;

import fr.istic.sir.domain.House;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the House entity.
 */
public interface HouseRepository extends JpaRepository<House,Long> {

}
