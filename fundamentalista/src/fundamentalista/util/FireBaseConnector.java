package fundamentalista.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.conn.params.ConnConnectionParamBean;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fundamentalista.entidade.Fundamento;
import fundamentalista.entidade.Papel;

public class FireBaseConnector {
	private static String chave = "/home/rogerio/git/fundamentalista/fundamentalista/src/carteira-investimento-firebase-adminsdk-avt6f-39e464b0f8.json";

	// public static void main(String[] args) throws FileNotFoundException {
	// FirebaseOptions options = new FirebaseOptions.Builder()
	// .setServiceAccount(new FileInputStream(chave))
	// .setDatabaseUrl("https://carteira-investimento.firebaseio.com/")
	// .build();
	// FirebaseApp.initializeApp(options);
	// }

	private static FireBaseConnector instance = null;
	private static DatabaseReference ref;

	private FireBaseConnector() {

	}

	public static FireBaseConnector getInstance() {
		if (instance == null) {
			instance = new FireBaseConnector();
			try {
				instance.connection();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		return instance;
	}

	public static DatabaseReference getRef() {
		return ref;
	}

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream serviceAccount = new FileInputStream(chave);

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
				.setDatabaseUrl("https://carteira-investimento.firebaseio.com/").build();

		// FirebaseApp.initializeApp(options);

		// Initialize the default app
		FirebaseApp defaultApp = FirebaseApp.initializeApp(options);

		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference ref = database.getReference();

		DatabaseReference usersRef = ref.child("papel");

		Map<String, Papel> papeis = new HashMap<String, Papel>();
		Papel p = new Papel();
		Fundamento f = new Fundamento();
		f.setP_l(25.5);
		p.setNome("teste1");
		p.setFundamento(f);
		papeis.put(p.getNome(), p);

		usersRef.setValue(papeis);
		System.out.println("feito");
	}

	private void connection() throws FileNotFoundException {
		FileInputStream serviceAccount = new FileInputStream(chave);

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
				.setDatabaseUrl("https://carteira-investimento.firebaseio.com/").build();

		// Initialize the default app
		FirebaseApp defaultApp = FirebaseApp.initializeApp(options);

		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		ref = database.getReference();
	}
}
