package com.mavenmokito.test.appmokito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.when;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runnuer.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mavenmokito.test.appmokito.service.TaskService;
import com.mavenmokito.test.appmokito.serviceimpl.AppServiceFindTask;

/*
 * Service verification counter to stub
 * Creating mock objects with Mokito
 * - Class as well as interface Mocks
 * - Mocks are both 
 *  (your object gives indirect input to your test through Stubbing 
 *  and gathers indirect output through spying)
 *  - Using Annotations instead of methods
 *  @Mock
 *  @RunWith(MockitoJUnitRunner.class) - The runner injects mocked beans
 */
 @RunWith(MockitoJUnitRunner.class)
public class AppServiceFindTaskMockWithAnnotations {

// We can use @Mock to create and inject mocked instances without having to call Mockito.mock manually.
  @Mock
  TaskService service;
  // to inject mock fields into the tested object automatically.
  @InjectMocks
  AppServiceFindTask findTask;
  
/*
 * Utilize already mocked object using @Mock and @InjectMock
 * Call function filterOverPreicate of AppServiceFindTask - passing Predicate
 * assertTrue if retured list is empty
 */
  @Test
  public void testServiceWithEmptyMock(){
    List<Integer> resultList = findTask.filterOverPreicate(x -> x < 3);
		assertTrue(resultList.isEmpty());
  }
  
  /*
   * when findAllTask is called for TaskService - return List range 1..9 [excluding 10]
   * Call function filterOverPreicate of AppServiceFindTask - passing Predicate [x<3]
   * assertTrue - expected List size = 2 (i.e. 1 and 2)
   */
  
  	@Test
	public void testServiceWithWhenThenMock() {
		List<Integer> mokedList = IntStream.range(1, 10)
                                        .boxed()
                                        .collect(Collectors.toList());
		when(service.findAllTask()).thenReturn(mokedList);

		List<Integer> resultList = findTask.filterOverPreicate(x -> x < 3);
		assertTrue(resultList.equals(Arrays.asList(1, 2)));
		assertTrue(resultList.size() == 2);
	}
  
  /*
   * when findAllTask is called for TaskService - return List range 1..9 [excluding 10]
   * Call function filterOverPreicate of AppServiceFindTask - passing Predicate [x%2 == 0] even numbers
   * assertTrue - expected List size = 4 (i.e. 2, 4 , 6 and 8)
   */
  
	@Test
	public void testServiceWithEvenNumberPredicateMock() {

		// @formatter: off
		List<Integer> list = IntStream.range(1, 10)
                                  .boxed()
                                  .collect(Collectors.toList());
		// @formatter: on

		when(service.findAllTask()).thenReturn(list);
		List<Integer> result = findTask.filterOverPreicate(x -> x % 2 == 0);
		assertTrue(result.equals(Arrays.asList(2, 4, 6, 8)));
	}
  
    /*
   * when findAllTask is called for TaskService - 
   *   return List range 1..9 [excluding 10]
   *   return emptyList - passing null argument
   * Call function filterOverPreicate of AppServiceFindTask - passing Predicate [x%2 == 0] even numbers
   * assertTrue - expected List size = 4 (i.e. 2, 4 , 6 and 8)
   *
   * assertTrue - expectedList is empty [Function contract - null will be returned as empty]
   */

	@Test
	public void testServiceWithDataFollowedByNullListMock() {

		// @formatter:off
		List<Integer> list = IntStream.range(1, 10)
										.boxed()
										.collect(Collectors.toList());
		// @formatter:on

		when(service.findAllTask()).thenReturn(list).thenReturn(null);
		List<Integer> result = findTask.filterOverPreicate(x -> x % 2 == 0);
		assertTrue(result.equals(Arrays.asList(2, 4, 6, 8)));
		List<Integer> nullList = findTask.filterOverPreicate(x -> x % 2 == 0);
		assertTrue(nullList.isEmpty());
	}
 
 /*
  * when findAllTask is called for TaskService - 
 *   throw NullPointerException
 * Call function filterOverPreicate of AppServiceFindTask - passing Predicate [x%2 == 0] even numbers
 * @Test is expecting NullPointerException
 */
 	@Test(expected = NullPointerException.class)
	public void testServiceExceptionMock() {
		when(service.findAllTask()).thenThrow(new NullPointerException());
		findTask.filterOverPreicate(x -> x % 2 == 0);
	}
  
  /*
   * when findAllTask is called for TaskService - 
   *   throw NullPointerException
   * Do not call function filterOverPreicate of AppServiceFindTask - passing Predicate [x%2 == 0] even numbers
   * @Test is verify method of service - findAllTask() is never called
   */
	@Test
	public void testServiceNoMethodCalled() {
		when(service.findAllTask()).thenThrow(new NullPointerException());
//		findTask.filterOverPreicate(x -> x % 2 == 0);
		verify(service,never()).findAllTask();
	}


  /*
   * when findAllTask is called for TaskService - 
   *   throw NullPointerException
   * Do not call function filterOverPreicate of AppServiceFindTask - passing Predicate [x%2 == 0] even numbers
   * @Test is verify method of service - findAllTask() has been called times(3)
   */  
	@Test
	public void testServiceCalledXTimes() {
		when(service.findAllTask()).thenReturn(new ArrayList<Integer>());
		findTask.filterOverPreicate(x -> x % 2 == 0);
		findTask.filterOverPreicate(x -> x % 2 == 0);
		findTask.filterOverPreicate(x -> x % 2 == 0);
		verify(service,times(3)).findAllTask();
	}
  
   /*
   * when findAllTask is called for TaskService - 
   *   throw NullPointerException
   * Do not call function filterOverPreicate of AppServiceFindTask - passing Predicate [x%2 == 0] even numbers
   * @Test is verify method of service - findAllTask() has been called atLeast(2)
   */  
  
	@Test
	public void testServiceCalledAtLeast() {
		when(service.findAllTask()).thenReturn(new ArrayList<Integer>());
		findTask.filterOverPreicate(x -> x % 2 == 0);
		findTask.filterOverPreicate(x -> x % 2 == 0);
		findTask.filterOverPreicate(x -> x % 2 == 0);
		verify(service,atLeast(2)).findAllTask();
	}
}
