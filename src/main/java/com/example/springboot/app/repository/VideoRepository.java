package com.example.springboot.app.repository;

import com.example.springboot.app.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

    List<VideoEntity> findByName(String name);
    List<VideoEntity> findByNameContainsOrDescriptionContainsAllIgnoreCase(String name, String description);
    List<VideoEntity> findByNameContainsIgnoreCase(String name);
    List<VideoEntity> findByDescriptionContainsIgnoreCase(String description);
    @Query("SELECT v FROM VideoEntity AS v WHERE v.name = ?1")
    List<VideoEntity> findCustomerReport(String name);
    /*@Query("SELECT v FROM VideoEntity AS v "
            + "INNER JOIN v.metrics AS m "
            + "INNER JOIN m.activity AS a "
            + "INNER JOIN v.engagement AS e "
            + "WHERE a.views < :minimumViews "
            + "OR e.likes < : minimumLikes")
    List<VideoEntity> findVideosThatArentPopular(@Param("minimumViews") Long minimumViews,
                                                 @Param("minimumLikes") Long minimumLikes);*/
    @Query(value="SELECT * FROM VIDEO_ENTITY WHERE NAME = ?1", nativeQuery = true)
    List<VideoEntity> findCustomWithPureSql(String name);

}
