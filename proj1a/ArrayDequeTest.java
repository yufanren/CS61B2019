import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayDequeTest {
	/** Remove from empty Deque. */
	@Test
	public void testNeg() {
		ArrayDeque<Integer> S1 = new ArrayDeque<>();
		S1.removeLast();
		assertEquals(0, S1.size());
	}


	/** test if created arrays are the same. Test get. */
	@Test
	public void testCreate() {
		ArrayDeque<Integer> T1 = new ArrayDeque<>();
		ArrayDeque<Integer> T2 = new ArrayDeque<>();

		T1.addFirst(5);
		T2.addFirst(5);

		assertEquals(T1.get(0), T2.get(0));

		for (int i = 0; i < 8; i++) {
			T1.addLast(i);
		}
		T2.addFirst(7);

		assertEquals(T1.get(8), T2.get(0));
	}

	/** test if can make and add data */
	@Test
	public void testRemove() {
		ArrayDeque<Integer> T3 = new ArrayDeque<>();
		ArrayDeque<Integer> T4 = new ArrayDeque<>();
		T4.addFirst(1);
		for (int i = 0; i < 100; i++) {
			T3.addLast(i);
		}
		for (int i = 0; i < 98; i++) {
			T3.removeLast();
		}
		//assertEquals(T3.size(), 1);
		assertEquals(T3.get(1), T4.get(0));
	}

	/** Test get. */
	@Test
	public void testGet() {
		ArrayDeque<Integer> T5 = new ArrayDeque<>();
		ArrayDeque<Integer> T6 = new ArrayDeque<>();
		T6.addFirst(4);
		T5.addFirst(0);
		T5.removeLast();
		T5.addFirst(2);
		T5.addFirst(3);
		T5.addLast(4);
		assertEquals(T6.get(0), T5.get(2));

	}
	/** Test String Type and print.
	@Test
	public static void main(String[] args) {
		ArrayDeque<String> T9 = new ArrayDeque<>();
		T9.addLast("Shquirt");
		T9.addFirst("Derp");
		T9.addLast("Shmeagle");
		T9.printDeque();

	}*/



}
