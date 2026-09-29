package ee.foobar.kodu1film.controller;

import ee.foobar.kodu1film.entity.Movie;
import ee.foobar.kodu1film.entity.Type;
import ee.foobar.kodu1film.repository.MovieRepository;
import ee.foobar.kodu1film.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class MovieController {
    private final MovieRepository movieRepository;
    private final TypeRepository typeRepository;


    @GetMapping("movies")
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("movies/available")
    public List<Movie> getAvailableMovies() {
        return movieRepository.findByRentedFalseOrRentedIsNull();
    }


    @PostMapping("movies")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieRepository.save(movie));
    }

    @PatchMapping("movies/{id}")
    public Movie editMovie(@PathVariable Long id, @RequestBody Movie movie) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id is required");
        }
        return movieRepository.save(movie);
    }

    @PatchMapping("movies/{id}/rent")
    public ResponseEntity<Movie> rentMovie(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id is required");
        }
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        movie.setRented(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieRepository.save(movie));
    }

    @PatchMapping("movies/{id}/return")
    public ResponseEntity<Movie> returnMovie(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id is required");
        }
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        movie.setRented(false);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieRepository.save(movie));
    }

    public record UpdateMovieTypeRequest(Long typeId) {}

    @PatchMapping("movies/{id}/type")
    public ResponseEntity<Movie> updateMovieType(@PathVariable Long id, @RequestBody UpdateMovieTypeRequest request) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id is required");
        }
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

//        Type type = typeRepository.findById(request.typeId())
//                .orElseThrow(() -> new RuntimeException("Type not found"));
        Type type = new Type();
        type.setId(request.typeId());
        movie.setType(type);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieRepository.save(movie));
    }


    @DeleteMapping("movie/{id}")
    public List<Movie> deleteMovie(@PathVariable Long id) {
        movieRepository.deleteById(id);
        return movieRepository.findAll();
    }

}

