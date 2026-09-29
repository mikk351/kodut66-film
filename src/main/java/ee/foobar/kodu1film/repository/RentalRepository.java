package ee.foobar.kodu1film.repository;

import ee.foobar.kodu1film.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
