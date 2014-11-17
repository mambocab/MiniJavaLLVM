package notifications.thrownerrors;

import notifications.IsDeclaration;
import notifications.diagnostics.ExplicitSignatureContractDiagnostic;
import checker.VarEnv;

public class TooManyParametersError extends CompilerDiagnosticBuilder {

	private ExplicitSignatureContractDiagnostic signatureError;

	public TooManyParametersError(VarEnv env, IsDeclaration declaration) {
		this.signatureError = new ExplicitSignatureContractDiagnostic(env, declaration);
	}

}