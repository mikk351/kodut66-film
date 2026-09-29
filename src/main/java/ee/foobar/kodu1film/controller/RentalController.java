package ee.foobar.kodu1film.controller;

import ee.foobar.kodu1film.dto.MovieRequest;
import ee.foobar.kodu1film.entity.Movie;
import ee.foobar.kodu1film.entity.Rental;
import ee.foobar.kodu1film.repository.RentalRepository;
import ee.foobar.kodu1film.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @GetMapping("rentals")
    public List<Rental> getRentals() {
        return rentalService.getRentals();
    }

    @GetMapping("find-movie-active-rental/{movieId}")
    public Rental getActiveRentals(@PathVariable Long movieId) {
        return rentalService.findMovieActiveRentals(movieId);
    }

    @PostMapping("start-rental")
    public Rental startRental(@RequestBody List<MovieRequest> movies) {
        return rentalService.saveRental(movies);
    }

    @PostMapping("end-rental")
    public Rental endRental() {
        return rentalService.endRental();
    }


}
