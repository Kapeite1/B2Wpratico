package com.b2wteste.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.b2wteste.model.Planeta;
import com.b2wteste.repository.PlanetaRepository;

@Service
public class PlanetaService {

	@Autowired
	private PlanetaRepository planetaRepository;

	public PlanetaService(PlanetaRepository planetaRepository) {
		 this.planetaRepository = planetaRepository;
	}

	public List<Planeta> findAll() {
		return planetaRepository.findAll();		
	}

	public Planeta save(Planeta planeta) {
	    return planetaRepository.save(planeta);
	}

	public Optional<Planeta> findById(String id) {
		Optional<Planeta> planeta = planetaRepository.findById(id);
		return planeta;
	}

	public void delete(Optional<Planeta> planeta) {
		planetaRepository.deleteById(planeta.get().getId());
		
	}

	public Optional<Planeta> findByName(String nome) {
		return planetaRepository.findPlanetaByNome(nome);
	}

	
}
