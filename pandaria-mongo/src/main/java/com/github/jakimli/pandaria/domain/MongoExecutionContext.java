package com.github.jakimli.pandaria.domain;

import com.github.jakimli.pandaria.domain.wait.Repeatable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Scope("cucumber-glue")
public class MongoExecutionContext extends Repeatable {

    private String collection;
    private Consumer<String> execution;

    @Override
    public void retry() {
        execute();
    }

    public void collection(String collection) {
        this.collection = collection;
    }

    public void execution(Consumer<String> execution) {
        this.execution = execution;
    }

    public void execute() {
        this.execution.accept(this.collection);
    }
}
