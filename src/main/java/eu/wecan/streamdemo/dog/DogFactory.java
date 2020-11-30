package eu.wecan.streamdemo.dog;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import eu.wecan.streamdemo.colors.Color;
import eu.wecan.streamdemo.colors.ColorableFactory;

@Service
public class DogFactory implements ColorableFactory<Dog> {

  private static final List<String> BREEDS = List.of(
      "German shepherd",
      "Bulldog",
      "Puli",
      "Beagle",
      "Labrador Retriever",
      "Dachshund",
      "Rottweiler");

  private final Random random = new Random();

  @Override
  public Stream<Dog> generate() {
    return IntStream.iterate(0, x -> x + 1).boxed().map(this::getRandomDog);
  }

  private Dog getRandomDog(final int n) {
    return Dog.builder()
        .withColor(Color.values()[random.nextInt(Color.values().length)])
        .withGender(random.nextBoolean() ? Gender.FEMALE : Gender.MALE)
        .withBreed(BREEDS.get(random.nextInt(BREEDS.size())))
        .withName("Buksi_" + n)
        .build();
  }
}
