package lab11_05_11.parser.ast;

import lab11_05_11.visitors.Visitor;

public class And extends BinaryOp {
	public And(Exp left, Exp right) {
		super(left, right);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitAnd(left, right);
	}
}
