package com.mavenmokito.test.appmokito;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Arrays;

import org.junit.Test;

import com.mavenmokito.test.appmokito.service.TaskService;
import com.mavenmokito.test.appmokito.serviceimpl.AppServiceFindTask;
import com.mavenmokito.test.appmokito.stub.TaskServiceStubImpl;

public class AppServiceFindTaskTest {

	// AssertTrue of the dummy values match the expected result with predicate
	// filter
	@Test
	public void test() {
		TaskService serviceStub = new TaskServiceStubImpl();
		AppServiceFindTask serviceImpl = new AppServiceFindTask(serviceStub);
		List<Integer> values = serviceImpl.filterOverPreicate(x -> x < 3);
		assertTrue(Arrays.asList(1, 2).equals(values));
	}
  
  // AssertTrue of the dummy list size matches the expected result with predicate
	// filter
	@Test
	public void testPredicateListSize() {
		TaskService serviceStub = new TaskServiceStubImpl();
		AppServiceFindTask serviceImpl = new AppServiceFindTask(serviceStub);
		List<Integer> values = serviceImpl.filterOverPreicate(x -> x % 2 == 0);
		assertTrue(values.size() == 4);
	}

}
