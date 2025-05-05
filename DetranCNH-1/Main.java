class Main {
  public static void main(String[] args) {
    Cnh motorista = new Cnh();
    System.out.println(motorista.getTipo());
    motorista.setTipo('C');
    System.out.println("Carteira tipo: " + motorista.getTipo());
    motorista.setPontos(41);
    int pontos = motorista.getPontos();
    
    if(pontos<=40 && pontos>30){
      motorista.setSituacao(2);
    else if (pontos>40){
      motorista.setSituacao(3);
    }else{
      motorista.setSituacao(1);
    }
    
    }
  }
}