package me.souprpk.forumbackend.api.repository.imageboard;

import me.souprpk.forumbackend.api.models.imageboard.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "SELECT i FROM Image i WHERE i.tags LIKE %:tag%")
    Page<Image> findByTagsContaining(@Param("tag") String tag, Pageable pageable);
}
