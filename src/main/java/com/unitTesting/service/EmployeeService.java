package com.unitTesting.service;

import com.unitTesting.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto getEmployeeById(Long id);

    EmployeeDto createNewEmployee(EmployeeDto employeeDto);

    EmployeeDto updateEmployee(EmployeeDto employeeDto, Long id);

    void deleteEmployee(Long id);
}
