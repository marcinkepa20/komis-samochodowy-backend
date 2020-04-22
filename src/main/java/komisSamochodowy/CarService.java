package komisSamochodowy;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    ObjectMapper objectMapper;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("cars")
    public ResponseEntity getCars() throws JsonProcessingException{
        List<Car> cars = carRepository.findAll();
        return ResponseEntity.ok(objectMapper.writeValueAsString(cars));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("cars")
    public ResponseEntity addCars(@RequestBody Car car){
        Optional<Car> carFromDb = carRepository.findByCarbrand(car.getCarbrand());

        if(carFromDb.isPresent()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        Car savedCar = carRepository.save(car);
        return ResponseEntity.ok(savedCar);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("cars/{id}")
    public ResponseEntity deleteCars(@PathVariable int id) throws JsonProcessingException {
        carRepository.deleteById((long) id);
        List<Car> cars = carRepository.findAll();
        return ResponseEntity.ok(objectMapper.writeValueAsString(cars));
    }
}
