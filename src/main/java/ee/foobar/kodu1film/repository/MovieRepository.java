package ee.foobar.kodu1film.repository;

import ee.foobar.kodu1film.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByRentedFalseOrRentedIsNull();
}
