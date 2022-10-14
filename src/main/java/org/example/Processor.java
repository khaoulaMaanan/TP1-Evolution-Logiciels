package org.example;

import Visitors.*;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.List;
import java.util.*;

public class Processor {
    int totalnbreLignesCodeMethode = 0;
    int totalnbreLignesCodeClasse = 0;
    private final String projectPath;
    private final Parser parser;
    private final TypeDeclarationVisitor typeDeclarationVisitor;
    private final MethodDeclarationVisitor methodDeclarationVisitor;
    private final PackageDeclarationVisitor packageDeclarationVisitor;
    private final MethodInvocationVisitor methodInvocationVisitor;
    private final FileVisitor fileVisitor;
    private final List<CompilationUnit> cu1;
    private final List<CompilationUnit> cu2;

    public Processor(String projectPath) {
        this.projectPath = projectPath;
        this.parser = new Parser(projectPath);
        this.typeDeclarationVisitor = new TypeDeclarationVisitor();
        this.methodDeclarationVisitor = new MethodDeclarationVisitor();
        this.packageDeclarationVisitor = new PackageDeclarationVisitor();

        this.fileVisitor = new FileVisitor();
        this.methodInvocationVisitor = new MethodInvocationVisitor();
        cu1 = new ArrayList<>();
        cu2 = new ArrayList<>();
    }

    public void AfficherStatistiques() throws IOException {
        System.out.println("Nombre de classes de l'application : " + typeDeclarationVisitor.getNbreClasses());
        System.out.println("Nombre de lignes de code de l'application : " + fileVisitor.getNbreLignes());
        System.out.println("Nombre de méthodes de l'application : " + typeDeclarationVisitor.getNbreMethodes());
        System.out.println("Nombre de packages de l'application : " + packageDeclarationVisitor.getNbrePackages());
        System.out.println("Nombre moyen de méthodes par classe : " + typeDeclarationVisitor.getNbreMethodes() / typeDeclarationVisitor.getNbreClasses());
        countNumberOfMethodLines();
        System.out.println("Nombre moyen de lignes de code par méthode : " + totalnbreLignesCodeMethode / typeDeclarationVisitor.getNbreMethodes());
        System.out.println("Nombre moyen d'attributs par classe : " + typeDeclarationVisitor.getNbreAttributs() / typeDeclarationVisitor.getNbreClasses());
        System.out.println("Les 10% des classes qui possèdent le plus grand nombre de méthodes : " + getTenPercentClassesWithGreaterNbrOfMethods());
        System.out.println("Les 10% des classes qui possèdent le plus grand nombre d'attributs : " + getTenPercentClassesWithGreaterNbrOfAttributes());
        System.out.println("Les classes qui font partie aux deux catégories précédentes : " + getClassesOfBothCategories());
        System.out.println("Les classes qui possèdent plus de X méthodes : \n******Veuillez insérer la valeur de x :");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int x = Integer.parseInt(reader.readLine());
        System.out.println(getClassesWithNbrOfMethodsGreaterThanX(x));
        System.out.println("Le nombre maximal de paramètres par rapport à toutes les méthodes de l'application est de : " + methodDeclarationVisitor.getNbreMaxParametre());


    }

    public void process() throws IOException {
        List<File> javaFiles = parser.getListJavaFilesForFolder(new File(this.projectPath));
        for (File file : javaFiles) {
            CompilationUnit cu = parser.parse(file);
            cu.accept(typeDeclarationVisitor);
            cu.accept(methodDeclarationVisitor);
            cu.accept(packageDeclarationVisitor);
            cu.accept(fileVisitor);
            cu.accept(methodInvocationVisitor);
            cu1.add(cu);
        }

    }


    public List<String> getTenPercentClassesWithGreaterNbrOfMethods() {
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
        List<String> resultatClasses = new ArrayList<>();
        typeDeclarationVisitor.getMapClassesNbreMethodes().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        int TenPercentClasses = ((10 * typeDeclarationVisitor.getNbreClasses()) / 100) + 1;
        for (int i = 0; i < TenPercentClasses; i++) {
            resultatClasses.add(reverseSortedMap.keySet().toArray()[i].toString());

        }
        return resultatClasses;
    }

