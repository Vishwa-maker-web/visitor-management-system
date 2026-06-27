package com.example.visitor.repository;


import com.example.visitor.entity.GateEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GateEntryRepository extends JpaRepository<GateEntry,Long> {


}
