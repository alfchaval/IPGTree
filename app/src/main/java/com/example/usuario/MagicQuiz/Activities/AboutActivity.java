package com.example.usuario.MagicQuiz.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.usuario.MagicQuiz.R;

public class AboutActivity extends AppCompatActivity {

    ScrollView scroll_answers;
    TextView txv_about;
    ImageView imv_arrow_up, imv_arrow_down;

    ViewTreeObserver viewTreeObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        linkViews();
        setListeners();
        aboutText();
    }

    public void aboutText() {
        txv_about.setText(
                "Actualizado: 23-04-2019, RNA.\n" +
                        "\n" +
                        "Código de la app: https://github.com/alfchaval/Tree.git\n" +
                        "Sugerencias: alfchaval@hotmail.com\n" +
                        "\n" +
                        "Cosas por venir:\n" +
                        "- En la búsqueda por edición y número, que si una edición tiene 20 cartas, la carta número 3 sea la 03 y no la 0003.\n" +
                        "- Contadores de veneno en el contador de vidas.\n" +
                        "- Añadir documentos: CR, AMTR, Proceso de descalificación, Anuncio del HJ, guía del WER, código del juez.\n" +
                        "- Opción de buscar por palabras en los documentos.\n" +
                        "- Añadir más preguntas al quiz y luego hacer que solo se carguen 10 preguntas al azar de un conjunto de preguntas mayor.\n" +
                        "- Expandir el tema de errores de comunicación en el árbol.\n" +
                        "- Que el temporizador continúe en marcha aunque salgas de ahí.\n" +
                        "- Añadir en cada documento la fecha de la última actualización del mismo.\n" +
                        "- Añadir sección de enlaces con recursos útiles como la dirección del foro o el email de certificaciones.\n" +
                        "- Cambiar el color del tiempo del contador descendiente cuando se vaya a números negativos.\n" +
                        "- Añadir vista horizontal al temporizador."
        );
    }

    public void linkViews() {
        scroll_answers = findViewById(R.id.scrollview);
        txv_about = findViewById(R.id.txv_about);
        imv_arrow_up = findViewById(R.id.imv_arrow_up);
        imv_arrow_down = findViewById(R.id.imv_arrow_down);
        viewTreeObserver = scroll_answers.getViewTreeObserver();
    }

    public void setListeners() {
        imv_arrow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll_answers.fullScroll(View.FOCUS_UP);
            }
        });
        imv_arrow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll_answers.fullScroll(View.FOCUS_DOWN);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Arrows disapear when you full scroll to let you read the first/last question
            scroll_answers.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    showOrHideArrows();
                }
            });
            //Also you don't want to see them if there aren't answers outside the screen
            viewTreeObserver.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    showOrHideArrows();
                }
            });
        }
        else {
            //Older versions can't use OnScrollChangeListener, so instead of disapear, the arrows are semitransparent
            imv_arrow_down.setAlpha(0.4f);
            imv_arrow_up.setAlpha(0.4f);
            //They still disapear if there aren't answers outside the screen
            viewTreeObserver.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    showOrHideArrowsOldDevices();
                }
            });
        }
    }

    //Arrows disapear when you full scroll to let you read the first/last question
    public void showOrHideArrows() {
        if(scroll_answers.canScrollVertically(-1)) {
            imv_arrow_up.setVisibility(View.VISIBLE);
        }
        else {
            imv_arrow_up.setVisibility(View.INVISIBLE);
        }
        if(scroll_answers.canScrollVertically(1)) {
            imv_arrow_down.setVisibility(View.VISIBLE);
        }
        else {
            imv_arrow_down.setVisibility(View.INVISIBLE);
        }
    }

    //In older versions you can't hide an arrow after scrolling, so you always show both arrows
    public void showOrHideArrowsOldDevices() {
        //You really don't need the "canScrollVertically(-1)"
        if(scroll_answers.canScrollVertically(1)) {
            imv_arrow_up.setVisibility(View.VISIBLE);
            imv_arrow_down.setVisibility(View.VISIBLE);
        }
        else {
            imv_arrow_up.setVisibility(View.INVISIBLE);
            imv_arrow_down.setVisibility(View.INVISIBLE);
        }
    }
}