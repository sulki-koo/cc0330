package cookcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cookcloud.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>{

}
