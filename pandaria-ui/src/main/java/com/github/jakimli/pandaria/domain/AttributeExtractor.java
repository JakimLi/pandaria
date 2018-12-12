package com.github.jakimli.pandaria.domain;

import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.ImmutableMap.of;

public class AttributeExtractor {

    private WebElement element;

    public AttributeExtractor(WebElement element) {
        this.element = element;
    }

    public String attribute(String name) {
        Map<String, Extractor> extractors = of(
                "selected", e -> String.valueOf(e.isSelected()),
                "enabled", e -> String.valueOf(e.isEnabled())
        );

        return Optional.ofNullable(extractors.get(name))
                .map(e -> e.extract(element))
                .orElse(element.getAttribute(name));
    }

    private interface Extractor {
        String extract(WebElement element);
    }
}
