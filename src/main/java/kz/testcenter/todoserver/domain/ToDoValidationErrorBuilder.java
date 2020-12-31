package kz.testcenter.todoserver.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ToDoValidationErrorBuilder {
    public static ToDoValidationError fromBindingErrors(Errors errors) {
        ToDoValidationError error =
                new ToDoValidationError("Validation failed. " + errors.getErrorCount() + "error(s)");
        for (FieldError fieldError: errors.getFieldErrors()) {
            error.addValidationError(fieldError.toString());
        }

        return error;
    }
}
