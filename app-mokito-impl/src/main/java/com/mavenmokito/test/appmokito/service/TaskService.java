package com.mavenmokito.test.appmokito.TaskService;

import java.util.List;

// Application interface fetching service over network (already tested)
public interface TaskService {

  // @return - expected a list or empty or null
  public List<Integer> findAllTask();
}
