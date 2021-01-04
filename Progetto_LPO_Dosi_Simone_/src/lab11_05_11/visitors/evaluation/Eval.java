package lab11_05_11.visitors.evaluation;

import java.io.PrintWriter;

import lab11_05_11.environments.EnvironmentException;
import lab11_05_11.environments.GenEnvironment;
import lab11_05_11.parser.ast.Block;
import lab11_05_11.parser.ast.Exp;
import lab11_05_11.parser.ast.VarIdent;
import lab11_05_11.parser.ast.Stmt;
import lab11_05_11.parser.ast.StmtSeq;
import lab11_05_11.visitors.Visitor;

import static java.util.Objects.requireNonNull;

public class Eval implements Visitor<Value> {

	private final GenEnvironment<Value> env = new GenEnvironment<>();
	private final PrintWriter printWriter; // output stream used to print values

	public Eval() {
		printWriter = new PrintWriter(System.out, true);
	}

	public Eval(PrintWriter printWriter) {
		this.printWriter = requireNonNull(printWriter);
	}

	// dynamic semantics for programs; no value returned by the visitor

	@Override
	public Value visitProg(StmtSeq stmtSeq) {
		try {
			stmtSeq.accept(this);
			// possible runtime errors
			// EnvironmentException: undefined variable
		} catch (EnvironmentException e) {
			throw new EvaluatorException(e);
		}
		return null;
	}

	// dynamic semantics for statements; no value returned by the visitor

	@Override
	public Value visitAssignStmt(VarIdent ident, Exp exp) {
		env.update(ident, exp.accept(this));
		return null;
	    // completare
	}

	@Override
	public Value visitPrintStmt(Exp exp) {
		printWriter.println(exp.accept(this));
		return null;
	    // completare
	}

	@Override
	public Value visitVarStmt(VarIdent ident, Exp exp) {
		env.dec(ident, exp.accept(this));
		return null;
	    // completare
	}

	@Override
	public Value visitIfStmt(Exp exp, Block thenBlock, Block elseBlock) {
	    // completare
		if (exp.accept(this).toBool())
			thenBlock.accept(this);
		else if (elseBlock != null)
			elseBlock.accept(this);
		return null;
	}

	@Override
	public Value visitBlock(StmtSeq stmtSeq) {
		env.enterScope();
		stmtSeq.accept(this);
		env.exitScope();
		return null;
	    // completare
	}

	// dynamic semantics for sequences of statements
	// no value returned by the visitor

	@Override
	public Value visitSingleStmt(Stmt stmt) {
	    // completare
		stmt.accept(this);
		return null;
	}

	@Override
	public Value visitMoreStmt(Stmt first, StmtSeq rest) {
	    // completare
		first.accept(this);
		rest.accept(this);
		return null;
	}

	// dynamic semantics of expressions; a value is returned by the visitor

	@Override
	public Value visitAdd(Exp left, Exp right) {
	    // completare
		return new IntValue(left.accept(this).toInt()+right.accept(this).toInt());
	}

	@Override
	public Value visitIntLiteral(int value) {
	    // completare
		return new IntValue(value);
	}

	@Override
	public Value visitMul(Exp left, Exp right) {
	    // completare
		return new IntValue(left.accept(this).toInt()*right.accept(this).toInt());
	}

	@Override
	public Value visitSign(Exp exp) {
	    // completare
		return new IntValue(-exp.accept(this).toInt());
	}

	@Override
	public Value visitVarIdent(VarIdent id) {
	    // completare
		return env.lookup(id);
	}

	@Override
	public Value visitNot(Exp exp) {
	    // completare
		return new BoolValue(!exp.accept(this).toBool());
	}

	@Override
	public Value visitAnd(Exp left, Exp right) {
	    // completare
		return new BoolValue(left.accept(this).toBool() && right.accept(this).toBool());
	}

	@Override
	public Value visitBoolLiteral(boolean value) {
	    // completare
		return new BoolValue(value);
	}

	@Override
	public Value visitEq(Exp left, Exp right) {
	    // completare
		return new BoolValue(left.accept(this).equals(right.accept(this)));
	}

	@Override
	public Value visitPairLit(Exp left, Exp right) {
	    // completare
		return new PairValue(left.accept(this),right.accept(this));
	}

	@Override
	public Value visitFst(Exp exp) {
	    // completare
		return exp.accept(this).toProd().getFstVal();
	}

	@Override
	public Value visitSnd(Exp exp) {
	    // completare
		return exp.accept(this).toProd().getSndVal();
	}

}
