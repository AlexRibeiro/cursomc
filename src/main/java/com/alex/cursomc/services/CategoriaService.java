package com.alex.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alex.cursomc.domain.Categoria;
import com.alex.cursomc.repositories.CategoriaRepository;
import com.alex.cursomc.services.exceptions.DataIntegrityException;
import com.alex.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository catRepo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = catRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
	}
	
	public List<Categoria> findAll(){
		return catRepo.findAll();
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return catRepo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return catRepo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			catRepo.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma catégoria que possui produtos");
		}	
		
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		return catRepo.findAll(pageRequest);
	}
	
}
