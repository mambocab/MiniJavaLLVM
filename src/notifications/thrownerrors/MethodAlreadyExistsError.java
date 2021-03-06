package notifications.thrownerrors;

import syntax.Id;
import checker.MethEnv;

public class MethodAlreadyExistsError extends CompilerDiagnosticBuilder {

	private NameClashError nameClashError;

	public MethodAlreadyExistsError(Id id, MethEnv clashingDeclarations) {
		this.nameClashError = new NameClashError(id, clashingDeclarations);
	}

}
