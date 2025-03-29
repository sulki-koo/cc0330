package cookcloud.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cookcloud.entity.Report;
import cookcloud.repository.ReportRepository;
import cookcloud.service.ReportService;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	@Transactional
	public void insertReport(Report report) {
		report.setReportSendAt(LocalDateTime.now());
		report.setReportIsProc("N");
		report.setReportIsSend("N");
		reportRepository.save(report);
	}

}
