package constantes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatarData {

    public SimpleDateFormat getFormatoData(Date formato) {
        SimpleDateFormat formatoDara = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatoDara;
    }
}
