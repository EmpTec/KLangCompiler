package de.hsrm.compiler.Klang.nodes;

import de.hsrm.compiler.Klang.types.*;
import de.hsrm.compiler.Klang.visitors.*;

public abstract class Node {
    public Type type;
    public abstract <R> R welcome(Visitor<R> v);
}