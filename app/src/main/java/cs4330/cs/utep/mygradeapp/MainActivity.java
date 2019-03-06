package cs4330.cs.utep.mygradeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText userName = findViewById(R.id.userEdit);
        EditText password = findViewById(R.id.pinEdit);
        Button submitBtn = findViewById(R.id.submit);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userName.getText().toString() != null && password.getText().toString() != null) {
                    Intent i = new Intent("cs4330.cs.utep.mygradeapp.GradeActivity");
                    i.putExtra("username", userName.getText().toString());
                    i.putExtra("password", password.getText().toString());
                    startActivity(i);
                }
            }
        });

    }
}
