package com.dishoo.library_project.controller;

import com.dishoo.library_project.responsemodels.HistoryResponse;
import com.dishoo.library_project.service.HistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/histories")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/search/findBooksByUserEmail")
    public Page<HistoryResponse> findBooksByUserEmail(@RequestParam String userEmail, Pageable pageable) throws Exception {
        return historyService.findBooksByUserEmail(userEmail, pageable)
                .map(HistoryResponse::new);
    }
}
