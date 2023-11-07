package com.lizana.transaction.infrastructure.repository;

import com.lizana.transaction.domain.documents.Movement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends ReactiveMongoRepository<Movement, String> {
}
