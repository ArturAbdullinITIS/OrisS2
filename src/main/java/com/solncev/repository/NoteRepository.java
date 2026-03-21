package com.solncev.repository;

import com.solncev.model.Note;
import com.solncev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByAuthor(User author);

    List<Note> findByIsPublicTrue();

    @Query("""
    select n from Note n
    where n.author = :author
      and (
        lower(n.title) like lower(concat('%', :q, '%'))
        or lower(cast(n.content as string)) like lower(concat('%', :q, '%'))
      )
    order by n.createdAt desc
""")
    List<Note> searchMyNotes(@Param("author") User author, @Param("q") String q);
}