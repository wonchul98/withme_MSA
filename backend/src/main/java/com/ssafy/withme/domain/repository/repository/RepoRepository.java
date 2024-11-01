package com.ssafy.withme.domain.repository.repository;

import com.ssafy.withme.domain.repository.entity.Repo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface RepoRepository extends JpaRepository<Repo, Long> {

    Slice<Repo> findByMember_IdAndIdLessThan(Long memberId, Long cursor, Pageable pageable);

    Slice<Repo> findByMember_IdAndUpdatedAtBefore(Long memberId, LocalDateTime cursor, Pageable pageable);
}
