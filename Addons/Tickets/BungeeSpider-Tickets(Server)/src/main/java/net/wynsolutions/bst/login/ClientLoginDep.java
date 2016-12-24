package net.wynsolutions.bst.login;

import java.io.IOException;

import com.google.api.client.googleapis.auth.clientlogin.ClientLogin;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

public class ClientLoginDep {

	private ClientLogin authenticator;
	private String username, password;
	
	public ClientLoginDep(String user, String pass) {
		this.username = user;
		this.password = pass;
	}
	
	private void login() throws IOException{
		// HttpTransport used to send login request.
	    HttpTransport transport = new NetHttpTransport();
	    try {
	      // authenticate with ClientLogin
	      authenticator = new ClientLogin();
	      authenticator.transport = transport;
	      authenticator.authTokenType = "wise";
	      authenticator.username = this.username;
	      authenticator.password = this.password;
	      authenticator.authenticate();
	      System.out.println("Authentication succeeded.");
	    } catch (HttpResponseException e) {
	      // Likely a "403 Forbidden" error.
	      System.err.println(e.getStatusMessage());
	      throw e;
	    }
	}
	
	public ClientLogin getClientLogin(){
		return this.authenticator;
	}
	
}
