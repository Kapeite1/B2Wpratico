package com.b2wteste.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.b2wteste.model.Planeta;
import com.b2wteste.model.PlanetaList;
import com.b2wteste.services.PlanetaService;

@RestController
public class PlanetaController {

	@Autowired
	private PlanetaService planetaService;

	@GetMapping("/planetas")
	public ResponseEntity<List<Planeta>> getAllPlanetas() {
		return new ResponseEntity<>(planetaService.findAll(), HttpStatus.OK);
	}

	@PostMapping("/planetas")
	public ResponseEntity<Planeta> create(@RequestBody Planeta planeta) {
		RestTemplate restTemplate = new RestTemplate();
		PlanetaList search = restTemplate.getForObject("https://swapi.dev/api/planets/?search=" + planeta.getNome(),
				PlanetaList.class);
		planeta.setParticipacoes(search.getResults().get(0).getFilms().length);
		return new ResponseEntity<>(planetaService.save(planeta), HttpStatus.CREATED);
	}

	@GetMapping("/planetas/{id}")
	public Optional<Planeta> getPlanetaById(@PathVariable String id) throws Exception {
		Optional<Planeta> planeta = planetaService.findById(id);
		if (planeta == null) {
			throw new Exception("Planeta não existe");
		}
		return planeta;
	}

	@GetMapping("/planetas/nome/{nome}")
	public Optional<Planeta> getPlanetaByName(@PathVariable String nome) throws Exception {
		nome = nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
		Optional<Planeta> planeta = planetaService.findByName(nome);
		if (planeta == null) {
			throw new Exception("Planeta não existe");
		}
		return planeta;
	}

	@DeleteMapping("/planetas/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Optional<Planeta> delete(@PathVariable String id) throws Exception {
		Optional<Planeta> planeta = Optional.ofNullable(
				planetaService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
		if (planeta == null) {
			throw new Exception("Planeta não existe");
		}
		planetaService.delete(planeta);
		return planeta;
	}

}
