package se.sodalabs.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HubServiceTest {

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private HubService hubService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testRegisterNewParticipant_Success() {
    String expectedResponse = "Participant registered successfully";
    ResponseEntity<String> mockedResponseEntity = ResponseEntity.ok(expectedResponse);
    when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
        .thenReturn(mockedResponseEntity);

    ResponseEntity<String> response = hubService.registerNewParticipant();

    assertEquals(expectedResponse, response.getBody());
  }

  @Test
  void testRegisterNewParticipant_Failure() {
    ResponseEntity<String> mockedResponseEntity = ResponseEntity.notFound().build();
    when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
        .thenReturn(mockedResponseEntity);

    ResponseEntity<String> response = hubService.registerNewParticipant();

    assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
  }

  @Test
  void testSendFeedback_Success() {
    int happinessScore = 75;
    String expectedResponse = "Feedback sent successfully";
    ResponseEntity<String> mockedResponseEntity = ResponseEntity.ok(expectedResponse);
    when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
        .thenReturn(mockedResponseEntity);

    ResponseEntity<String> response = hubService.sendFeedback(happinessScore);

    assertEquals(expectedResponse, response.getBody());
  }

  @Test
  void testSendFeedback_Failure() {
    int happinessScore = 75;
    ResponseEntity<String> mockedResponseEntity = ResponseEntity.notFound().build();
    when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
        .thenReturn(mockedResponseEntity);

    ResponseEntity<String> response = hubService.sendFeedback(happinessScore);

    assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
  }
}
