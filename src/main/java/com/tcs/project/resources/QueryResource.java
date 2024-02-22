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
@RequestMapping("/queries")
public class QueryResource {

    @Autowired
    private QueryService queryService;

    @PostMapping
    public ResponseEntity<Query> createQuery(@RequestBody Query query) {
        Query createdQuery = queryService.createQuery(query);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuery);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Query> getQueryById(@PathVariable int id) {
        Optional<Query> queryOptional = queryService.getQueryById(id);
        return queryOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Query>> getAllQueries() {
        List<Query> queries = queryService.getAllQueries();
        return ResponseEntity.ok(queries);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Query> updateQuery(@PathVariable int id, @RequestBody Query updatedQuery) {
        Query query = queryService.updateQuery(id, updatedQuery);
        return ResponseEntity.ok(query);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuery(@PathVariable int id) {
        queryService.deleteQuery(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/admin-answer")
    public ResponseEntity<Void> adminAnswer(@PathVariable int id, @RequestBody String answer) {
        queryService.adminAnswer(id, answer);
        return ResponseEntity.ok().build();
    }
}

