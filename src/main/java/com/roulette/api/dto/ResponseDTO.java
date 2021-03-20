package com.roulette.api.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The results or the status of any action
 * 
 * @author Victor Buritica
 *
 */
@JsonInclude(Include.NON_NULL)
public class ResponseDTO {
	private String responseMessage;
	private Long winnerNumber;
	private HashMap<Long, BetDTO> betsConnected;
	private List<WinnerDTO> winners;

	public ResponseDTO() {
		betsConnected = new HashMap<>();
		winners = new ArrayList<>();
	}

	public String getOutputMessage() {

		return responseMessage;
	}

	public void setOutputMessage(String outputMessage) {
		this.responseMessage = outputMessage;
	}

	public void setBetsConnected(HashMap<Long, BetDTO> betsConnected) {
		this.betsConnected = betsConnected;
	}

	public HashMap<Long, BetDTO> getBetsConnected() {

		return betsConnected;
	}

	public void setWinnerNumber(Long winnerNumber) {
		this.winnerNumber = winnerNumber;
	}

	public Long getWinnerNumber() {

		return winnerNumber;
	}

	public List<WinnerDTO> getWinners() {
		return winners;
	}

	public void setWinners(List<WinnerDTO> winners) {
		this.winners = winners;
	}

	public void addWinner(WinnerDTO winnerDTO) {
		if (winnerDTO != null) {
			this.winners.add(winnerDTO);
		}
	}
}
