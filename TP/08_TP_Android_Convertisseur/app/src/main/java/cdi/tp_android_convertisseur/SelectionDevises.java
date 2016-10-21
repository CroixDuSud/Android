package cdi.tp_android_convertisseur;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;


/**
 * Created by Bonneau on 13/10/2016.
 */

public class SelectionDevises extends DialogFragment {

    public interface SelectionDevisesListenner {
        public void onSelectDevise(String nomDevise, int idTextView);
    }

    public static SelectionDevises newInstance(int idTextView) {
        SelectionDevises dialog = new SelectionDevises();
        Bundle args = new Bundle();
        args.putInt("idTextView", idTextView);
        dialog.setArguments(args);
        return dialog;
    }

    SelectionDevisesListenner mainActivite;

    @Override
    public void onAttach(Activity activite) {
        super.onAttach(activite);
        try {
            mainActivite = (SelectionDevisesListenner)getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " doit implementer OnArticleSelectedListener");
        }
    }

/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mainActivite = (SelectionDevisesListenner)getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " doit implementer OnArticleSelectedListener");
        }
    }
 */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int idTextView = getArguments().getInt("idTextView");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Sélection de la devise");
        builder.setItems(R.array.liste_devises, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int numItem){
                // la devise a ete selectionnee
                Log.d("DIALOG", "le numéro de la devise : " + numItem);
                String listDevises[] = getResources().getStringArray(R.array.liste_devises);
                mainActivite.onSelectDevise(listDevises[numItem], idTextView);
            }
        });
        return builder.create();
    }
}
