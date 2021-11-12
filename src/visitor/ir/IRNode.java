package visitor.ir;

import java.lang.String;

import visitor.IRVisitor;
import visitor.ast.Type;

public abstract class IRNode
{
    public abstract String toString();
    public abstract Object accept(IRVisitor visitor);
}
