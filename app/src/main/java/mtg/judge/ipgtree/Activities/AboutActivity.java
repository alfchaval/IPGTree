package mtg.judge.ipgtree.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import mtg.judge.ipgtree.R;

public class AboutActivity extends AppCompatActivity {

    private ScrollView scroll_answers;
    private TextView txv_about;
    private ImageView imv_arrow_up, imv_arrow_down;

    private ViewTreeObserver viewTreeObserver;

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
                "Actualizado: 13-10-2019, RNA.\n" +
                        "\n" +
                        "Código de la app: https://github.com/alfchaval/Tree.git\n" +
                        "Sugerencias: alfchaval@hotmail.com\n" +
                        "\n" +
                        "Últimos cambios:\n" +
                        "• El temporizador continua en marcha aunque salgas de ahí (pero no si cierras la app).\n" +
                        "• Las vidas se guardan aunque salgas del contador de vidas (pero no si cierras la app).\n" +
                        "• Arreglado un bug al pulsar múltiples botones.\n" +
                        "• Añadida función de ocultar anotaciones en opciones.\n" +
                        "\n" +
                        "Cosas por venir:\n" +
                        "• Añadir documentos: Anuncio del HJ, guía del WER, código del juez.\n" +
                        "• Opción de buscar por palabras en los documentos.\n" +
                        "• Añadir más preguntas al quiz.\n" +
                        "\n" +
                        "Bugs conocidos:\n" +
                        "• La aplicación crashea si estando en algún documento, el quiz o el árbol te da por bloquear el móvil."
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