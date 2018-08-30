package com.taskagile.infrastructure.repository;

import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.team.TeamRepository;
import com.taskagile.domain.model.user.UserId;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class HibernateTeamRepository extends HibernateSupport<Team> implements TeamRepository {

  HibernateTeamRepository(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public List<Team> findTeamsByUserId(UserId userId) {
    String sql =
      " SELECT t.* FROM team t WHERE t.user_id = :userId " +
      " UNION " +
      " ( " +
      "   SELECT t.* FROM team t, board b, board_member bm " +
      "   WHERE t.id = b.team_id AND bm.board_id = b.id AND bm.user_id = :userId " +
      " ) ";
    NativeQuery<Team> query = getSession().createNativeQuery(sql, Team.class);
    query.setParameter("userId", userId.value());
    return query.list();
  }
}
