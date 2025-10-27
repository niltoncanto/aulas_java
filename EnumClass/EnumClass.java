enum DiaSemana {
    SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO, DOMINGO;

        @Override
        public String toString() {
                return "DIA: " + name();
        }
}

class EnumClass {
    public static void mostrarCompromisso(DiaSemana dia) {
        switch (dia) {
            case SEGUNDA:
                System.out.println(dia);
                System.out.println("dia = " + dia.name());   // <- name()
                System.out.println("dia = " + dia.ordinal());
                break; // opcional, mas recomendado
            default:
                System.out.println("Sem compromisso cadastrado.");
        }
    }

    public static void main(String[] args) {
        System.out.println("Agenda do Dia");
        System.out.println("====================");
        mostrarCompromisso(DiaSemana.SEGUNDA);
    }
}




    