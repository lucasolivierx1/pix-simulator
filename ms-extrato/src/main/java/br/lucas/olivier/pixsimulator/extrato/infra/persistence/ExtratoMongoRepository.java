package br.lucas.olivier.pixsimulator.extrato.infra.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtratoMongoRepository extends MongoRepository<ExtratoDocument, Long> {
    List<ExtratoDocument> findAllByIdConta(String idConta);
}
