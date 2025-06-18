package com.dishoo.library_project.dao;

import com.dishoo.library_project.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByUserEmail(@RequestParam("user_email") String userEmail, Pageable pageable);

    // for admins: idea is to see only not answered messages
    Page<Message> findByClosed(@RequestParam("closed") boolean closed, Pageable pageable);

}