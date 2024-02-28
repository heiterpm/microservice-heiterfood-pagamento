package br.com.heiterfood.pagamentos.controller;

import br.com.heiterfood.pagamentos.dto.PagamentoDto;
import br.com.heiterfood.pagamentos.service.PagamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    @Autowired
    private PagamentoService service;

    @GetMapping
    public Page<PagamentoDto> listar (@PageableDefault(size = 10)Pageable paginacao) {
        return service.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> detalhar (@PathVariable @NotNull Long id) {
        PagamentoDto dto = service.obterPorId(id);

        return  ResponseEntity.ok(dto);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PagamentoDto> criar (@RequestBody @Valid PagamentoDto dto, UriComponentsBuilder uriComponentsBuilder) {
        PagamentoDto pagamento = service.criarPagamento(dto);
        URI uri = uriComponentsBuilder.path("/pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();

        return ResponseEntity.created(uri).body(pagamento);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PagamentoDto> atualizar (@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDto dto) {
        PagamentoDto pagamento = service.atualizarPagamento(id, dto);

        return  ResponseEntity.ok(pagamento);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<PagamentoDto> remover (@PathVariable @NotNull Long id) {
        service.excluirPagamento(id);

        return  ResponseEntity.noContent().build();
    }
}
