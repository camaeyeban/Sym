public class IRGenerator {
    private AbstractSyntaxTreeNode ast;
    IRTable table = new IRTable();

    public IRGenerator(AbstractSyntaxTreeNode ast) {
        this.ast = ast;
    }

    public IRTable generate() {
        table.add(ast);

        return this.table;
    }
}