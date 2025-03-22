package cookcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cookcloud.entity.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>{

}
