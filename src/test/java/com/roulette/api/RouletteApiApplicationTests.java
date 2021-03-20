package com.roulette.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.roulette.api.dto.BetDTO;
import com.roulette.api.dto.ResponseDTO;
import com.roulette.api.model.Roulette;
import com.roulette.api.repository.RouletteRepository;
import com.roulette.api.service.ServiceBet;
import com.roulette.api.service.ServiceRoulette;
import com.roulette.api.util.Constants;

/**
 * Test
 * 
 * @author Victor Buritica
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class RouletteApiApplicationTests {
	public void createRouletteTest() {
	}
}
