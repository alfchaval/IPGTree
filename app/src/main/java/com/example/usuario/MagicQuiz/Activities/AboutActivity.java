package com.example.usuario.MagicQuiz.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.usuario.MagicQuiz.R;

public class AboutActivity extends AppCompatActivity {

    TextView txv_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        txv_about = findViewById(R.id.txv_about);
        txv_about.setText(
                "Actualizado: 25-03-2019, RNA.\n" +
                "\n" +
                "Código de la app: https://github.com/alfchaval/Tree.git\n" +
                "Sugerencias: alfchaval@hotmail.com\n" +
                "\n" +
                "Próximamente:\n" +
                "- Terminar la sección de documentos\n" +
                "- Añadir más preguntas al quiz\n" +
                "- En el árbol expandir el tema de los ciclos para incluir casos con varios jugadores involucrados\n" +
                "- En el árbol expandir ejemplos de errores de comunicación, que ahora abarcan todo el punto 4 de las MTR\n" +
                "- Que el temporizador continúe en marcha aunque salgas de él\n" +
                "- Poder buscar en el oracle por edición + número de carta\n");
    }
}