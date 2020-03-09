package com.NowakArtur97.WorldOfManga.repository.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.NowakArtur97.WorldOfManga.model.neo4j.User;

public interface UserNeo4jRepository extends Neo4jRepository<User, Long>{

}
