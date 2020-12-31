package kz.testcenter.todoserver.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ToDo {
    @NotNull
    private String id;

    @NotNull
    @NotBlank
    private String description;
    private Boolean completed;

    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime created;

    private LocalDateTime modified;

    public ToDo() {
        LocalDateTime date = LocalDateTime.now();
        this.id = UUID.randomUUID().toString();
        this.created = date;
        this.modified = date;
    }
}
