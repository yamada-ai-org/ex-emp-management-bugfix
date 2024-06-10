package com.example.controller;

import com.example.domain.Employee;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee/api")
public class EmployeeAPIController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/search")
    public List<String> searchAPI(@RequestParam String query){
        List<Employee> employeeList = employeeService.searchByNameLike(query);
        return employeeList.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
    }
}