    public List<String> getTenPercentClassesWithGreaterNbrOfAttributes() {
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
        List<String> resultatClasses = new ArrayList<>();
        typeDeclarationVisitor.getMapClassesNbreAttributs().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        int TenPercentClasses = ((10 * typeDeclarationVisitor.getNbreClasses()) / 100) + 1;
        for (int i = 0; i < TenPercentClasses; i++) {
            resultatClasses.add(reverseSortedMap.keySet().toArray()[i].toString());

        }
        return resultatClasses;
    }

    public List<String> getClassesOfBothCategories() {
        List<String> list1 = getTenPercentClassesWithGreaterNbrOfMethods();
        List<String> list2 = getTenPercentClassesWithGreaterNbrOfAttributes();
        list1.retainAll(list2);
        return list1;
    }

    public List<String> getClassesWithNbrOfMethodsGreaterThanX(int x) {
        List<String> resultatClasses = new ArrayList<>();
        typeDeclarationVisitor.getMapClassesNbreMethodes().entrySet().stream().filter(e -> e.getValue() > x);
        typeDeclarationVisitor.getMapClassesNbreMethodes().entrySet().stream().filter(e -> e.getValue() > x).forEach(e -> {
            resultatClasses.add(e.getKey());
        });
        return resultatClasses;
    }

    public void countNumberOfMethodLines() {
        for (int i = 0; i < cu1.size(); i++) {
            if (!typeDeclarationVisitor.getAllTypes().get(i).isInterface())
                cu2.add(cu1.get(i));
        }
        for (int i = 0; i < cu2.size(); i++) {
            for (MethodDeclaration method : typeDeclarationVisitor.getTypes().get(i).getMethods()) {
                int startPos = cu2.get(i).getLineNumber(method.getStartPosition());
                int endPos = cu2.get(i).getLineNumber(method.getStartPosition() + method.getLength());
                //System.out.println("nombre de ligne de la methode : "+typeDeclarationVisitor.getTypes().get(i).getName()+" "+method.getName()+" "+ ((endPos-startPos)+1));
                totalnbreLignesCodeMethode += ((endPos - startPos) + 1);

            }
        }
    }


    public void printMethodsInvForEachMethod() throws IOException, URISyntaxException {

        File resourcesDirectory = new File("src/main/resources/graph.png");
        File imgFile = new File(resourcesDirectory.getAbsolutePath());
        imgFile.delete();
        imgFile.createNewFile();
        DefaultDirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

        for (TypeDeclaration nodeClass : typeDeclarationVisitor.getTypes()) {
            MethodDeclarationVisitor visitorMethod = new MethodDeclarationVisitor();
            nodeClass.accept(visitorMethod);
            System.out.println("class : " + nodeClass.getName());
            String className = nodeClass.getName().toString();


            for (MethodDeclaration nodeMethod : visitorMethod.getMethods()) {
                MethodInvocationVisitor visitorMethodInvoction = new MethodInvocationVisitor();
                nodeMethod.accept(visitorMethodInvoction);
                String methodName = nodeMethod.getName().toString();
                System.out.println("\tmethod : " + methodName);

                List<MethodInvocation> methodInvocations = visitorMethodInvoction.getMethods();
                System.out.println("\t\tinvocations : " + methodInvocations);
                for (MethodInvocation methodInvocation : methodInvocations) {

                    if (methodInvocation.getExpression() != null) {
                        if (methodInvocation.getExpression().resolveTypeBinding() != null) {
                            g.addVertex(className + " :: " + methodName);
                            String type = methodInvocation.getExpression().resolveTypeBinding().getName();
                            System.out.println("\t\t\tType : " + methodInvocation.getExpression().resolveTypeBinding().getName());
                            g.addVertex(type + " :: " + methodInvocation.getName());
                            g.addEdge(className + " :: " + methodName, type + " :: " + methodInvocation.getName());

                        }
                    } else {

                        if (methodInvocation.resolveMethodBinding() != null) {
                            g.addVertex(className + " :: " + methodName);
                            String declaringClass = methodInvocation.resolveMethodBinding().getDeclaringClass().getName();
                            g.addVertex(declaringClass + " :: " + methodInvocation.getName());
                            g.addEdge(className + " :: " + methodName, declaringClass + " :: " + methodInvocation.getName());
                        }
                    }

                }

            }
        }
        JGraphXAdapter<String, DefaultEdge> graphAdapter = new JGraphXAdapter<String, DefaultEdge>(g);
        graphAdapter.getEdgeToCellMap().forEach((edge, cell) -> cell.setValue(null));
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());
        BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        ImageIO.write(image, "PNG", imgFile);
    }


}
