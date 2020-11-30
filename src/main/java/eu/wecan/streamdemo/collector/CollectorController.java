package eu.wecan.streamdemo.collector;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collector")
public class CollectorController {

  @PostMapping("/reverse")
  public List<String> reverse(@RequestBody final List<String> input) {
    return input.parallelStream().collect(reverseCollector());
  }

  @PostMapping("/char-count")
  public Integer charcount(@RequestBody final List<String> strings) {
    return strings.stream().collect(Collectors.summingInt(String::length));
  }

  @PostMapping("/longest-string")
  public String longestString(@RequestBody final List<String> strings) {
    return strings.parallelStream()
        .collect(Collectors.maxBy((x, y) -> x.length() - y.length()))
        .orElse(Strings.EMPTY);
  }

  private <T> Collector<T, LinkedList<T>, LinkedList<T>> reverseCollector() {
    return Collector.of(
        () -> new LinkedList<T>(),
        (result, element) -> {
          result.addFirst(element);
        },
        (result1, result2) -> {
          result2.addAll(result1);
          return result2;
        });
  }

}
