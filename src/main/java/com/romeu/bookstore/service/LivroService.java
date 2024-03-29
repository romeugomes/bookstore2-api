package com.romeu.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romeu.bookstore.domain.Categoria;
import com.romeu.bookstore.domain.Livro;
import com.romeu.bookstore.repositories.LivroRepository;
import com.romeu.bookstore.service.exception.ObjectNotFoundException;

@Service
public class LivroService {
	@Autowired
	private LivroRepository repository;

	@Autowired
	private CategoriaService categoriaService;

	public Livro findById(Integer id) {
		Optional<Livro> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Livro.class.getName()));
	}

	public List<Livro> findAll() {
		return repository.findAll();
	}

	public List<Livro> findAll(Integer id_cat) {
		categoriaService.findById(id_cat);
		return repository.findAllByCategoria(id_cat);
	}

	public Livro update(Integer id, Livro obj) {
		Livro newObj = findById(id);
		atualizaLivro(obj, newObj);
		return repository.save(newObj);
	}

	private void atualizaLivro(Livro obj, Livro newObj) {
		newObj.setTitulo(obj.getTitulo());
		newObj.setNome_autor(obj.getNome_autor());
		newObj.setTexto(obj.getTexto());
	}

	public Livro create(Integer id_cat, Livro obj) {
		obj.setId(null);
		Categoria cat = categoriaService.findById(id_cat);
		obj.setCategoria(cat);
		return repository.save(obj);
	}

	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}

	/*
	 * public List<Categoria> findAll() { return repository.findAll(); }
	 * 
	 * public Categoria create(Categoria obj) { obj.setId(null); return
	 * repository.save(obj); }
	 * 
	 * public Categoria update(Integer id, CategoriaDTO objDTO) { Categoria obj =
	 * findById(id); obj.setNome(objDTO.getNome());
	 * obj.setDescricao(objDTO.getDescricao()); return repository.save(obj); }
	 * 
	 * public void delete(Integer id) { findById(id); try {
	 * repository.deleteById(id); } catch
	 * (org.springframework.dao.DataIntegrityViolationException e) { throw new
	 * DataIntegrityViolationException(
	 * "Categoria não pode ser deletada pois possui livros associados!!!"); } }
	 */

}
