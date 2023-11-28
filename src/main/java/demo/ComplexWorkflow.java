package demo;

import static java.lang.System.out;

public class ComplexWorkflow {

    public static void main(String[] args) {
        ComplexWorkflow workflow = new ComplexWorkflow();
        Object o = workflow.process("1234");
        out.println(o);
    }

    public Object process(String id) {
        step1(id);
        return id;
    }

    private void step1(String id) {
        step2(id);
    }

    private void step2(String id) {
        step3(id);
        step4(id);
    }

    private void step3(String id) {
        output(id);
    }

    private void step4(String id) {
        action(id);
    }

    void action(String id) {
        output(id);
    }

    private void output(String id) {
        System.err.println(id);
    }
}
