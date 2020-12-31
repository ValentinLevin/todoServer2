package kz.testcenter.todoserver.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ToDoValidationError {
    private final String errorMessage;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<String> errors = new ArrayList<>();

    public ToDoValidationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void addValidationError(String error) {
        errors.add(error);
    }
}
