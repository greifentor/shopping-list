package de.ollie.shoppinglist.rest.v1;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.ollie.shoppinglist.core.service.JWTService;
import de.ollie.shoppinglist.core.service.StatusService;
import de.ollie.shoppinglist.rest.v1.converter.StatusDTOConverter;
import de.ollie.shoppinglist.rest.v1.dto.StatusDTO;
import lombok.RequiredArgsConstructor;

@RestController(HealthController.URL)
@RequiredArgsConstructor
public class HealthController {

	static final String URL = "/check";

	private final JWTService jwtService;
	private final StatusService statusService;
	private final StatusDTOConverter statusDTOConverter;

	@GetMapping("/status")
	public ResponseEntity<StatusDTO> getStatus(@RequestParam String jwt) {
		jwtService.getAuthorizationData(jwt);
		return ResponseEntity.of(Optional.of(statusDTOConverter.toDTO(statusService.getStatus())));
	}

}