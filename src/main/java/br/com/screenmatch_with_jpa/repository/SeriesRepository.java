package br.com.screenmatch_with_jpa.repository;

import br.com.screenmatch_with_jpa.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {

}
