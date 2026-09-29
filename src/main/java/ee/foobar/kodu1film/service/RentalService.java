package ee.foobar.kodu1film.service;

import ee.foobar.kodu1film.dto.MovieRequest;
import ee.foobar.kodu1film.entity.Movie;
import ee.foobar.kodu1film.entity.Rental;
import ee.foobar.kodu1film.entity.Type;
import ee.foobar.kodu1film.repository.MovieRepository;
import ee.foobar.kodu1film.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final MovieRepository movieRepository;

    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public double calculateFilmFee(Type type, int days) {
        switch (type.getType()) {
            case NEW -> {
                return type.getPrice() * days;
            }
            case REGULAR -> {
                if (days < 3) {
                    return type.getPrice();
                }
                return type.getPrice() + type.getPrice() * (days-3);
            }
            case OLD -> {
                if (days < 5) {
                    return type.getPrice();
                }
                return type.getPrice() + type.getPrice() * (days-5);
            }
            case null, default -> {
                return 0;
            }
        }
    }

    public Rental saveRental(List<MovieRequest> movieRequests) {
        List<Movie> dbMovies = new ArrayList<>();
        double sum = 0;
        Rental rental = new Rental();

        for (MovieRequest movieRequest: movieRequests) {
            if (movieRequest.days() < 1) {
                throw new RuntimeException("Rented days should be positive");
            }

            Movie dbMovie = movieRepository.findById(movieRequest.movieId()).orElseThrow();
            if (dbMovie.getRented()) {
                throw new RuntimeException("Movie already rented out.");
            }
            dbMovie.setRented(true);
            dbMovie.setActiveRental(rental);

            dbMovies.add(dbMovie);
            sum += calculateFilmFee(dbMovie.getType(), movieRequest.days());
        }

        rental.setInitialFee(sum);
        rental.setLateFee(0.0);
        rental.setMovies(dbMovies);

        Rental dbRental = rentalRepository.save(rental);
        rental.setId(dbRental.getId());
        return rental;
    }

    public Rental endRental() {
        return rentalRepository.save(new Rental());
    }

    public Rental findMovieActiveRentals(Long movieId) {
        return movieRepository.findById(movieId).orElseThrow().getActiveRental();
    }
}
