
import java.util.ArrayList;
import java.util.List;

/* todos os observadores devem implementar esse método */ 
interface Observer { 
    void update(int temperature); 
}

class DigitalDisplay implements Observer {
    public void update(int temperature) {
        System.out.println("Display Digital: Temperatura atual é " + temperature + "°C");
    }
}

class AnalogDisplay implements Observer {
    public void update(int temperature) {
        System.out.println("Display Analógico: Temperatura atual é " + temperature + "°C");
    }
}




class WeatherStation {
    private int temperature;
    private List<Observer> observers = new ArrayList<>();
    // Adiciona um observer à lista
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }
    // Remove um observer da lista
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    // Notifica todos os observers registrados
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature);
        }
    }
    // Define a temperatura e notifica os observers
    public void setTemperature(int temperature) {
        this.temperature = temperature;
        notifyObservers();
    }
}

// Classe principal com o método main
public class Main {
    public static void main(String[] args) {
        // Criação da estação e displays
        WeatherStation station = new WeatherStation();
        DigitalDisplay digital = new DigitalDisplay();
        AnalogDisplay analog = new AnalogDisplay();
        // Registro dos displays como observers
        station.registerObserver(digital);
        station.registerObserver(analog);
        // Atualização da temperatura (dispara notificação)
        station.setTemperature(25);
    }
}




