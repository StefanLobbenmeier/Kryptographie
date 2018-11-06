package multipletimepad;

import static org.junit.Assert.*;

import org.junit.*;

public class TestMultipleTimePad {

	private MultpleTimePadMain main;

	@Before
	public void init() {
		main = new MultpleTimePadMain();
	}

	@Test
	public void testIsValid() {
		assertTrue(main.isValid((byte) ('a' ^ 'b'), 'a'));
		assertTrue(main.isValid((byte) ('a' ^ 'b'), 'b'));
		assertFalse(main.isValid((byte) ('a' ^ 'c'), 'b'));
	}

}
