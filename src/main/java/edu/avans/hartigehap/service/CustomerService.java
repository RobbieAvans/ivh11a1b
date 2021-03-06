package edu.avans.hartigehap.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Restaurant;

public interface CustomerService {
    List<Customer> findAll();

    Customer findById(Long id);

    Customer findByFirstNameAndLastName(String firstName, String lastName);
    
    Customer findByEmail(String email);
    
    Customer findBySessionID(String sessionID);

    List<Customer> findCustomersForRestaurant(Restaurant restaurant);

    Page<Customer> findAllByPage(Pageable pageable);

    Page<Customer> findCustomersForRestaurantByPage(Restaurant restaurant, Pageable pageable);

    Customer save(Customer customer);

    void delete(Long id);
}
