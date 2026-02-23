package com.depmt.repository;

import com.depmt.entity.FormSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FormSubmissionRepository extends JpaRepository<FormSubmission, UUID> {
    List<FormSubmission> findByEnrollmentProgramIdAndDeletedAtIsNull(UUID enrollmentProgramId);
}
