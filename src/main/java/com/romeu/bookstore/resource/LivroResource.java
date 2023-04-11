package com.romeu.bookstore.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.romeu.bookstore.domain.Livro;
import com.romeu.bookstore.dto.LivroDTO;
import com.romeu.bookstore.service.LivroService;

@RestController
@RequestMapping(value = "/livros")
public class LivroResource {

	@Autowired
	private LivroService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Livro> findById(@PathVariable Integer id) {
		Livro obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping
	public ResponseEntity<List<LivroDTO>> findAll(
			@RequestParam(value = "categoria", defaultValue = "0") Integer id_cat) {

		List<Livro> list;
	
		if (id_cat == 0) {
			list = service.findAll(); // Busca a lista de Livros
		} else {
			list = service.findAll(id_cat); // Busca a lista de Livros
		}

		List<LivroDTO> listDto = list.stream().map(obj -> new LivroDTO(obj)).collect(Collectors.toList()); // Converte
																											// Livros
																											// para
																											// LivrosDTO
		return ResponseEntity.ok().body(listDto);
	}

	/*
	 * @PostMapping public ResponseEntity<Categoria> create(@RequestBody Categoria
	 * obj) { obj = service.create(obj); URI uri =
	 * ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand
	 * (obj.getId()).toUri(); return ResponseEntity.created(uri).build(); }
	 * 
	 * @PutMapping(value = "/{id}") public ResponseEntity<CategoriaDTO>
	 * update(@PathVariable Integer id, @RequestBody CategoriaDTO objDTO) {
	 * Categoria newObj = service.update(id, objDTO); return
	 * ResponseEntity.ok().body(new CategoriaDTO(newObj)); }
	 * 
	 * @DeleteMapping(value = "/{id}") public ResponseEntity<Void>
	 * delete(@PathVariable Integer id) { service.delete(id); return
	 * ResponseEntity.noContent().build(); }
	 */

}
