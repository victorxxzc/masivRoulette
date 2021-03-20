package com.roulette.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roulette.api.dto.BetDTO;
import com.roulette.api.dto.ResponseDTO;
import com.roulette.api.dto.RouletteDTO;
import com.roulette.api.model.Roulette;
import com.roulette.api.service.ServiceBet;
import com.roulette.api.service.ServiceRoulette;
import com.roulette.api.util.Constants;

/**
 * Requests to the service
 * 
 * @author Victor Buritica
 *
 */
@RequestMapping("/api")
@RestController
public class RouletteRestController {
	@Autowired
	private ServiceRoulette serviceRoulette;
	@Autowired
	private ServiceBet serviceBet;

	@GetMapping(Constants.CREATE_ROULETTE)
	public ResponseEntity<?> createRoulette() {
		Long roulette = null;
		Map<String, Object> response = new HashMap<>();
		try {
			roulette = serviceRoulette.createRoulette();
		} catch (DataAccessException e) {
			response.put(Constants.MSG, Constants.MSG_BAD_REQUEST);
			response.put(Constants.ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put(Constants.MSG, Constants.RESPONSE_OK);
		response.put(Constants.ROULETTE, roulette);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping(Constants.OPEN_ROULETTE)
	public ResponseEntity<?> openRoulette(@PathVariable("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		ResponseDTO responseDto = new ResponseDTO();
		try {
			responseDto= serviceRoulette.openRoulette(id);
		} catch (DataAccessException e) {
			response.put(Constants.MSG, Constants.MSG_BAD_REQUEST);
			response.put(Constants.ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		response.put(Constants.MSG, Constants.RESPONSE_OK);
		response.put(Constants.ROULETTE, responseDto);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}

	@PostMapping(Constants.BET_ROULETTE_WAGER)
	public ResponseDTO placeBet(@RequestBody BetDTO betDTO) {
		return serviceBet.placeBet(betDTO);
	}
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
