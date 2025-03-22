package cookcloud.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cookcloud.entity.Code;
import cookcloud.entity.CodeId;
import cookcloud.repository.CodeRepository;
import cookcloud.service.CodeService;
import jakarta.annotation.PostConstruct;

@Service
public class CodeServiceImpl implements CodeService {

	@Autowired
	private CodeRepository codeRepository;
	
	private static Map<CodeId, Code> codeMap = new HashMap<>();

	@PostConstruct // 서버 실행시 자동 실행
	public void loadCodes() {
		List<Code> codes = codeRepository.findAll();
		
		codeMap = codes.stream().collect(Collectors.toMap(
				code ->  new CodeId(code.getParentCode(), code.getChildCode()), 
				code -> code
				));
		 System.out.println("Data loaded into map. Total records: " + codeMap);  // 로딩된 map의 크기 확인
	}
	
	@Override
	public Code getCodeInfo(CodeId codeId) {
		return codeMap.get(codeId);
	}
	
	@Override
	public Map<CodeId, Code> getAllCode() {
		return codeMap;
	}
}
