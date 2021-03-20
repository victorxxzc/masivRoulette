package com.roulette.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.roulette.api.model.Roulette;
/**
 *Class for crud operations 
 * @author Victor Buritica
 *
 */
@Repository
public interface RouletteRepository extends CrudRepository<Roulette, Long>{

}
