package com.b2wteste.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PlanetaList {

	private int count;
	private String next;
	private String previous;
	private List<PlanetaExterno> results;

	public PlanetaList() {
		super();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public List<PlanetaExterno> getResults() {
		return results;
	}

	public void setResults(List<PlanetaExterno> results) {
		this.results = results;
	}

}
