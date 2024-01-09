package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;

public interface MovieRepository  extends JpaRepository<Movie, Long>{

	
	
	
	@Query(nativeQuery = true, value = """
			SELECT * 
		    FROM  tb_movie
		    INNER JOIN  tb_genre ON tb_genre.id = tb_movie.genre_id  
		    WHERE (:genreId IS NULL OR tb_genre.id IN :genreId)
				
				""", countQuery = """
						SELECT COUNT(*) FROM (
			SELECT DISTINCT tb_movie.title, tb_movie.Synopsis, tb_genre.name
		    FROM  tb_movie
		    INNER JOIN  tb_genre ON tb_genre.id = tb_movie.genre_id  
		    WHERE (:genreId IS NULL OR tb_genre.id IN :genreId)
			) AS tb_result
						""")
		Page<MovieProjection> searchAllPaged(List<Long> genreId, Pageable pageable);
    
}
