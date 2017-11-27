package ziiim.babybash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ziiim.babybash.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer>
{

}
