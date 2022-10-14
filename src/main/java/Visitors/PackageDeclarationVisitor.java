package Visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PackageDeclaration;

import java.util.ArrayList;
import java.util.List;

public class PackageDeclarationVisitor extends ASTVisitor {
    private int NbrePackages;
    private List<PackageDeclaration> packages = new ArrayList<>();

    public PackageDeclarationVisitor() {
        NbrePackages = 0;
    }

    public int getNbrePackages() {
        return NbrePackages;
    }

    public void setNbrePackages(int nbrePackages) {
        NbrePackages = nbrePackages;
    }

    public List<PackageDeclaration> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageDeclaration> packages) {
        this.packages = packages;
    }

    public boolean visit(PackageDeclaration node) {
        NbrePackages++;
        packages.add(node);
        return super.visit(node);
    }


}
