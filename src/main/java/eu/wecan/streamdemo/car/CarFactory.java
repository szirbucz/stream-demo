package eu.wecan.streamdemo.car;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import eu.wecan.streamdemo.colors.Color;
import eu.wecan.streamdemo.colors.ColorableFactory;

@Service
public class CarFactory implements ColorableFactory<Car> {

  private static final List<String> MANUFACTURERS =
      List.of("Audi", "BMW", "Mazda", "Trabant", "VW");

  @Override
  public Stream<Car> generate() {
    return IntStream.iterate(0, x -> x + 1).boxed().map(this::getRandomCar);
  }

  private Car getRandomCar(final int serialNumber) {
    final Random random = new Random();
    final Color[] colors = Color.values();
    return Car.builder().withId(UUID.randomUUID().toString())
        .withColor(colors[random.nextInt(colors.length)])
        .withManufacturer(MANUFACTURERS.get(random.nextInt(MANUFACTURERS.size())))
        .withMaxSpeed(150 + random.nextInt(150))
        .withName("Car" + serialNumber).build();
  }
}
