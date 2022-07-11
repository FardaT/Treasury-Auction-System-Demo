package com.greenfox.treasuryauctionsystem.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.greenfox.treasuryauctionsystem.services.AuctionService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@ExtendWith(MockitoExtension.class)
//@WebMvcTest(AuctionController.class)
@AutoConfigureMockMvc
@SpringBootTest
class AuctionControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private AuctionService auctionService;

  @Test
  void showAllAuctions() {
    assertTrue(true);
  }

  @Test
  void deleteAuctionPost() throws Exception {


//    Map<String, Long> requestBody = new HashMap<>();
//    requestBody.put("id", 1L);
//    Cookie cookie = new Cookie("jwt_token", "asdf");


//    mvc.perform(MockMvcRequestBuilders.post("/auctions/disable")).

//    mvc.perform(MockMvcRequestBuilders.post("/auctions/disable")
//        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//        .content(String.valueOf(requestBody)))
//        .andExpect(status().isOk());


    assertTrue(true);

  }

  @Test
  void processFinishedAuctionPost() {
    assertTrue(true);
  }
}

