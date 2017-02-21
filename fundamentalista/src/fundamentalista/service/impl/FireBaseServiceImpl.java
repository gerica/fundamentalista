package fundamentalista.service.impl;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import fundamentalista.entidade.Papel;
import fundamentalista.service.FireBaseService;
import fundamentalista.util.FireBaseConnector;

@Service
public class FireBaseServiceImpl implements FireBaseService {

	@Override
	public void savePapeis(List<Papel> papeis) throws FileNotFoundException {
		// DatabaseReference usersRef = connector.connection().child("papel");

		FireBaseConnector.getInstance();
		// Map<String, Papel> mapPapeis = new HashMap<String, Papel>();
		DatabaseReference postsRef = FireBaseConnector.getRef().child("papel");
		;

		for (Papel p : papeis) {
			postsRef.push().setValue(p);
			// System.out.println(p);
			// mapPapeis.put("key" + p.hashCode(), p);

		}

		// usersRef.setValue(mapPapeis);
		// System.out.println("feito");

	}

	public List<Papel> recuperarPapeis() throws FileNotFoundException {
		List<Papel> papeis = new ArrayList<>();

		FireBaseConnector.getInstance();
		// Get a reference to our posts
		DatabaseReference ref = FireBaseConnector.getRef().child("papel");

		// Attach a listener to read the data at our posts reference
		ref.addChildEventListener(new ChildEventListener() {

			@Override
			public void onCancelled(DatabaseError databaseError) {
				System.out.println("The read failed: " + databaseError.getCode());
			}

			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String arg1) {
				Papel papel = dataSnapshot.getValue(Papel.class);
				// System.out.println(papel);
				papeis.add(papel);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String arg1) {

			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String arg1) {

			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {

			}
		});
		int count = 0;
		while (papeis.size() == 0 && count <= 10) {
			try {
				Thread.sleep(1000);
				count++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println(count);
		System.out.println(papeis.size());

		return papeis;
	}

}
