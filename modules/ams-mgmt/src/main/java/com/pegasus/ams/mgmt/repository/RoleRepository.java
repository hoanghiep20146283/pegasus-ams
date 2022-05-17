package com.pegasus.ams.mgmt.repository;

import com.pegasus.ams.mgmt.entity.Role;
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
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByCode(String code);

    Optional<Role> findFirstByCode(String code);

    Optional<Role> findFirstByName(String code);

    @Query("select distinct r from Role r left join r.permissions p where (r.name like %:name% " +
            "or :name is null)")
    Page<Role> searchRoles(@Param("name") String name, Pageable pageable);

    @Query("select r from Role r where lower(r.name) = lower(:name) and lower(r.name) <> lower(:name)")
    Optional<Role> findExistByName(String name);

    @Query("select r from Role r where lower(r.code) = lower(:code) and lower(r.code) <> lower(:code)")
    Optional<Role> findExistByCode(String code);

    @Query("select r from Role r where r.id in (:ids)")
    Set<Role> findRolesByIds(List<Long> ids);

    @Query("Select r from Role r join r.users u where u.id = :userId")
    List<Role> findAllRolesByUserId(@Param("userId") Long userId);
}
