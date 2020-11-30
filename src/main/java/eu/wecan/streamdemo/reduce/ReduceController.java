package eu.wecan.streamdemo.reduce;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reduce")
public class ReduceController {

  @PostMapping("/prefix")
  public String longestCommonPrefix(@RequestBody final List<String> strings) {
    return strings.stream().reduce(this::commonPrefix).orElse(Strings.EMPTY);
  }

  @PostMapping("/char-count")
  public Integer charcount(@RequestBody final List<String> strings) {
    return strings.stream()
        .reduce(0,
            (sumLength, string) -> sumLength + string.length(),
            (sumLength1, sumLength2) -> sumLength1 + sumLength2);
  }

  private String commonPrefix(final String first, final String second) {
    final int length = IntStream.iterate(0, x -> x + 1)
        .dropWhile(charsEqualAtIndex(first, second))
        .findFirst().getAsInt();
    return first.substring(0, length);
  }

  private IntPredicate charsEqualAtIndex(final String first, final String second) {
    return x -> x < Math.min(first.length(), second.length())
        && first.charAt(x) == second.charAt(x);
  }
}
