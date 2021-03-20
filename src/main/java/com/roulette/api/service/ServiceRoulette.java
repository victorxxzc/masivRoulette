package com.roulette.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roulette.api.dto.BetDTO;
import com.roulette.api.dto.ResponseDTO;
import com.roulette.api.dto.RouletteDTO;
import com.roulette.api.dto.WinnerDTO;
import com.roulette.api.model.Bet;
import com.roulette.api.model.Roulette;
import com.roulette.api.repository.RouletteRepository;
import com.roulette.api.util.Constants;
/**
 * The operation of the roulette
 * @author Victor Buritica
 *
 */
@Service
public class ServiceRoulette {
	@Autowired
	private RouletteRepository rouletteRepository;
	@Autowired
	ObjectMapper objectMapper;
	public Long createRoulette() {
		Roulette roulette = new Roulette(Constants.CLOSED);
		Roulette response = rouletteRepository.save(roulette);

		return response.getIdRoulette();
	}
	public ResponseDTO openRoulette(Long id) {
		ResponseDTO response = new ResponseDTO();
		Optional<Roulette> roulette = rouletteRepository.findById(id);
		if (roulette.isPresent()) {
			Roulette request = roulette.get();
			rouletteClosed(request);
			request.setStatus(Constants.OPEN);
			rouletteRepository.save(request);
		}
		return response;
	}
	private void rouletteClosed(Roulette request) {
		if (request.getStatus().equals(Constants.CLOSED)) {
			request.setBetList(null);
		}
	}
//	public ResponseDTO closeRoulette(Long id) {
//		Optional<Roulette> optionalRoulette = rouletteRepository.findById(id);
//		ResponseDTO output = new ResponseDTO();
//		if (optionalRoulette.isPresent()) {
//			Roulette roulette = optionalRoulette.get();
//			roulette.setStatus(Constants.CLOSED);
//			rouletteRepository.save(roulette);
//			HashMap<Long, BetDTO> betsConnected = objectMapper.convertValue(roulette.getBetList(),
//					new TypeReference<HashMap<Long, BetDTO>>() {
//					});
//			output = chooseWinner(betsConnected);
//			output.setStatusCode(Constants.STATUS_OK);
//			output.setOutputMessage(Constants.ROULETTE_CLOSED);
//			output.setBetsConnected(betsConnected);		
//		} else {
//			output.setStatusCode(Constants.NOT_FOUND);
//			output.setOutputMessage(Constants.ROULETTE_NOT_AVALILABLE);
//		}
//
//		return output;
//	}
//	private ResponseDTO chooseWinner(HashMap<Long, BetDTO> betsDTO) {
//		Long randomNumber = (long) (Math.random() * 36);
//		ResponseDTO outputDTO = new ResponseDTO();
//		outputDTO.setWinnerNumber(randomNumber);
//		WinnerDTO winnerDTO = null;
//		for (Map.Entry<Long, BetDTO> betDTO : betsDTO.entrySet()) {
//			winner((BetDTO) betDTO);
//			outputDTO.addWinner(winnerDTO);
//		}
//		
//		return outputDTO;
//	}
//	private WinnerDTO winner(BetDTO betDTO) {
//		WinnerDTO winnerDTO = new WinnerDTO();
//		boolean isNumberSelected = isNumberSelected(betDTO);
//		if(isNumberSelected && isWinnerNumber(betDTO)) {
//			winnerDTO = new WinnerDTO(betDTO.getIdUser(), betDTO.getMoney() * 5);		
//		}else if(isColorSelected(betDTO) && isWinnerColor(betDTO)) {
//			winnerDTO = new WinnerDTO(betDTO.getIdUser(), betDTO.getMoney() * 1.8);
//		}
//		
//		return winnerDTO;
//	}
//	private boolean isWinnerNumber(BetDTO bet) {
//		Long randomNumber = (long) (Math.random() * 36);
//		
//		return bet.getNumber() == randomNumber ? true : false;
//	}	
//	private boolean isNumberSelected(BetDTO betDTO) {
//		
//		return betDTO.getNumber() != null ? true : false;
//	}
//	private boolean isWinnerColor(BetDTO betDTO) {
//		Long randomNumber = (long) (Math.random() * 36);
//		boolean isWinnerColor = false;
//		if(betDTO.getColor().equalsIgnoreCase(Constants.RED) && randomNumber % 2 != 0) {
//			isWinnerColor = true;
//		} else if(betDTO.getColor().equalsIgnoreCase(Constants.RED) && randomNumber % 2 == 0) {
//			 isWinnerColor = true;
//		} 
//		
//		return isWinnerColor;
//	}	
//	private boolean isColorSelected(BetDTO betDTO) {
//		
//		return betDTO.getColor() != null ? true : false;
//	}
//	public List<RouletteDTO> listRoulettes() {		
//		List<Roulette> outputDTO = new ArrayList<>();
//		rouletteRepository.findAll().forEach(outputDTO::add);
//		List<RouletteDTO> response = outputDTO.stream().map(roulette -> {return new RouletteDTO(roulette.getIdRoulette(),roulette.getBetList(),roulette.getStatus());}).collect(Collectors.toList());
//		
//		return response;
//	}
}
