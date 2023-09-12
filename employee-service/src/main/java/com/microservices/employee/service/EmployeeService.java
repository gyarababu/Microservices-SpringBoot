package com.microservices.employee.service;

import com.microservices.employee.dto.APIResponseDto;
import com.microservices.employee.dto.EmployeeDto;
import org.springframework.stereotype.Service;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    APIResponseDto getEmployeeById(long employeeId);
}
