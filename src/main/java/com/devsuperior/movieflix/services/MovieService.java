package com.devsuperior.movieflix.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository repository;
	
	String a = " as";
	
	@Transactional
	public MovieDetailsDTO findById(Long id) {
		Optional<Movie> obj = repository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new MovieDetailsDTO(entity);
	
	}
	@Transactional(readOnly = true)
	public Page<MovieProjection> findAllPaged(String genreId, Pageable pageable) {
		
		//String[] vet = genreId.split(genreId);
		//List<String> list = Arrays.asList(vet);
		//List<Long> genreIds = list.stream().map(x -> Long.parseLong(x)).toList();	
		
		List<Long> genreIds = new ArrayList<>();
		if(!"0".equals(genreId)) {
			genreIds = Arrays.asList(genreId.split(genreId)).stream().map(Long::parseLong).toList();	

		}
		
	   return repository.searchAllPaged(genreIds , pageable);
	}
	
}
