public class Main1 {
    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();
        DigitalDisplay digital = new DigitalDisplay();
        AnalogDisplay analog = new AnalogDisplay();

        station.registerObserver(digital);
        station.registerObserver(analog);

        station.setTemperature(30);
    }
    
}

interface Observer{
    void update(int temperatura);
}

class DigitalDisplay implements Observer{
    public void update(int temperatura){
        System.out.println("Digital Display: Temperatura atual é " + temperatura + "°C");
    }
}

class AnalogDisplay implements Observer{
    public void update(int temperatura){
        System.out.println("Analog Display: Temperatura atual é " + temperatura + "°C");
    }
}

class WeatherStation{
    private int temperatura;
    private List<Observer> observers = new ArrayList<>();
    
    public void registerObserver(Observer observer){
        observers.add(observer);
    }
    
    public void removeObserver(Observer observer){
        observers.remove(observer);
    }
    
    public void notifyObservers(){
        for(Observer observer : observers){
            observer.update(temperatura);
        }
    }
    
    public void setTemperature(int temperatura){
        this.temperatura = temperatura;
        notifyObservers();
    }
}


