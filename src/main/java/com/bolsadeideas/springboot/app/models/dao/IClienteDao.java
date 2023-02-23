package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>{

	List<Cliente> findAll();

	void save(Cliente cliente);

	Optional <Object> findById(Long id);

	void deleteById(Long id);




}
