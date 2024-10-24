package com.sunbird.serve.volunteering.usermanagement.repositories;

import com.sunbird.serve.volunteering.models.VolunteerHours.VolunteeringHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VolunteerManagementRepository extends JpaRepository<VolunteeringHours, UUID> {

    List<VolunteeringHours> findAllByUserId(String userId);

    Optional<VolunteeringHours> findByUserIdAndNeedDeliverableId(String userId, String needDeliverableId);

}
