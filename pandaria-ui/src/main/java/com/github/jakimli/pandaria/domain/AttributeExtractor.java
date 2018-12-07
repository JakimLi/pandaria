package com.github.jakimli.pandaria.domain;

import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.ImmutableMap.of;

public class AttributeExtractor {

    private WebElement element;

    private Map<String, Extractor> extractors;

    public AttributeExtractor(WebElement element) {
        this.element = element;
        this.extractors = of(
                "selected", e -> String.valueOf(e.isSelected()),
                "enabled", e -> String.valueOf(e.isEnabled())
        );
    }

    public String attribute(String name) {
        return Optional.ofNullable(this.extractors.get(name))
                .map(e -> e.extract(element))
                .orElse(element.getAttribute(name));
    }

    private interface Extractor {
        String extract(WebElement element);
    }
}
