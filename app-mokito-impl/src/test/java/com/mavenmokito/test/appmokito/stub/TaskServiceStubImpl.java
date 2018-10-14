package com.mavenmokito.test.appmokito.stub;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.mavenmokito.test.appmokito.service.TaskService;

/*
 * Stubs to be created for each scenario
 * - Implementation is hard coded
 * - Code is static [predetermined behavior]
 * - Method call verification functionality is unavailable
 * - Each functional verification require implementation
 * 
 * To return list with values, empty or null
 * Similarly positive, negative and edge case implementation for each service
 */
 
 public class TaskServiceStubImpl implements TaskService {
  
  // Implemented application stub for test purpose
  @Override
	public List<Integer> findAllTask() {
		return IntStream.range(1, 10)
                    .boxed()
                    .collect(Collectors.toList());
	}
    
 }
