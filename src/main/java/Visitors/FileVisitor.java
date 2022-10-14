package Visitors;

import org.eclipse.jdt.core.dom.*;

public class FileVisitor extends ASTVisitor {
    private int nbreLignes;

    public FileVisitor() {
        this.nbreLignes = 0;
    }

    public int getNbreLignes() {
        return nbreLignes;
    }

    public void setNbreLignes(int nbreLignes) {
        this.nbreLignes = nbreLignes;
    }

    @Override
    public boolean visit(ImportDeclaration node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(TypeDeclaration node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(PackageDeclaration node) {
        nbreLignes++;
        return true;

    }

    @Override
    public boolean visit(AssertStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(Block node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(BreakStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(ConstructorInvocation node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(ContinueStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(DoStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(EmptyStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(EnhancedForStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(ExpressionStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(ForStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(IfStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(LabeledStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(ReturnStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(SuperConstructorInvocation node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(SwitchCase node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(SwitchStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(SynchronizedStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(ThrowStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(TryStatement node) {
        nbreLignes++;
        return true;
    }


    @Override
    public boolean visit(TypeDeclarationStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(WhileStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(VariableDeclarationStatement node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(MethodDeclaration node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(MethodInvocation node) {
        nbreLignes++;
        return true;
    }

    @Override
    public boolean visit(FieldDeclaration node) {
        nbreLignes++;
        return true;
    }


}
