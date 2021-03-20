package com.roulette.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.roulette.api.dto.BetDTO;
import com.roulette.api.dto.ResponseDTO;
import com.roulette.api.dto.RouletteDTO;
import com.roulette.api.service.ServiceBet;
import com.roulette.api.service.ServiceRoulette;
import com.roulette.api.util.Constants;
/**
 * Requests to the service
 * @author Victor Buritica
 *
 */
@RestController
public class RouletteRestController {
	@Autowired
	private ServiceRoulette serviceRoulette;
	@Autowired
	private ServiceBet serviceBet;

	@GetMapping(Constants.CREATE_ROULETTE)
	public Long createRoulette() {

		return serviceRoulette.createRoulette();
	}

//	@PutMapping(Constants.OPEN_ROULETTE)
//	public ResponseDTO openRoulette(@PathVariable("id") Long id) {
//		System.out.println("a");
//		return serviceRoulette.openRoulette(id);
//	}

//	@PostMapping(Constants.BET_ROULETTE_WAGER)
//	public ResponseDTO placeBet(@RequestBody BetDTO betDTO) {
//
//		return serviceBet.placeBet(betDTO);
//	}
//
//	@PutMapping(Constants.CLOSE_ROULETTE)
//	public ResponseDTO closeRoulette(@PathVariable("id") Long id) {
//
//		return serviceRoulette.closeRoulette(id);
//	}
//
//	@GetMapping(Constants.GET_ROULETTES)
//	public List<RouletteDTO> listRoulettes() {
//
//		return serviceRoulette.listRoulettes();
//	}
}
