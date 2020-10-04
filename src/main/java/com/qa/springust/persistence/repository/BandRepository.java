package com.qa.springust.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.springust.persistence.domain.Band;

@Repository
public interface BandRepository extends JpaRepository<Band, Long> {

}
