package notifications.thrownerrors;

import notifications.RichDiagnostic;
import syntax.ClassType;
import compiler.Position;

public class CyclicInheritanceError extends RichDiagnostic {

	private ClassType classType;

	public CyclicInheritanceError(ClassType classType) {
		this.classType = classType;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position getPos() {
		// TODO Auto-generated method stub
		return null;
	}

}
