package med.voll.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.DTO.MedicoDTO;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository repository;

    public void cadastrar(MedicoDTO dados) {
        repository.save(new Medico(dados));
    }


}
