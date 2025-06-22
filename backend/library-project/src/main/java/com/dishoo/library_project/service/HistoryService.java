package com.dishoo.library_project.service;

import com.dishoo.library_project.dao.HistoryRepository;
import com.dishoo.library_project.dao.UserRepository;
import com.dishoo.library_project.entity.History;
import com.dishoo.library_project.entity.User;
import com.dishoo.library_project.responsemodels.HistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<History> findBooksByUserEmail(String userEmail, Pageable pageable) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found"));
        return historyRepository.findBooksByUserEmail(userEmail, pageable);

    }
}
