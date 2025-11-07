package com.example.cicd.service.impl;

import com.example.cicd.dto.CustomerDTO;
import com.example.cicd.entity.Customer;
import com.example.cicd.exception.ResourceNotFoundException;
import com.example.cicd.repo.CustomerRepository;
import com.example.cicd.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repo;

    public CustomerServiceImpl(CustomerRepository repo) {
        this.repo = repo;
    }

    private CustomerDTO toDto(Customer c) {
        CustomerDTO d = new CustomerDTO();
        d.setId(c.getId());
        d.setName(c.getName());
        d.setEmail(c.getEmail());
        d.setRegisterDate(c.getRegisterDate());
        return d;
    }

    private Customer toEntity(CustomerDTO d) {
        Customer c = new Customer();
        c.setName(d.getName());
        c.setEmail(d.getEmail());
        c.setRegisterDate(d.getRegisterDate() != null ? d.getRegisterDate() : LocalDate.now());
        return c;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO dto) {
        Customer entity = toEntity(dto);
        Customer saved = repo.save(entity);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(Long id) {
        Customer c = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
        return toDto(c);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO dto) {
        Customer existing = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        if (dto.getRegisterDate() != null) existing.setRegisterDate(dto.getRegisterDate());
        Customer saved = repo.save(existing);
        return toDto(saved);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer existing = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
        repo.delete(existing);
    }
}
