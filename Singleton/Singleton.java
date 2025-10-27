class Singleton{
    private static Singleton instance;
    private Singleton(){}
    
    public static Singleton getInstance(){
        if(instance==null){
            instance = new Singleton();
        }
        return instance;
    }
}

public class Main{
    public static void main(String[] args){
        Singleton instancia1 = Singleton.getInstance();
        System.out.println(instancia1);
        Singleton instancia2 = Singleton.getInstance();
        System.out.println(instancia2);

    }
}