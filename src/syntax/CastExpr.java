package syntax;

import compiler.*;
import checker.*;
import codegen.*;
import interp.*;

/** Provides a representation for expressions that can appear
 *  in a statement.
 */
public class CastExpr extends Expression {
    Expression needsCast;
    Type castType;
    Type exprType;
    public CastExpr(Position pos, Type castType, Expression needsCast) {
        super(pos);
        this.castType = castType;
        this.needsCast = needsCast;
    }

    public Type typeOf(Context ctxt, VarEnv env)
    throws Diagnostic {
        castType = castType.check(ctxt);
        exprType = needsCast.typeOf(ctxt, env);
        return castType;
    }

    public org.llvm.Value llvmGen(LLVM l) {
        if (castType.equal(Type.INT)) {
            return l.getBuilder().buildTrunc(needsCast.llvmGen(l),
                                             castType.llvmTypeField(), "cast");
        } else if (castType.equal(Type.LONG)) {
            return l.getBuilder().buildSExt(needsCast.llvmGen(l),
                                            castType.llvmTypeField(), "cast");
        } else {
            return l.getBuilder().buildBitCast(needsCast.llvmGen(l),
                                               castType.llvmTypeField(), "cast");
        }
    }

    public void compileExpr(Assembly a, int free) {
        /* no need for casts in x86 */
        needsCast.compileExpr(a, free);
    }
    public Value eval(State st) {
        return needsCast.eval(st);
    }
}
