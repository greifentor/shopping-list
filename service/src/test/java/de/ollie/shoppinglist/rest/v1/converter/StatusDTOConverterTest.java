package de.ollie.shoppinglist.rest.v1.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.shoppinglist.core.model.Status;

@ExtendWith(MockitoExtension.class)
public class StatusDTOConverterTest {

	private static final long LONG = 4711L;

	@Mock
	private Status status;

	@InjectMocks
	private StatusDTOConverter unitUnderTest;

	@Nested
	class TestsOfMethod_toDTO_Status {

		@Test
		void passANullValue_returnsANullValue() {
			assertNull(unitUnderTest.toDTO(null));
		}

		@Test
		void passAStatusModelWithSetFreeMem_setsTheFreeMemInTheDTOCorrectly() {
			// Prepare
			when(status.getFreeMem()).thenReturn(LONG);
			// Run & Check
			assertEquals(LONG, unitUnderTest.toDTO(status).getFreeMem());
		}
	}

}