package kz.testcenter.todoserver.repository;

import kz.testcenter.todoserver.domain.ToDo;
import kz.testcenter.todoserver.domain.ToDoBuilder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ToDoRepository implements CommonRepository<ToDo> {
    private final Map<String, ToDo> toDos = new HashMap<>();

    private final Comparator<Map.Entry<String, ToDo>> entryComparator = Comparator.comparing((Map.Entry<String, ToDo> o) -> o.getValue().getCreated());

    @Override
    public ToDo save(ToDo domain) {
        ToDo result = toDos.get(domain.getId());
        if (result != null) {
            result.setModified(LocalDateTime.now());
            result.setDescription(domain.getDescription());
            result.setCompleted(domain.getCompleted());
            domain = result;
        }

        toDos.put(domain.getId(), domain);

        return domain;
    }

    @Override
    public Iterable<ToDo> save(Collection<ToDo> domains) {
        return domains.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        this.toDos.remove(id);
    }

    @Override
    public ToDo findById(String id) {
        return this.toDos.get(id);
    }

    @Override
    public Iterable<ToDo> findAll() {
        return this.toDos.entrySet().stream().sorted(entryComparator).map(Map.Entry::getValue).collect(Collectors.toList());
    }
}
