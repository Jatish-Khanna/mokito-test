package com.mavenmokito.test.appmokito;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.haaSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStreams;

import org.junit.Test;
import org.junit.runner.RunWith;
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
public class AppServiceFindTaskMockWithHamcrest {

// We can use @Mock to create and inject mocked instances without having to call Mockito.mock manually.
  @Mock
  TaskService service;
  // to inject mock fields into the tested object automatically.
  @InjectMocks
  AppServiceFindTask findTask;
  
 /*
 * Utilize already mocked object using @Mock and @InjectMock
 * Call function filterOverPreicate of AppServiceFindTask - passing Predicate
 * assertThat - returned List hasSize = 0 (hamcrest)
 */
  
  @Test
	public void testServiceWithEmptyMock() {
		List<Integer> resultList = findTask.filterOverPreicate(x -> x < 3);
		assertThat(resultList, hasSize(0));
	}
  
   /*
   * when findAllTask is called for TaskService - return List range 1..9 [excluding 10]
   * Call function filterOverPreicate of AppServiceFindTask - passing Predicate [x<3]
   * assertThat - expected List hasSize = 2 (i.e. hasItems 1 and 2)
   */
  
	@Test
	public void testServiceWithWhenThenMock() {
		List<Integer> mokedList = IntStream.range(1, 10)
                                       .boxed()
                                       .collect(Collectors.toList());
		when(service.findAllTask()).thenReturn(mokedList);

		List<Integer> resultList = findTask.filterOverPreicate(x -> x < 3);
		assertThat(resultList, hasItems(1, 2));
		assertThat(resultList, hasSize(2));
	}
  
   /*
   * when findAllTask is called for TaskService - return List range 1..9 [excluding 10]
   * Call function filterOverPreicate of AppServiceFindTask - passing Predicate [x%2 == 0] even numbers
   * assertTrue - expected List jasSize = 4 (i.e. 2, 4 , 6 and 8)
   */
  @Test
	public void testServiceWithEvenNumberPredicateMock() {

		// @formatter: off
		List<Integer> list = IntStream.range(1, 10)
                                  .boxed()
                                  .collect(Collectors.toList());
		// @formatter: on

		when(service.findAllTask()).thenReturn(list);
		List<Integer> resultList = findTask.filterOverPreicate(x -> x % 2 == 0);
		assertThat(resultList, hasItems(2, 4, 6, 8));
	}
  
  /*
   * when findAllTask is called for TaskService - 
   *   return List range 1..9 [excluding 10]
   *   return emptyList - passing null argument
   * Call function filterOverPreicate of AppServiceFindTask - passing Predicate [x%2 == 0] even numbers
   * assertThat - expected List hasSize = 4 (i.e. 2, 4 , 6 and 8)
   *
   * assertThat - expectedList is empty [hamcrest]
   */
  @Test
	public void testServiceWithDataFollowedByNullListMock() {

		// @formatter:off
		List<Integer> list = IntStream.range(1, 10)
										.boxed()
										.collect(Collectors.toList());
		// @formatter:on

		when(service.findAllTask()).thenReturn(list).thenReturn(null);
		List<Integer> resultList = findTask.filterOverPreicate(x -> x % 2 == 0);
		assertThat(resultList, hasItems(2, 4, 6, 8));
		List<Integer> nullList = findTask.filterOverPreicate(x -> x % 2 == 0);
		assertThat(nullList, empty());
	}
}
