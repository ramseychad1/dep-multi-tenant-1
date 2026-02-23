package com.depmt.repository;

import com.depmt.entity.EnrollmentProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentProgramRepository extends JpaRepository<EnrollmentProgram, UUID> {
    List<EnrollmentProgram> findByDeletedAtIsNull();
    Optional<EnrollmentProgram> findByIdAndDeletedAtIsNull(UUID id);
}
