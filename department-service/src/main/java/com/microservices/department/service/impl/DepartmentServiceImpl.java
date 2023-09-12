package com.microservices.department.service.impl;

import com.microservices.department.dto.DepartmentDto;
import com.microservices.department.entity.Department;
import com.microservices.department.exception.DepartmentNotFoundException;
import com.microservices.department.repository.DepartmentRepository;
import com.microservices.department.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        // dto to entity
        Department department = mapToEntity(departmentDto);

        // save
        Department savedDepartment = departmentRepository.save(department);

        // entity to dto
        DepartmentDto savedDto = mapToDto(savedDepartment);

        // return dto
        return savedDto;
    }

    @Override
    public DepartmentDto getDepartmentByCode(String departmentCode) {

        // find department by code
        Department department = departmentRepository.findByDepartmentCode(departmentCode);

        if (department == null) {
            throw new DepartmentNotFoundException("Department","departmentCode",departmentCode);
        }

        // convert entity to dto
        DepartmentDto newDepartmentDto = mapToDto(department);

        // return dto
        return newDepartmentDto;
    }

    // modelMapper entity to dto
    private DepartmentDto mapToDto(Department department){
        DepartmentDto departmentDto = modelMapper.map(department, DepartmentDto.class);
        return departmentDto;
    }

    // modelMapper dto to Entity
    private Department mapToEntity(DepartmentDto departmentDto){
        Department department = modelMapper.map(departmentDto, Department.class);
        return department;
    }
}
