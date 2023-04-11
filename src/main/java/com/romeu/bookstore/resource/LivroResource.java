package com.romeu.bookstore.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.romeu.bookstore.domain.Livro;
import com.romeu.bookstore.dto.LivroDTO;
import com.romeu.bookstore.service.LivroService;

import jakarta.websocket.server.PathParam;

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
	 * //Pode utilizar o PATCH e o PUT, mas utilizar PATCH geralemnte quando não é
	 * preciso atualizar todas as informações
	 */
	@PatchMapping(value = "/{id}")
	public ResponseEntity<Livro> update(@PathVariable Integer id, @RequestBody Livro obj) {
		Livro newObj = service.update(id, obj);
		return ResponseEntity.ok().body(newObj);
	}

	@PostMapping
	public ResponseEntity<Livro> create(@RequestParam(value = "categoria", defaultValue = "-1") Integer id_cat,
			@RequestBody Livro obj) {
		Livro newObj = service.create(id_cat, obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/livros/{id}")
				.buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	/*
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
