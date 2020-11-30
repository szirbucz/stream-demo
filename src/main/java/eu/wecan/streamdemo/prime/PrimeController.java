package eu.wecan.streamdemo.prime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prime")
public class PrimeController {

  @GetMapping("/recursive")
  public List<Integer> primesBelowRecursive(@RequestParam("max") final Integer max) {
    return getPrimes(
        IntStream.iterate(2, x -> x + 1).boxed().limit(max - 1).collect(Collectors.toList()))
            .collect(Collectors.toList());
  }

  private Stream<Integer> getPrimes(final List<Integer> numbers) {
    if (numbers.isEmpty()) {
      return Stream.of();
    }
    final int prime = numbers.get(0);
    return Stream.concat(
        Stream.of(prime),
        getPrimes(
            numbers
                .stream()
                .filter(x -> x % prime > 0)
                .collect(Collectors.toList())));
  }

  @GetMapping("/iterative")
  public List<Integer> primesBelowIterative(@RequestParam("max") final Integer max) {
    List<Integer> numbers =
        IntStream.iterate(2, x -> x + 1).boxed().limit(max - 1).collect(Collectors.toList());
    final List<Integer> primes = new ArrayList<Integer>();
    Optional<Integer> optionalPrime = Optional.of(2);
    while (!optionalPrime.isEmpty()) {
      final int prime = optionalPrime.get();
      primes.add(prime);
      numbers = numbers.stream().filter(x -> x % prime > 0).collect(Collectors.toList());
      optionalPrime = numbers.stream().findFirst();
    }
    return primes;
  }

  @GetMapping("/oldschool")
  public List<Integer> primesBelowOldschool(@RequestParam("max") final Integer max) {
    final boolean[] numbers = new boolean[max + 1];
    int actualPrime = 2;
    while (actualPrime * 2 <= max) {
      for (int n = actualPrime * 2; n <= max; n += actualPrime) {
        numbers[n] = true;
      }
      while (actualPrime <= max && numbers[++actualPrime]) {
      }
    }
    final ArrayList<Integer> primes = new ArrayList<Integer>();
    for (int i = 2; i <= max; ++i) {
      if (!numbers[i]) {
        primes.add(i);
      }
    }
    return primes;
  }
}
