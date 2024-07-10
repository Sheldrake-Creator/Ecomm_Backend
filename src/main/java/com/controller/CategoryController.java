package com.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CategoryDTO;
import com.exception.RepositoryException;
import com.response.HttpResponse;
import com.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping("/level")
    public ResponseEntity<HttpResponse> getCategoriesByLevel(@RequestBody Integer levelInteger) {
        try {
            List<CategoryDTO> categories = categoryService.findCategoriesByLevel(levelInteger);
            logger.debug("Categories found: {}", categories);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("category", categories)).message("Rating created successfully")
                            .status(HttpStatus.CREATED).statusCode(HttpStatus.CREATED.value()).build());
        } catch (RepositoryException e) {
            logger.error("Error creating rating", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString()).message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
        } catch (Exception e) {
            logger.error("Unexpected error during rating creation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Unexpected error during rating creation").status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
        }
    }

}
