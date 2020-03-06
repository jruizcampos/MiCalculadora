package com.jruizcampos.micalculadora_v2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.inputMain)
    TextInputEditText txtOperacion;
    private OperacionMat operacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        operacion = new OperacionMat();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ButtonClear)
    public void limpiarEntrada(){
        operacion.limpiar();
        txtOperacion.setText(operacion.getOperacion());
    }

    @OnClick({R.id.ButtonZero,R.id.ButtonOne,R.id.ButtonTwo,R.id.ButtonThree,R.id.ButtonFour,
            R.id.ButtonFive,R.id.ButtonSix,R.id.ButtonSeven,R.id.ButtonEight,R.id.ButtonNine,
            R.id.ButtonPlus,R.id.ButtonMinus,R.id.ButtonMult,R.id.ButtonDiv,R.id.ButtonPoint})
    public void ingresarItem(Button btn){

        char val = btn.getText().charAt(0);
        if(operacion.agregarItem(val)){
            txtOperacion.setText(operacion.getOperacion());
        }
    }

    @OnClick(R.id.ButtonResult)
    public void ejecutarOperacion(){

        if(operacion.ejecutarOperacion()){
            txtOperacion.setText(operacion.getResultado());
        }
    }
    @OnClick(R.id.imgBack)
    public void borrarItemDerecho(){
        operacion.borrarItemDerecho();
        txtOperacion.setText(operacion.getOperacion());
    }

}
