package notifications.diagnostics;

import java.util.LinkedHashMap;

import notifications.contracts.ImplicitTypeContract;
import syntax.Expression;
import syntax.Syntax;
import syntax.Type;
import checker.Env;
import compiler.Position;

public class ImplicitTypeContractDiagnostic extends TypeMismatchDiagnostic {

	private LinkedHashMap<Expression, Type> expressions;
	private Syntax contractProvider;
	private ImplicitTypeContract languageContract;
	private String string;

	public ImplicitTypeContractDiagnostic(Expression incorrectExpression, Type actualType,
			Syntax contractProvider) {
		this.string = incorrectExpression.toString() + " is of type " + actualType +
				" but should be of type " + contractProvider.getContract().getRequiredType() + 
				" for " + contractProvider.toString(); 
		this.expressions = new LinkedHashMap();
		this.expressions.put(incorrectExpression, actualType);
		this.contractProvider = contractProvider;
//		this.languageContract = languageContract;
	}

	public ImplicitTypeContractDiagnostic(Expression leftArgument, Type leftArgumentType,
			Expression rightArgument, Type rightArgumentType,
			Syntax contractProvider) {
		this.expressions = new LinkedHashMap();
		this.expressions.put(leftArgument, leftArgumentType);
		this.expressions.put(leftArgument, leftArgumentType);
		this.contractProvider = contractProvider;
//		this.languageContract = languageContract;
	}

	public ImplicitTypeContractDiagnostic(Env incorrectEnv, Type methEnvActualType,
			Syntax contractProvider2) {
		// hmm. environments (i.e. method declarations) aren't expressions or even syntax.
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return this.string;
	}

	@Override
	public Position getPos() {
		// TODO Auto-generated method stub
		return null;
	}

}
