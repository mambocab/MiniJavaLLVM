// Copyright (c) Mark P Jones, Portland State University
// Subject to conditions of distribution and use; see LICENSE for details
// February 3 2008 11:12 AM

package syntax;

import compiler.*;
import checker.*;
import codegen.*;

import org.llvm.TypeRef;

/** Provides a representation for types.
 */
public abstract class Type {
    public static final Type INT     = new PrimitiveType("int");
    public static final Type LONG    = new PrimitiveType("long");
    public static final Type FLOAT   = new PrimitiveType("float");
    public static final Type DOUBLE  = new PrimitiveType("double");
    public static final Type BOOLEAN = new PrimitiveType("boolean");
    public static final Type NULL    = new PrimitiveType("null");
    public static final Type VOID    = new PrimitiveType("void");

    public abstract org.llvm.Value defaultValue();

    /** Test for equality with another type.
     */
    public abstract boolean equal(Type type);

    public abstract TypeRef llvmType();

    public abstract void llvmGenTypes(LLVM l);

    /** Test to see if this class is a supertype of another type.
     */
    public boolean isSuperOf(Type type) {
        return this.equal(type);
    }

    /** Test to see if this type is a class; by default, we return null.
     */
    public ClassType isClass() {
        return null;
    }

    /** Check to ensure that this is a valid type.  This is part of the
     *  mechanism used to deal with types that are specified by name,
     *  which cannot be properly resolved until parsing is complete.
     */
    public Type check(Context ctxt) {
        return this;
    }

    /** Returns the number of bytes needed to store an object of
     *  this type.
     */
    public int size() {
        return Assembly.WORDSIZE;
    }

    public static boolean mixedNull(Type l_t, Type r_t) {
        if (l_t.equal(Type.NULL) && !r_t.equal(Type.NULL)) {
            return true;
        } else if (!l_t.equal(Type.NULL) && r_t.equal(Type.NULL)) {
            return true;
        }
        return false;
    }
}