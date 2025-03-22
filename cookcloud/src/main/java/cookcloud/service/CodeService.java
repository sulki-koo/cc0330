package cookcloud.service;

import java.util.Map;

import cookcloud.entity.Code;
import cookcloud.entity.CodeId;

public interface CodeService {

	public abstract Code getCodeInfo(CodeId codeId);
	
	public abstract Map<CodeId, Code> getAllCode();
	
}
