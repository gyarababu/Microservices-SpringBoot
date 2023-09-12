package com.microservices.department.service.impl;

import com.microservices.department.dto.DepartmentDto;
import com.microservices.department.entity.Department;
import com.microservices.department.repository.DepartmentRepository;
import com.microservices.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        // dto to entity
        Department department = new Department(
                departmentDto.getId(),
                departmentDto.getDepartmentName(),
                departmentDto.getDepartmentDescription(),
                departmentDto.getDepartmentCode()
        );

        // save
        Department savedDepartment = departmentRepository.save(department);

        // entity to dto
        DepartmentDto savedDto = new DepartmentDto(
                savedDepartment.getId(),
                savedDepartment.getDepartmentName(),
                savedDepartment.getDepartmentDescription(),
                savedDepartment.getDepartmentCode()
        );

        // return dto
        return savedDto;
    }

    @Override
    public DepartmentDto getDepartmentByCode(String departmentCode) {

        // find department by code
        Department department = departmentRepository.findByDepartmentCode(departmentCode);

        // convert entity to dto
        DepartmentDto newDepartmentDto = new DepartmentDto(
                department.getId(),
                department.getDepartmentName(),
                department.getDepartmentDescription(),
                department.getDepartmentCode()
        );

        // return dto
        return newDepartmentDto;
    }
}
