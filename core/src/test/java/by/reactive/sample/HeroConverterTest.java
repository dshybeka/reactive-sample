package by.reactive.sample;

import static org.junit.Assert.assertEquals;

import by.reactive.sample.converter.HeroConverter;
import by.reactive.sample.model.Hero;
import by.reactive.sample.model.Skill;
import by.reactive.sample.model.SkillInfo;
import com.google.common.collect.Lists;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

public class HeroConverterTest {

    private HeroConverter heroConverter;

    @Before
    public void setUp() throws Exception {

        heroConverter = new HeroConverter();
    }

    @Test
    public void testConvert() throws Exception {
        //  given:
        Hero flash = Hero.builder()
                         .name("Flash")
                         .lastSeen(System.currentTimeMillis())
                         .skills(Lists.newArrayList(
                             Skill.builder()
                                  .name("Fast run")
                                  .type("speed")
                                  .info(SkillInfo.builder()
                                                 .power(0.0)
                                                 .source("space")
                                                 .build())
                                  .build(),
                             Skill.builder()
                                  .name("Lighting shot")
                                  .type("electricity")
                                  .info(SkillInfo.builder()
                                                 .power(10.0)
                                                 .source("space")
                                                 .build())
                                  .build()
                         ))
                         .build();

        //  when:
        Optional<String> converted = heroConverter.toJson(flash);

        System.out.printf(converted.get());

        Optional<Hero> convertedBack = converted.flatMap(heroConverter::fromJson);

        //  then:
        assertEquals(flash, convertedBack.orElseThrow(() -> new IllegalStateException("Should be equal")));
    }
}
