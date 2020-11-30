package eu.wecan.streamdemo.dog;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import eu.wecan.streamdemo.colors.Color;
import eu.wecan.streamdemo.colors.Colorable;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = Dog.DogBuilder.class)
public class Dog implements Colorable {
    private String name;
    private Color color;
    private Gender gender;
    private String breed;
}
