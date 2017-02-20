package info.fshi.oppnetdemo1.fragment;

import info.fshi.oppnetdemo1.R;
import info.fshi.oppnetdemo1.account.AccountManager;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AccountFragment extends Fragment {

	Context mContext;

	private final static String TAG = "AccountFragment";
	
	public AccountFragment(Context context){
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = null;
		switch(AccountManager.getManager().getState()){
		case AccountManager.STATE_NOT_AUTHORISED:
			v = inflater.inflate(R.layout.accounts_fragment_layout_authorise, container, false);
			break;
		case AccountManager.STATE_AUTHORISED:
			v = inflater.inflate(R.layout.accounts_fragment_layout, container, false);
			break;
		default:
			break;
		}
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		switch(AccountManager.getManager().getState()){
		case AccountManager.STATE_NOT_AUTHORISED:
			Button authButton = (Button) getView().findViewById(R.id.btn_auth);
			authButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AccountManager.getManager().login(mContext);
				}
			});
			break;
		case AccountManager.STATE_AUTHORISED:
			TextView accountId = (TextView) getView().findViewById(R.id.account_id);
			TextView accountExpired = (TextView) getView().findViewById(R.id.account_exp);
			TextView accountUsername = (TextView) getView().findViewById(R.id.account_usrname);
			TextView accountName = (TextView) getView().findViewById(R.id.account_name);
			TextView accountEmail = (TextView) getView().findViewById(R.id.account_email);
			accountId.setText(AccountManager.getManager().getParsedInfo().getSubject());
			accountExpired.setText(AccountManager.getManager().getParsedInfo().getExpiration().toString());
			accountUsername.setText(AccountManager.getManager().getParsedInfo().get("preferred_username").toString());
			accountName.setText(AccountManager.getManager().getParsedInfo().get("name").toString());
			accountEmail.setText(AccountManager.getManager().getParsedInfo().get("email").toString());
			break;
		case AccountManager.STATE_TOKEN_EXPIRED:
			// renew token
			break;
		default:
			break;
		}
	}



}
