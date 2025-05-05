

class Singleton {
    private static volatile Singleton instance;
    private String value;
    private Singleton(String value) {
        this.value = value;
    }
    public static Singleton getInstance(String value) {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton(value);
                }
            }
        }
        return instance;
    }
    public String getValue() {
        return value;
    }
}

public class SingletonTest {
    public static void main(String[] args) {
        Singleton singleton1 = Singleton.getInstance("1ª Instância");
        Singleton singleton2 = Singleton.getInstance("2ª Instância");
        System.out.println(singleton1 == singleton2); // true
        System.out.println(singleton1.getValue());
        System.out.println(singleton2.getValue());
    }
}


