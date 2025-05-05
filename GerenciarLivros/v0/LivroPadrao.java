import java.util.Calendar;
import java.util.Date;

import Livro.Status;

public class LivroPadrao extends Livro {
    private Date dataDeDevolucao;

    public LivroPadrao(String titulo, String autor, String ISBN) {
        super(titulo, autor, ISBN);
    }

    @Override
    public String reservar() {
        if (this.status == Status.DISPONIVEL) {
            this.status = Status.RESERVADO;
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 7);
            dataDeDevolucao = c.getTime();
            return "Livro reservado com sucesso. Data de devolução: " + dataDeDevolucao;
        } else {
            return "Livro já está reservado.";
        }
    }

}

