package cookcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cookcloud.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long>{

}
