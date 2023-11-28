package demo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static java.lang.System.out;

public class Debugger {


    // region Hello
    String event = "Demo";
    String presentation = "Debugging with IntelliJ IDEA";
    String x = "@antonarhipov"; // follow me! :)
    String company = "JetBrains";
    //endregion


    /**
     * <h1>Debugging stream chains with Lambdas</h1>
     * <ul style="font-size:18px"/>
     * <li>Breakpoints for lambdas</li>
     * <li>Run to cursor</li>
     * <li>(Smart) Step into lambdas</li>
     * <li>Trace current stream chain</li>
     * </ul>
     */
    public static class Lambdas {
        public static void main(String[] args) {
            List<String> reader = List.of("aaaa", "bbbbb", "cccc", "dddd", "ee");
            List<String> lines = reader.stream().filter(l -> l.length() > 3).filter(l -> !l.startsWith("a")).map(l -> l.substring(0, 2)).toList();
            out.println(lines);
        }
    }


    /**
     * <h1>Method breakpoints</h1>
     * <ul style="font-size:18px"/>
     * <li>Good for interface methods</li>
     * <li>"Catch any invocation!"</li>
     * </ul>
     */
    public static class MethodBreakpoints {
        public static void main(String[] args) {
            BaseInterface i = Somewhere.getObject();
            out.println(i.boo());
        }

        interface BaseInterface {
            String foo(); //unused?

            String boo();
        }

        //region somewhere
        static class Somewhere {
            static BaseInterface getObject() {
                doSomething();

                class Clazz implements BaseInterface {
                    @Override
                    public String foo() {
                        return "Clazz.foo";
                    }

                    @Override
                    public String boo() {
                        return "Clazz.boo";
                    }
                }
                return new Clazz();
            }

            //region something
            private static void doSomething() {
                try {
                    //region hidden
                    BaseInterface my = new BaseInterface() {
                        @Override
                        public String foo() {
                            out.println("Hello from Somewhere!");
                            return null;
                        }

                        @Override
                        public String boo() {
                            return null;
                        }
                    };
                    my.getClass().getMethod("f" + "oo").invoke(my);
                    //endregion
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
            //endregion
        }
        //endregion
    }


    /**
     * <h1>Breakpoints</h1>
     * <ul style="font-size:18px"/>
     * <li>Non-suspending breakpoints</li>
     * <li>Evaluate and log</li>
     * </ul>
     */
    public static class Breakpoints {
        public static class Cache {

            static Cache instance;

            static Cache getInstance(int i) {
                if (instance == null) {
                    instance = new Cache();
                }
                return instance;
            }

            public static void main(String[] args) {
                for (int i = 0; i < 10; i++) {
                    System.out.println(getInstance(i));
                }
            }
        }
    }
}