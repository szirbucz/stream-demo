package eu.wecan.streamdemo.car;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import eu.wecan.streamdemo.colors.Color;
import eu.wecan.streamdemo.colors.ColorFilter;

@RestController
@RequestMapping("/cars")
public class CarController {

  private static final int N = 20;

  @Autowired
  private CarFactory carFactory;

  @Autowired
  private ColorFilter colorFilter;

  @GetMapping
  public Stream<Car> getAll() {
    return generateCars();
  }

  @GetMapping("by-speed")
  public Stream<Car> getBySpeedRange(@RequestParam("min-speed") final Optional<Integer> minSpeed,
      @RequestParam("max-speed") final Optional<Integer> maxSpeed) {
    return generateCars()
        .filter(car -> carFasterThan(car, minSpeed))
        .filter(car -> carSlowerThan(car, maxSpeed))
        .sorted((x, y) -> x.getMaxSpeed().compareTo(y.getMaxSpeed()));
  }

  @GetMapping("by-id")
  public Map<String, Car> mapById() {
    return generateCars().collect(Collectors.toMap(Car::getId, Function.identity()));
  }

  @GetMapping("by-color")
  public Map<Color, List<Car>> mapByColor() {
    return generateCars().collect(Collectors.groupingBy(Car::getColor));
  }

  @GetMapping("color-filter")
  public Stream<Car> colorFilter(
      @RequestParam(name = "color", required = false) final Optional<Set<Color>> colors) {
    return colorFilter.filter(generateCars(), colors);
  }

  @GetMapping("count-of-brand")
  public Stream<Car> countOfBrand(
      @RequestParam("count") final int count,
      @RequestParam("brand") final String brand) {
    return carFactory.generate()
        .filter(x -> brand.equals(x.getManufacturer()))
        .limit(count);
  }

  @GetMapping("count-by-brand")
  public Map<String, Long> countByBrand() {
    return carFactory.generate().limit(1000)
        .collect(Collectors.groupingBy(Car::getManufacturer, Collectors.counting()));
  }

  private boolean carFasterThan(final Car car, final Optional<Integer> speed) {
    return speed.map(x -> x <= car.getMaxSpeed()).orElse(true);
  }

  private boolean carSlowerThan(final Car car, final Optional<Integer> speed) {
    return speed.map(x -> x >= car.getMaxSpeed()).orElse(true);
  }

  private Stream<Car> generateCars() {
    return carFactory.generate().limit(N);
  }

}
