package com.roulette.api.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roulette.api.dto.BetDTO;
import com.roulette.api.dto.ResponseDTO;
import com.roulette.api.model.Bet;
import com.roulette.api.model.Roulette;
import com.roulette.api.repository.RouletteRepository;
import com.roulette.api.util.Constants;

/**
 * The operation of the bets
 * 
 * @author Victor Buritica
 *
 */
@Service
public class ServiceBet {
	@Autowired
	private RouletteRepository rouletteRepository;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	HttpServletRequest servletRequest;

	public ResponseDTO betRoulette(BetDTO betDTO) {
		ResponseDTO response = new ResponseDTO();
		String rouletteStatus = findStatus(betDTO.getIdRoulette());
		Optional<Roulette> roulette = rouletteRepository.findById(betDTO.getIdRoulette());
		betDTO.setIdUser(servletRequest.getHeader("idUser"));
		boolean userBets = findUserBets(roulette, betDTO.getIdUser());
		if (rouletteStatus.equals(Constants.OPEN)) {
			boolean isValidBet = isValidBet(betDTO);
			if (isValidBet && userBets) {
				response = saveBet(betDTO);
			}
		}

		return response;
	}

	private boolean findUserBets(Optional<Roulette> roulette, String idUser) {
		boolean isValid = false;
		if (roulette.isPresent()) {
			if (count(roulette, idUser) < 3) {
				isValid = true;
			}
		}
		return isValid;
	}

	private int count(Optional<Roulette> roulette, String idUser) {
		int cant = 0;
		Set<Integer> numers = roulette.get().getBetList().keySet();
		Iterator<Integer> it = numers.iterator();
		while (it.hasNext() && cant < 3) {
			Integer num = it.next();
			if (roulette.get().getBetList().get(num).getIdUser().equals(idUser)) {
				cant += 1;
			}
			it.remove();
		}

		return cant;
	}

	public String findStatus(Long id) {
		Optional<Roulette> roulette = rouletteRepository.findById(id);
		String status = Constants.CLOSED;
		if (roulette.isPresent() && roulette.get().getStatus().equals(Constants.OPEN)) {
			status = Constants.OPEN;
		}

		return status;
	}

	public boolean isValidBet(BetDTO betDTO) {
		boolean isValid = true;
		if (betDTO.getColor() != null && betDTO.getNumber() != null) {
			isValid = false;
		} else if (betDTO.getColor() != null && isValidColor(betDTO)) {
			isValid = true;
		} else if (betDTO.getNumber() != null && isValidNumber(betDTO)) {
			isValid = true;
		} else {
			isValid = false;
		}

		return isValid;
	}

	private boolean isValidColor(BetDTO betDTO) {

		return betDTO.getColor().equalsIgnoreCase(Constants.RED) || betDTO.getColor().equalsIgnoreCase(Constants.BLACK)
				? true
				: false;
	}

	private boolean isValidNumber(BetDTO betDTO) {

		return betDTO.getNumber() >= 0 && betDTO.getNumber() <= 36 ? true : false;
	}

	public ResponseDTO saveBet(BetDTO betDTO) {
		Roulette roulette = rouletteRepository.findById(betDTO.getIdRoulette()).get();
		ResponseDTO response = new ResponseDTO();
		Bet bet = objectMapper.convertValue(betDTO, Bet.class);
		roulette.addBetToList(bet);
		rouletteRepository.save(roulette);

		return response;
	}
}
