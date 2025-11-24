package com.notificationhub.controller;

import com.notificationhub.model.Template;
import com.notificationhub.repository.TemplateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {

    private final TemplateRepository templateRepository;

    public TemplateController(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @PostMapping
    public ResponseEntity<Template> createTemplate(@RequestBody Template template) {
        return ResponseEntity.ok(templateRepository.save(template));
    }

    @GetMapping
    public ResponseEntity<List<Template>> getAllTemplates() {
        return ResponseEntity.ok(templateRepository.findAll());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Template> getTemplate(@PathVariable String code) {
        return templateRepository.findByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
