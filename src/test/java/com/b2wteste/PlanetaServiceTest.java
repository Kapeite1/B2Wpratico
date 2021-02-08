package com.b2wteste;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.b2wteste.model.Planeta;
import com.b2wteste.repository.PlanetaRepository;
import com.b2wteste.services.PlanetaService;

@SpringBootTest
public class PlanetaServiceTest {

	@Autowired
	private PlanetaRepository planetaRepository;

	@BeforeEach
	void tearDown() {
		planetaRepository.deleteAll();
	}

	@Test
	void getAllPlanetas() {
		Planeta planetaSample = new Planeta("1", "Marte", "Quente", "Plano", 3);
		planetaRepository.save(planetaSample);
		PlanetaService planetaService = new PlanetaService(planetaRepository);

		Planeta lastPlaneta = planetaService.findAll().get(0);

		Assertions.assertThat(planetaSample.getId()).isEqualTo("1");
		Assertions.assertThat(lastPlaneta.getNome()).isEqualTo("Marte");
		Assertions.assertThat(lastPlaneta.getClima()).isEqualTo("Quente");
		Assertions.assertThat(lastPlaneta.getTerreno()).isEqualTo("Plano");
	}

	@Test
	void getPlanetaById() {
		Planeta planetaSample = new Planeta("1", "Marte", "Quente", "Plano", 3);
		planetaRepository.save(planetaSample);
		PlanetaService planetaService = new PlanetaService(planetaRepository);

		Optional<Planeta> lastPlaneta = planetaService.findById(planetaSample.getId());

		Assertions.assertThat(lastPlaneta.get().getId()).isNotNull();
		Assertions.assertThat(lastPlaneta.get().getNome()).isEqualTo("Marte");
		Assertions.assertThat(lastPlaneta.get().getClima()).isEqualTo("Quente");
		Assertions.assertThat(lastPlaneta.get().getTerreno()).isEqualTo("Plano");
	}

	@Test
	void SaveAPlanet() {
		Planeta planetaSample = new Planeta("1", "Marte", "Quente", "Plano", 3);
		PlanetaService planetaService = new PlanetaService(planetaRepository);

		Planeta planetaSaved = planetaService.save(planetaSample);

		Assertions.assertThat(planetaSaved.getId()).isNotNull();
		Assertions.assertThat(planetaSaved.getNome()).isEqualTo("Marte");
		Assertions.assertThat(planetaSaved.getClima()).isEqualTo("Quente");
		Assertions.assertThat(planetaSaved.getTerreno()).isEqualTo("Plano");
	}

}
