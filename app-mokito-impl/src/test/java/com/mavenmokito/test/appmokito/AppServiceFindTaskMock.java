package com.mavenmokito.test.appmokito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.mockito.Mockito;

import com.mavenmokito.test.appmokito.service.TaskService;
import com.mavenmokito.test.appmokito.serviceimpl.AppServiceFindTask;

/*
 * Service verification counter to stub
 * Creating mock objects with Mokito
 * - Class as well as interface Mocks
 * - Mocks are both 
 *  (your object gives indirect input to your test through Stubbing 
 *  and gathers indirect output through spying)
 */

public class AppServiceFindTaskMock {

/*
 * Mock the object using static mock of TaskService.class
 * Create object of AppServiceFindTask
 * Call function filterOverPreicate of AppServiceFindTask - passing Predicate
 * assertTrue if retured list is empty
 */
	@Test
	public void testServiceWithEmptyMock() {
		TaskService service = mock(TaskService.class);
		AppServiceFindTask serviceImpl = new AppServiceFindTask(service);
		List<Integer> resultList = serviceImpl.filterOverPreicate(x -> x < 3);
		assertTrue(resultList.isEmpty());
	}
  
  
/*
 * Mock the object using static mock of TaskService.class
 * when findAllTask is called for TaskService - return List range 1..9 [excluding 10]
 * Create object of AppServiceFindTask
 * Call function filterOverPreicate of AppServiceFindTask - passing Predicate [x<3]
 * assertTrue - expected List size = 2 (i.e. 1 and 2)
 */
  	@Test
	public void testServiceWithWhenThenMock() {
		TaskService service = Mockito.mock(TaskService.class);
		AppServiceFindTask serviceImpl = new AppServiceFindTask(service);
		List<Integer> mokedList = IntStream.range(1, 10).boxed().collect(Collectors.toList());
		when(service.findAllTask()).thenReturn(mokedList);

		List<Integer> resultList = serviceImpl.filterOverPreicate(x -> x < 3);
		assertTrue(resultList.equals(Arrays.asList(1, 2)));
		assertTrue(resultList.size() == 2);
	}
  
  
/*
 * Mock the object using static mock of TaskService.class
 * when findAllTask is called for TaskService - return List range 1..9 [excluding 10]
 * Create object of AppServiceFindTask
 * Call function filterOverPreicate of AppServiceFindTask - passing Predicate [x%2 == 0] even numbers
 * assertTrue - expected List size = 4 (i.e. 2, 4 , 6 and 8)
 */
  	@Test
	public void testServiceWithEvenNumberPredicateMock() {
		TaskService service = Mockito.mock(TaskService.class);
		AppServiceFindTask findEvenNumbers = new AppServiceFindTask(service);

		// @formatter: off
		List<Integer> list = IntStream.range(1, 10)
                                .boxed()
                                .collect(Collectors.toList());
		// @formatter: on

		when(service.findAllTask()).thenReturn(list);
		List<Integer> result = findEvenNumbers.filterOverPreicate(x -> x % 2 == 0);
		assertTrue(result.equals(Arrays.asList(2, 4, 6, 8)));
	}

/*
 * Mock the object using static mock of TaskService.class
 * when findAllTask is called for TaskService - 
 *   return List range 1..9 [excluding 10]
 *   return emptyList - passing null argument
 * Create object of AppServiceFindTask
 * Call function filterOverPreicate of AppServiceFindTask - passing Predicate [x%2 == 0] even numbers
 * assertTrue - expected List size = 4 (i.e. 2, 4 , 6 and 8)
 *
 * assertTrue - expectedList is empty [Function contract - null will be returned as empty]
 */

	@Test
	public void testServiceWithDataFollowedByNullListMock() {
		TaskService service = Mockito.mock(TaskService.class);
		AppServiceFindTask findEvenNumbers = new AppServiceFindTask(service);

		// @formatter:off
		List<Integer> list = IntStream.range(1, 10)
										.boxed()
										.collect(Collectors.toList());
		// @formatter:on

		when(service.findAllTask()).thenReturn(list).thenReturn(null);
		List<Integer> result = findEvenNumbers.filterOverPreicate(x -> x % 2 == 0);
		assertTrue(result.equals(Arrays.asList(2, 4, 6, 8)));
		List<Integer> nullList = findEvenNumbers.filterOverPreicate(x -> x % 2 == 0);
		assertTrue(nullList.isEmpty());
	}
  
  
/*
 * Mock the object using static mock of TaskService.class
 * when findAllTask is called for TaskService - 
 *   throw NullPointerException
 * Create object of AppServiceFindTask
 * Call function filterOverPreicate of AppServiceFindTask - passing Predicate [x%2 == 0] even numbers
 * @Test is expecting NullPointerException
 */
	@Test(expected=NullPointerException.class)
	public void testServiceExceptionMock() {
		TaskService service = Mockito.mock(TaskService.class);
		AppServiceFindTask findEvenNumbers = new AppServiceFindTask(service);

		when(service.findAllTask()).thenThrow(new NullPointerException());
		findEvenNumbers.filterOverPreicate(x -> x % 2 == 0);
	}


}
