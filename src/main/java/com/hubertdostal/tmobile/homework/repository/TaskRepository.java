package com.hubertdostal.tmobile.homework.repository;

import com.hubertdostal.tmobile.homework.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for {@link Task} entities
 *
 * @author hubert.dostal@gmail.com
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Method to fetch all stored instances {@link Task} based on its acquireBy value.
     * It is demonstration how to use native SQL query in JPA repositories
     *
     * @param acquiredBy value of acquireBy based on which {@link Task} are searched
     *
     * @return list of {@link Task} fulfilling condition
     */
    @Query(value = "select * from SCHEMA2.TASKS t where t.ACQUIRED_BY_=:acquiredBy", nativeQuery = true)
    List<Task> findByAcquiredByUser(Long acquiredBy);

    /**
     * Method to fetch all stored instances {@link Task} based on its userNote value - it is fulltext search based fetch.
     * It is demonstration how to use fulltext query in JPA repositories
     *
     * @param userNote value of userNote based on which {@link Task} are searched (method contains)
     *
     * @return list of {@link Task} fulfilling condition
     */
    List<Task> findByUserNoteContaining(String userNote);
}
