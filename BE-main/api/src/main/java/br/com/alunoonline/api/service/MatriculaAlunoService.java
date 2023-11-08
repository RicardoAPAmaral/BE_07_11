package br.com.alunoonline.api.service;

import br.com.alunoonline.api.dtos.PatchNotasRequest;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatriculaAlunoService {
    @Autowired
    MatriculaAlunoRepository repository;

    public void create(MatriculaAluno matriculaAluno) {
        matriculaAluno.setStatus("MATRICULADO");
        repository.save(matriculaAluno);


        //matriculaAluno.setNota2(null);
        //ou tratamento de exceção para menor custo processual

    }

    public void patchNotas(long id, PatchNotasRequest patchNotasRequest) {
        Optional<MatriculaAluno> matriculaAluno = repository.findById(id);
        MatriculaAluno matriculaUpdated = matriculaAluno.get();

        //regra de negócio por sua conta para uma ou duas notas.
        //fazer validação para ver se a matricula existe.

        matriculaUpdated.setNota1(patchNotasRequest.getNota1());
        //validateNotas()
        matriculaUpdated.setNota2(patchNotasRequest.getNota2());

        //regra de cálculo fora daqui.
        Double media = (matriculaUpdated.getNota1() + matriculaUpdated.getNota2()) / 2;
        // valor da media a ser aprovado esteja em uma constante.
        Double mediaParaAprovavcao = 7.0;
        // verificar o valor do Status em um método por fora
        if (media >= mediaParaAprovavcao) {
            matriculaUpdated.setStatus("APROVADO");
        }else{
                matriculaUpdated.setStatus("REPROVADO");
            }
        repository.save(matriculaUpdated);
        }
    }
   /* private boolean validateNotas(){
        if ()
        return true
        else
        return false

    }*/

