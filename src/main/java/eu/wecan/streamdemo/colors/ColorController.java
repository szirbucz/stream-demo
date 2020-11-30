package eu.wecan.streamdemo.colors;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/color")
public class ColorController {

  @Autowired
  private List<ColorableFactory<?>> factories;

  @GetMapping("/{color}")
  public Stream<Colorable> getElementsWithColor(@PathVariable("color") final String color) {
    return factories.stream().flatMap(
        x -> x.generate().filter(hasColor(color)).limit(5));
  }

  private Predicate<? super Colorable> hasColor(final String color) {
    return x -> color.equalsIgnoreCase(x.getColor().toString());
  }

}
