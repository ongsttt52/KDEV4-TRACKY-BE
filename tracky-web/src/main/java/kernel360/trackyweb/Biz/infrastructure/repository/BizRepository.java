package kernel360.trackyweb.Biz.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kernel360.trackyweb.Biz.infrastructure.entity.BizEntity;

@Repository
public interface BizRepository extends JpaRepository<BizEntity, Long> {

}
