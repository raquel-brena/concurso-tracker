package com.rb.web2.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.processoSeletivo.dto.RequestProcessoDTO;
import com.rb.web2.domain.processoSeletivo.dto.UpdateProcessoDTO;
import com.rb.web2.domain.processoSeletivo.mapper.ProcessoSeletivoMapper;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.vaga.Vaga;
import com.rb.web2.infra.properties.FileStorageProperties;
import com.rb.web2.repositories.AgendaRepository;
import com.rb.web2.repositories.CriterioAvaliacaoRepository;
import com.rb.web2.repositories.ProcessoSeletivoRepository;
import com.rb.web2.repositories.UserRepository;
import com.rb.web2.repositories.VagaRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

@Service
public class ProcessoSeletivoService {


  private final Path fileStorageLocation;

  @Autowired
  private DocumentoService documentoService;

  @Autowired
  private ProcessoSeletivoRepository repository;

  @Autowired
  private VagaRepository vagaRepository;

  @Autowired
  private AgendaRepository agendaRepository;

  @Autowired
  private CriterioAvaliacaoRepository criterioAvaliacaoRepository;

  @Autowired
  private UserRepository userRepository;

    public ProcessoSeletivoService(FileStorageProperties fileStorageProperties) {
    this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
        .toAbsolutePath().normalize();
  }

  public ProcessoSeletivo create(RequestProcessoDTO dto) {
    var existeProcesso = this.getProcessoSeletivoByTitulo(dto.titulo());

    if (existeProcesso.isPresent()) {
      throw new NotFoundException("Processo seletivo com o nome " + dto.titulo() + " já existe");
    }

    ProcessoSeletivo processo = ProcessoSeletivoMapper.toEntity(dto);
    return repository.save(processo);
  }

  public ProcessoSeletivo getProcessoSeletivoById(String id) {
    return repository.findById(id)
    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
  }

  public Optional<ProcessoSeletivo> getProcessoSeletivoByTitulo(String titulo) {
    return repository.findByTitulo(titulo);
  }

  public List<ProcessoSeletivo> getAllProcessoSeletivos() {
    return repository.findAll();
  }

  public ProcessoSeletivo atualizar(String id, UpdateProcessoDTO dto) {
    ProcessoSeletivo processo = this.getProcessoSeletivoById(id);

    if (dto.titulo() != null) {
      processo.setTitulo(dto.titulo());
      }

    if (dto.temporario() != processo.isTemporario()) {
      processo.setTemporario(dto.temporario());
    }

    if (dto.descricao() != processo.getDescricao()) {
      processo.setDescricao(dto.descricao());
    }

    if (dto.validade() != processo.getValidadeMeses()) {
      processo.setValidadeMeses(dto.validade());
    }

    if (dto.linkEdital() != processo.getLinkEdital()) {
      processo.setLinkEdital(dto.linkEdital());
    }

    if (dto.vagasIds() != null) {
    List<Vaga> vagas = vagaRepository.findAllById(dto.vagasIds());
    processo.setVagas(vagas);
    }

    if (dto.agendaId() != null) {
    Agenda agenda = agendaRepository.findById(dto.agendaId())
    .orElseThrow(() -> new NotFoundException("Agenda não encontrada"));
    processo.setAgenda(agenda);
    }

    if (dto.documentoNecessarios() != null) {
    processo.setDocumentosNecessarios(dto.documentoNecessarios());
    }

    if (dto.criteriosIds() != null) {
    List<CriterioAvaliacao> criterios =
    criterioAvaliacaoRepository.findAllById(dto.criteriosIds());
    if (criterios.isEmpty()) {
    throw new NotFoundException("Criterio de avaliação não encontrado");
    }
    processo.setCriterios(criterios);
    }

    if (dto.comissaoOrganizadoraIds() != null) {
    List<User> comissao = userRepository.findAllById(dto.comissaoOrganizadoraIds());
    processo.setComissaoOrganizadora(comissao);
    }

    if (dto.comissaoOrganizadoraIds() != null) {
    List<User> participantes =
    userRepository.findAllById(dto.comissaoOrganizadoraIds());
    processo.setParticipantes(participantes);
    }

    return repository.save(processo);
  }


  public String uploadEdital (MultipartFile file, String id) throws IOException {
   String fileDownloadUri = documentoService.uploadFile(file, id);
    ProcessoSeletivo processo = this.getProcessoSeletivoById(id);
    processo.setLinkEdital(fileDownloadUri);
    repository.save(processo);
    return fileDownloadUri;
  }

    public Resource downloadEdital(String filename, String userId) throws IOException {
     var resource =  this.documentoService.downloadFile(filename, userId);
   return resource;

  }
}
