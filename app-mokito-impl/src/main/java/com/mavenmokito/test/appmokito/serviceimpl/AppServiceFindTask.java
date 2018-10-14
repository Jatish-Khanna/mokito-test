package com.mavenmokito.test.appmokito.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.mavenmmokito.test.appmokito.service.TaskService;

public class AppServiceFindTask {

  // Refer interface
  TaskService findAllOverNetwork;
  
  public AppServiceFindTask(){
     super();
  }

  // Constructor injection for implementation of Interface
	public AppServiceFindTask(TaskService findAllOverNetwork) {
		super();
		this.findAllOverNetwork = findAllOverNetwork;
	}

 // Apply predicate over retured list retured from TaskService
	public List<Integer> filterOverPreicate(Predicate<Integer> filter) {
		List<Integer> filteredList = new ArrayList<>();
		List<Integer> completeList = findAllOverNetwork.findAllTask();
		if(completeList == null)
			return filteredList;
		// @formatter:off
		filteredList = completeList.stream()
							.filter(filter)
							.collect(Collectors.<Integer>toList());
		// @formatter:on
		return filteredList;
	}


}
