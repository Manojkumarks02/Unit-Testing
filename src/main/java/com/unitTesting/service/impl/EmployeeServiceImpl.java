package com.unitTesting.service.impl;

import com.unitTesting.dto.EmployeeDto;
import com.unitTesting.entity.Employee;
import com.unitTesting.exceptions.ResourceNotFoundException;
import com.unitTesting.repository.EmployeeRepository;
import com.unitTesting.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee= employeeRepository.findById(id).orElseThrow(
                () -> {return new ResourceNotFoundException("employee id not found with this id :"+id);
                });
        return modelMapper.map(employee,EmployeeDto.class);
    }

    @Override
    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {
        List<Employee> existingEmployee = employeeRepository.findByEmail(employeeDto.getEmail());
        if(!existingEmployee.isEmpty()){
            throw new RuntimeException("employee already exists with this email"+ employeeDto.getEmail());
        }
        Employee newEmployee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(newEmployee);
        return modelMapper.map(savedEmployee,EmployeeDto.class);
    }


    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("employee id not found with this id :" + id);
                });

        if(!employee.getEmail().equals(employeeDto.getEmail())){
            throw new RuntimeException("employee email cannot be updated...!");
        }

//        employeeDto.setId(null);
        modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        boolean exists = employeeRepository.existsById(id);
        if(!exists){
            throw new RuntimeException("Employee not found with this id"+ id);
        }
        employeeRepository.deleteById(id);

    }
}
