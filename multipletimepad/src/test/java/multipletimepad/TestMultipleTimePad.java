package multipletimepad;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestMultipleTimePad {

	private MultpleTimePadMain main;

	@BeforeEach
	void init() {
		main = new MultpleTimePadMain();
	}

	@Test
	void testIsValid() {
		assertTrue(main.isValid((byte) ('a' ^ 'b'), 'a'));
		assertTrue(main.isValid((byte) ('a' ^ 'b'), 'b'));
		assertFalse(main.isValid((byte) ('a' ^ 'c'), 'b'));
	}

}
