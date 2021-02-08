package com.b2wteste.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.b2wteste.model.Planeta;
import com.sun.el.stream.Optional;

@Repository
public interface PlanetaRepository extends MongoRepository<Planeta, String> {

	public java.util.Optional<Planeta> findPlanetaByNome(String nome);


}