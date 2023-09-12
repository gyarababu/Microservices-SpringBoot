package com.microservices.employee.service.impl;

import com.microservices.employee.dto.EmployeeDto;
import com.microservices.employee.entity.Employee;
import com.microservices.employee.exception.ResourceNotFoundException;
import com.microservices.employee.repository.EmployeeRepository;
import com.microservices.employee.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee employee = mapToEntity(employeeDto);

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeDto savedDto = mapToDto(employee);

        return savedDto;
    }

    @Override
    public EmployeeDto getEmployeeById(long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new ResourceNotFoundException("Employee","id",employeeId));

        EmployeeDto employeeDto = mapToDto(employee);

        return employeeDto;
    }

    // modelMapper entity to dto
    private EmployeeDto mapToDto(Employee employee){
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        return employeeDto;
    }

    // modelMapper dto to entity
    private Employee mapToEntity(EmployeeDto employeeDto){
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        return employee;
    }
}
