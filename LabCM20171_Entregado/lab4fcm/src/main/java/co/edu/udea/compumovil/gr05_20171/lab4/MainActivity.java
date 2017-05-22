package co.edu.udea.compumovil.gr05_20171.lab4;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;

    private TextView textViewSignin;

    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

<<<<<<< HEAD
            //and open profile activity
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        //initializing views
=======
        progressDialog = new ProgressDialog(this);
>>>>>>> 41d0890b19b0212cd26d7892a8427972d75f6673
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser() {

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
<<<<<<< HEAD
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Por favor ingrese el email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Por favor ingrese la contraseña",Toast.LENGTH_LONG).show();
=======
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Ingrese el email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese la contraseña", Toast.LENGTH_LONG).show();
>>>>>>> 41d0890b19b0212cd26d7892a8427972d75f6673
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registrando, espere por favor...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
<<<<<<< HEAD
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }else{
                            //display some message here
                            Toast.makeText(MainActivity.this,"Error en el registro",Toast.LENGTH_LONG).show();
=======
                        if (task.isSuccessful()) {
                            //display some message here
                            Toast.makeText(MainActivity.this, "Registro correcto", Toast.LENGTH_LONG).show();
                        } else {
                            //display some message here
                            Toast.makeText(MainActivity.this, "Usuario ya registrado", Toast.LENGTH_LONG).show();
>>>>>>> 41d0890b19b0212cd26d7892a8427972d75f6673
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {
<<<<<<< HEAD
=======
        if (view == buttonRegister) {
            registerUser();
        }

        if (view == textViewSignin) {

        }
    }

}
>>>>>>> 41d0890b19b0212cd26d7892a8427972d75f6673

        if(view == buttonSignup){
            registerUser();
        }

        if(view == textViewSignin){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}