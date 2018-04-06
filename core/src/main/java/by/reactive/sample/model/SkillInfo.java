package by.reactive.sample.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode
@Builder
@Value
public class SkillInfo {

    Double power;

    String source;
}
