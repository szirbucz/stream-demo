package eu.wecan.streamdemo.colors;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class ColorFilter {

  public <T extends Colorable> Stream<T> filter(
      final Stream<T> stream,
      final Optional<Set<Color>> colors) {
    return stream.filter(x -> containedByColors(x.getColor(), colors));
  }

  private boolean containedByColors(final Color color, final Optional<Set<Color>> colors) {
    return colors.map(x -> x.contains(color)).orElse(true);
  }
}
