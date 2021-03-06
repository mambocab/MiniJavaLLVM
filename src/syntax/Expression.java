/*
 * MiniJava Compiler - X86, LLVM Compiler/Interpreter for MiniJava.
 * Copyright (C) 2014, 2008 Mitch Souders, Mark A. Smith, Mark P. Jones
 *
 * MiniJava Compiler is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * MiniJava Compiler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MiniJava Compiler; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */


package syntax;

import interp.State;
import interp.Value;
import checker.Context;
import checker.VarEnv;
import codegen.Assembly;
import codegen.LLVM;

import compiler.Diagnostic;
import compiler.Position;

/** Provides a representation for expressions.
 */
public abstract class Expression extends Syntax {
    public Expression(Position pos) {
        super(pos);
    }

    public abstract org.llvm.Value llvmGen(LLVM l);

    /** Check this expression and return an object that describes its
     *  type (or throw an exception if an unrecoverable error occurs).
     */
    public abstract Type typeOf(Context ctxt, VarEnv env)
    throws Diagnostic;


    /** This value is used as the depth for an expression that can have
     *  side effects, and for which a change of evaluation order might
     *  give the wrong behavior.  To test for expressions with a possible
     *  side effect, you should determine whether getDepth() returns a
     *  value that is greater than or equal to DEEP (testing for equality
     *  with DEEP is *not* enough).
     */
    protected final static int DEEP = 1000;

    /** Return the depth of this expression tree as a measure of its
     *  complexity.  By default, we treat all expressions as having
     *  a potential side-effect, and thus return the constant DEEP in
     *  this base class.
     */
    int getDepth() {
        return DEEP;
    }

    /** Generate code to evaluate this expression and
     *  leave the result in the specified free variable.
     */
    public abstract void compileExpr(Assembly a, int free);
    void compileExpr(Assembly a) {
        compileExpr(a, 0);
    }

    void compileExprOp(Assembly a, String op, int free) {
        a.spill(free + 1);
        compileExpr(a, free + 1);
        a.emit(op, a.reg(free + 1), a.reg(free));
        a.unspill(free + 1);
    }
    void compileExprOp(Assembly a, String op) {
        compileExprOp(a, op, 0);
    }

    /** Generate code to evaluate this expression and
     *  branch to a specified label if the result is true.
     */
    void branchTrue(Assembly a, String lab, int free) {
        compileExpr(a, free);
        a.emit("orl", a.reg(free), a.reg(free));
        a.emit("jnz", lab);
    }
    void branchTrue(Assembly a, String lab) {
        branchTrue(a, lab, 0);
    }

    /** Generate code to evaluate this expression and
     *  branch to a specified label if the result is false.
     */
    void branchFalse(Assembly a, String lab, int free) {
        compileExpr(a, free);
        a.emit("orl", a.reg(free), a.reg(free));
        a.emit("jz", lab);
    }
    void branchFalse(Assembly a, String lab) {
        branchFalse(a, lab, 0);
    }

    /** Evaluate this expression.
     */
    public abstract Value eval(State st);

	public Syntax getDeclaration() {
		// dummy method for future error generation
		return null;
	}
}
