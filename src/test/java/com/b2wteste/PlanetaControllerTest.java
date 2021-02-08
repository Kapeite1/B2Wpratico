package com.b2wteste;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import com.b2wteste.model.Planeta;
import com.b2wteste.model.PlanetaList;
import com.b2wteste.services.PlanetaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class PlanetaControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private PlanetaService planetaService;

	@Test
	public void getAllPlanetas() throws Exception {
		List<Planeta> planetaList = new ArrayList<Planeta>();
		planetaList.add(new Planeta("1", "Marte", "Quente", "Plano", 3));
		planetaList.add(new Planeta("2", "Venus", "Frio", "Montanhoso", 6));
		when(planetaService.findAll()).thenReturn(planetaList);

		mockMvc.perform(MockMvcRequestBuilders.get("/planetas").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
	}

	@Test
	public void getPlanetaById() throws Exception {
		Planeta planeta1 = new Planeta("1", "Marte", "Quente", "Plano", 3);

		BDDMockito.given(planetaService.findById("1")).willReturn(Optional.of(planeta1));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/planetas/1")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(planeta1.getNome()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.clima").value("Quente"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.terreno").value("Plano"));
	}

	@Test
	public void getPlanetaByName() throws Exception {
		Planeta planeta1 = new Planeta("1", "Marte", "Quente", "Plano", 3);

		BDDMockito.given(planetaService.findByName("Marte")).willReturn(Optional.of(planeta1));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/planetas/nome/Marte")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(planeta1.getNome()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.clima").value("Quente"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.terreno").value("Plano"));
	}

	@Test
	void successfullyCreateAPlaneta() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		Planeta planeta = new Planeta("1", "Troiken", "Quente", "Plano", 99);
		PlanetaList search = restTemplate.getForObject("https://swapi.dev/api/planets/?search=" + planeta.getNome(),
				PlanetaList.class);
		planeta.setParticipacoes(search.getResults().get(0).getFilms().length);
		when(planetaService.save(any(Planeta.class))).thenReturn(planeta);

		ObjectMapper objectMapper = new ObjectMapper();
		String planetaJSON = objectMapper.writeValueAsString(planeta);

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.post("/planetas").contentType(MediaType.APPLICATION_JSON).content(planetaJSON));

		result.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Troiken"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.clima").value("Quente"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.terreno").value("Plano"));
	}

	@Test
	void deletePlanetaTest() throws Exception {
		Planeta planeta = new Planeta("1", "Marte", "Quente", "Plano", 3);
		BDDMockito.given(planetaService.findById("1")).willReturn(Optional.of(planeta));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/planetas/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	void deleteInexistentPlanetaTest() throws Exception {
		BDDMockito.given(planetaService.findById("1")).willReturn(Optional.empty());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/planetas/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
