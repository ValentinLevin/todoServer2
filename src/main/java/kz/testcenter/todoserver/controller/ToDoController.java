package kz.testcenter.todoserver.controller;

import kz.testcenter.todoserver.domain.ToDo;
import kz.testcenter.todoserver.domain.ToDoValidationError;
import kz.testcenter.todoserver.domain.ToDoValidationErrorBuilder;
import kz.testcenter.todoserver.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/todo")
public class ToDoController {
    private final CommonRepository<ToDo> repository;

    @Autowired
    public ToDoController(CommonRepository<ToDo> repository) {
        this.repository = repository;
    }

    @GetMapping("/items")
    public ResponseEntity<Iterable<ToDo>> getToDos() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable("id") String id) {
        return ResponseEntity.ok(repository.findById(id));
    }

    @PatchMapping("/items/{id}")
    public ResponseEntity<ToDo> setCompleted(@PathVariable("id") String id) {
        ToDo toDo = this.repository.findById(id);
        toDo.setCompleted(true);
        this.repository.save(toDo);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(toDo.getId()).toUri();

        return ResponseEntity.ok().header("Location", location.toString()).body(toDo);
    }

    @RequestMapping(value = "/items", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> createToDo(@Valid @RequestBody ToDo toDo, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }

        ToDo result = this.repository.save(toDo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(toDo.getId());
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable("id") String id) {
        this.repository.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ToDoValidationError handleException(Exception exception) {
        return new ToDoValidationError(exception.getMessage());
    }
}
