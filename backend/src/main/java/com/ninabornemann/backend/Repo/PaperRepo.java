package com.ninabornemann.backend.Repo;
import com.ninabornemann.backend.model.Paper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperRepo extends MongoRepository<Paper, String> {

}
