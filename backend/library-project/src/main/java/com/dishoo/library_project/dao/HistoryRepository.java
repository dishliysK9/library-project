package com.dishoo.library_project.dao;

import com.dishoo.library_project.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    /* example:

        select * from history h join user u on h.user_id = u.id where u.email = "adminuser@email.com"

    */
    @Query("SELECT h FROM History h WHERE h.user.email = :email")
    Page<History> findBooksByUserEmail(@Param("email") String userEmail, Pageable pageable);
}
