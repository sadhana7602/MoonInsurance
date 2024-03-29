package com.tcs.project.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tcs.project.resource.Query;
import com.tcs.project.services.QueryService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/queries")
public class QueryResource {

    @Autowired
    private QueryService queryService;

    @PostMapping("/add")
    public ResponseEntity<Boolean> createQuery(@RequestBody Query query) {
        boolean createdQuery = queryService.createQuery(query);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuery);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Query> getQueryById(@PathVariable int id) {
        Optional<Query> queryOptional = queryService.getQueryById(id);
        return queryOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Query>> getAllQueries() {
        List<Query> queries = queryService.getAllQueries();
        return ResponseEntity.ok(queries);
    }

    @PutMapping("/update")
    public ResponseEntity<Query> updateQuery(@RequestBody Query updatedQuery) {
        Query query = queryService.updateQuery(updatedQuery);
        return ResponseEntity.ok(query);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteQuery(@PathVariable int id) {
        queryService.deleteQuery(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/admin-answer")
    public ResponseEntity<Boolean> adminAnswer(@PathVariable int id, @RequestBody String answer) {
        boolean status =queryService.adminAnswer(id, answer);
        return ResponseEntity.status(HttpStatus.CREATED).body(status);
    }
}

