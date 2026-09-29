package ee.foobar.kodu1film.controller;

import ee.foobar.kodu1film.entity.Type;
import ee.foobar.kodu1film.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TypeController {
    private final TypeRepository typeRepository; // Mida see t'pselt teeb

    @GetMapping("types")
    public List<Type> getTypes() {
        return typeRepository.findAll();
    }

    @PostMapping("types")
    public ResponseEntity<Type> addType(@RequestBody Type type) {
        return ResponseEntity.status(HttpStatus.CREATED).body(typeRepository.save(type));
    }
}
