/**
 * @description: 利用反射实现一个简单的通用序列化/反序列化类SimpleMapper
 * @author: luf
 * @create: 2021-12-07 19:13
 **/
public class SimpleMapperDemo {
    static class Student {
        String name;
        int age;
        Double score;

        public Student() {
        }

        public Student(String name, int age, Double score) {
            super();
            this.name = name;
            this.age = age;
            this.score = score;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", score=" + score +
                    '}';
        }
    }

    public static void main(String[] args) {
        Student luf = new Student("luf", 23, 99d);
        // 重写的toString()方法
        System.out.println("luf:" + luf);

        // 自定义的方法，将对象obj转换为字符串
        String str = SimpleMapper.toString(luf);

        // 自定义的方法：将字符串转换为对象
        Student luf2 = (Student) SimpleMapper.fromString(str);
        System.out.println(luf2);
    }

}
