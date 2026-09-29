package ee.foobar.kodu1film.service;

import ee.foobar.kodu1film.dto.MovieRequest;
import ee.foobar.kodu1film.entity.Movie;
import ee.foobar.kodu1film.entity.MovieType;
import ee.foobar.kodu1film.entity.Rental;
import ee.foobar.kodu1film.entity.Type;
import ee.foobar.kodu1film.repository.MovieRepository;
import ee.foobar.kodu1film.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RentalServiceTest {
    private final Type newType = new Type(1L, MovieType.NEW, 4.0);
    private final Type regularType = new Type(2L, MovieType.REGULAR, 3.0);
    private final Type oldType = new Type(3L, MovieType.OLD, 3.0);

    private final Movie movie7 = new Movie(7L, "November", false, null, regularType);
    private final Rental rental = new Rental(1L, 24.0, 0.0, Collections.singletonList(movie7));


    @Mock
    private MovieRepository movieRepository;

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private RentalService rentalService;



    @BeforeEach
    void setUp() {}

    @Test
    void givenFilmTypeIsNewAndRentedFor6Days_whenCalculatingFilmFee_thenTotalIs24() {
        double filmFee = rentalService.calculateFilmFee(newType, 6);
        assertEquals(24.0, filmFee);
    }

    @Test
    void givenFilmTypeIsOldAndRentedFor6Days_whenCalculatingFilmFee_thenTotalIs6() {
        double filmFee = rentalService.calculateFilmFee(oldType, 6);
        assertEquals(6.0, filmFee);
    }

    @Test
    void given_when_then_1() {
        when(movieRepository.findById(7L)).thenReturn(Optional.of(movie7));
        when(rentalRepository.save(any())).thenReturn(rental);

        MovieRequest movieRequest = new MovieRequest(7L, 6);
        List<MovieRequest> movieRequests = new ArrayList<>(Collections.singletonList(movieRequest));
        Rental rental = rentalService.saveRental(movieRequests);

        assertEquals(12.0, rental.getInitialFee());
    }

    @Test
    void given_when_then() {}

}