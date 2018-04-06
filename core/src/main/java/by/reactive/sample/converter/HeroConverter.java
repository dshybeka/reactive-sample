package by.reactive.sample.converter;

import by.reactive.sample.model.Hero;
import com.google.gson.Gson;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public class HeroConverter {

    private final Gson gson;

    public HeroConverter() {

        gson = new Gson();
    }

    public Optional<String> toJson(Hero hero) {

        Optional<String> result = Optional.ofNullable(StringUtils.trimToNull(gson.toJson(hero)));

        return result;
    }

    public Optional<Hero> fromJson(String data) {

        Optional<Hero> result = Optional.ofNullable(gson.fromJson(data, Hero.class));

        return result;
    }
}
