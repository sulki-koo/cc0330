package cookcloud.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cookcloud.entity.Code;
import cookcloud.entity.CodeId;
import cookcloud.entity.Report;
import cookcloud.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportService reportService;

	// 신고 처리
	@PostMapping("/{type}/{id}")
	@ResponseBody
	public String reportContent(@PathVariable String type, @PathVariable Long id, @ModelAttribute Report report) {
		if ("recipe".equalsIgnoreCase(type)) {
			report.setRecipeId(id);
		} else if ("review".equalsIgnoreCase(type)) {
			report.setReviewId(id);
		} else {
			return "잘못된 신고 유형입니다.";
		}

		report.setMemId(report.getMemId()); // 신고한 사용자 정보 유지
		report.setReportReason(report.getReportReason());
		report.setReportCode(report.getReportCode());

		reportService.insertReport(report);
		return "신고가 완료되었습니다.";
	}
}
