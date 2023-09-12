package com.microservices.employee.service.impl;

import com.microservices.employee.dto.APIResponseDto;
import com.microservices.employee.dto.DepartmentDto;
import com.microservices.employee.dto.EmployeeDto;
import com.microservices.employee.entity.Employee;
import com.microservices.employee.exception.DepartmentNotFoundException;
import com.microservices.employee.exception.ResourceNotFoundException;
import com.microservices.employee.repository.EmployeeRepository;
import com.microservices.employee.service.EmployeeService;
import com.microservices.employee.service.FeignAPI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FeignAPI feignAPI;
    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee employee = mapToEntity(employeeDto);

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeDto savedDto = mapToDto(employee);

        return savedDto;
    }

    @Override
    public APIResponseDto getEmployeeById(long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new ResourceNotFoundException("Employee","id",employeeId));

        String departmentCode = employee.getDepartmentCode();
        if (departmentCode == null || departmentCode.isEmpty()) {
            throw new DepartmentNotFoundException("Department","departmentCode",departmentCode);
        }

        // synchronous communication using feignClient and getting department details
        DepartmentDto departmentDto = feignAPI.getDepartment(employee.getDepartmentCode());

        EmployeeDto employeeDto = mapToDto(employee);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);

        // returning both details
        return apiResponseDto;
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
