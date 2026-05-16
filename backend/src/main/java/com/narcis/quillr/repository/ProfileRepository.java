package com.narcis.quillr.repository;

import com.narcis.quillr.model.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
    Optional<Profile> findByUserId(Long userId);
}
