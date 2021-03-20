package com.roulette.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
 * 
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

	public ResponseDTO closeRoulette(Long id) {
		Optional<Roulette> respoRoulette = rouletteRepository.findById(id);
		ResponseDTO response = new ResponseDTO();
		if (respoRoulette.isPresent()) {
			Roulette roulette = respoRoulette.get();
			roulette.setStatus(Constants.CLOSED);
			rouletteRepository.save(roulette);
			HashMap<Long, BetDTO> betsConnected = objectMapper.convertValue(roulette.getBetList(),
					new TypeReference<HashMap<Long, BetDTO>>() {
					});
			response = chooseWinner(betsConnected);
		}
		return response;
	}

	private ResponseDTO chooseWinner(HashMap<Long, BetDTO> betsDTO) {
		Long numbers = (long) (Math.random() * 36);
		ResponseDTO response = new ResponseDTO();
		response.setWinnerNumber(numbers);
		WinnerDTO winnerDTO = null;
		for (Map.Entry<Long, BetDTO> betDTO : betsDTO.entrySet()) {
			winner((BetDTO) betDTO);
			response.addWinner(winnerDTO);
		}

		return response;
	}

	private WinnerDTO winner(BetDTO betDTO) {
		WinnerDTO winnerDTO = new WinnerDTO();
		boolean numberSelected = isNumberSelected(betDTO);
		if (numberSelected && isWinnerNumber(betDTO)) {
			winnerDTO = new WinnerDTO(betDTO.getIdUser(), betDTO.getMoney() * 5);
		} else if (isColorSelected(betDTO) && isWinnerColor(betDTO)) {
			winnerDTO = new WinnerDTO(betDTO.getIdUser(), betDTO.getMoney() * 1.8);
		}

		return winnerDTO;
	}

	private boolean isWinnerNumber(BetDTO bet) {
		Long numbers = (long) (Math.random() * 36);

		return bet.getNumber() == numbers ? true : false;
	}

	private boolean isNumberSelected(BetDTO betDTO) {

		return betDTO.getNumber() != null ? true : false;
	}

	private boolean isWinnerColor(BetDTO betDTO) {
		Long numbers = (long) (Math.random() * 36);
		boolean winnerColor = false;
		if (betDTO.getColor().equalsIgnoreCase(Constants.RED) && numbers % 2 != 0) {
			winnerColor = true;
		} else if (betDTO.getColor().equalsIgnoreCase(Constants.RED) && numbers % 2 == 0) {
			winnerColor = true;
		}

		return winnerColor;
	}

	private boolean isColorSelected(BetDTO betDTO) {

		return betDTO.getColor() != null ? true : false;
	}

	public List<RouletteDTO> listRoulettes() {
		List<Roulette> respoDto = new ArrayList<>();
		rouletteRepository.findAll().forEach(respoDto::add);
		List<RouletteDTO> response = respoDto.stream().map(roulette -> {
			return new RouletteDTO(roulette.getIdRoulette(), roulette.getBetList(), roulette.getStatus());
		}).collect(Collectors.toList());

		return response;
	}
}
