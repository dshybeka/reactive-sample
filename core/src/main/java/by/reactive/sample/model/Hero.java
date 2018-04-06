package by.reactive.sample.model;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode
@Builder
@Value
public class Hero {

    String name;

    List<Skill> skills;

    Long lastSeen;
}
