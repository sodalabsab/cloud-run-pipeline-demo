package se.sodalabs.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.sodalabs.demo.service.HubService;

@Tag(
    name = "Participant Demo Application",
    description = "Endpoints to trigger communication with central hub")
@RestController
@RequestMapping("/api/participant")
public class DemoController {

  public DemoController(BuildProperties buildProperties, HubService hubService) {
    this.buildProperties = buildProperties;
    this.hubService = hubService;
  }

  private final BuildProperties buildProperties;
  private final HubService hubService;

  @Operation(
      summary = "Say hello",
      description =
          "Request a friendly and cheerful response, indicating that the service is alive and well.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200")})
  @GetMapping(path = "/")
  public String sayHello() {
    return "Hello world";
  }

  @GetMapping("/register")
  @Operation(
      summary = "Register with hub",
      description = "Trigger a registration call to the hub service.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201"),
        @ApiResponse(
            responseCode = "409",
            description = "This participant has already been registered.")
      })
  public ResponseEntity register() {
    return hubService.registerWithHub();
  }

  @GetMapping("/heartbeat")
  @Operation(
      summary = "Send heartbeat to hub",
      description =
          "After registration, trigger sending a heartbeat to let hub know you're still alive.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204"),
        @ApiResponse(
            responseCode = "412",
            description =
                "The participant has not been registered yet. Call /register first, then try again.")
      })
  public ResponseEntity sendHeartbeat() {
    return hubService.sendHeartbeat();
  }

  @GetMapping("/availability/{availability}")
  @Operation(
      summary = "Set your availability for today",
      description =
          "Once you have registered with the hub, use this to signal if you're free or busy today.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request. Perhaps you sent something other than free or busy?"),
        @ApiResponse(
            responseCode = "412",
            description =
                "The participant has not been registered yet. Call /register first, then try again.")
      })
  public ResponseEntity setAvailability(
      @Parameter(description = "The availability to set - busy or free.", example = "free")
          @PathVariable
          String availability) {
    return hubService.setAvailability(availability);
  }

  @GetMapping("/version")
  @Operation(summary = "Get the currently running version")
  public String getVersion() {
    return buildProperties.getVersion();
  }
}
