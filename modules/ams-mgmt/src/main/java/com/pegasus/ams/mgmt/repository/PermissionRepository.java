package com.pegasus.ams.mgmt.repository;

import com.pegasus.ams.mgmt.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query("select p from Permission p where lower(p.module) = lower(:module) and lower(p.action) = lower(:action)")
    Optional<Permission> findFirstByModuleAndAction(String module, String action);

    @Query("select p from Permission p where (:searchValue is null or (p.module like %:searchValue%) " +
                                                                "or (p.action like %:searchValue%))")
    Page<Permission> searchPermission(@Param("searchValue") String searchValue, Pageable pageable);

    @Query("select p from Permission p where p.id in (:ids)")
    Set<Permission> findPermissionsByIds(List<Long> ids);
}
