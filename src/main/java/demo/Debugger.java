package demo;

import org.jetbrains.annotations.Debug;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

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
                    //region hidden
                    my.getClass().getMethod("f"+"oo").invoke(my);
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





    static class Renderers {

        public static void main(String[] args) {

            List<Student> students = List.of(
                    new Student("Anton", List.of("Math", "History")),
                    new Student("Jodie", List.of("English", "History")),
                    new Student("Max", List.of("Biology", "Sports")),
                    new Student("Anton", List.of("Chemistry", "Music")));

            for (Student student : students) {
                out.println(student);
            }

            List<Integer> integers = Stream.generate(new Random()::nextInt).limit(200).toList();
            out.println("integers = " + integers);
        }

        //region debug
        @Debug.Renderer(text = "name + ',  ' + courses.size()",
                childrenArray = "courses.toArray()",
                hasChildren = "!courses.isEmpty()")
        //endregion
        static class Student {
            String name;
            List<String> courses;

            public Student(String name, List<String> courses) {
                this.name = name;
                this.courses = courses;
            }
        }
    }










    /**
     * <h1>Filters & Watches</h1>
     * <ul style="font-size:18px"/>
     * <li>Caller filters</li>
     * <li>Instance filters</li>
     * <li>Inline watches</li>
     * <li>Class-level watches</li>
     * <li>Expression (inline) watches</li>
     * </ul>
     */
    static class Filters {

        public static void main(String[] args) {
            List<User> users = List.of(
                    new User("Anton"),
                    new User("Anton"),
                    new User("Joe")
            );
            for (int i = 0; i < 10; i++) {
                for (User user : users) {
                    user.inc();
                    System.out.println(user);
                }
            }
        }
    }
}


