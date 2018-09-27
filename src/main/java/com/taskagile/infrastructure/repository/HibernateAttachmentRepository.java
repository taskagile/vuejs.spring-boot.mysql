package com.taskagile.infrastructure.repository;

import com.taskagile.domain.model.attachment.Attachment;
import com.taskagile.domain.model.attachment.AttachmentRepository;
import com.taskagile.domain.model.card.CardId;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class HibernateAttachmentRepository extends HibernateSupport<Attachment> implements AttachmentRepository {

  HibernateAttachmentRepository(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public List<Attachment> findAttachments(CardId cardId) {
    String sql = "SELECT a.* FROM attachment a WHERE a.card_id = :cardId order by id desc";
    NativeQuery<Attachment> query = getSession().createNativeQuery(sql, Attachment.class);
    query.setParameter("cardId", cardId.value());
    return query.list();
  }
}
