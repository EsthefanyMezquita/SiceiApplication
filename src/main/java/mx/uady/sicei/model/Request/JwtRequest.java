package mx.uady.sicei.model.request;

import java.io.Serializable;

public class JwtRequest implements Serializable {
	private static final long serialVersionUID = 5926468583005150707L;
	private String username;
	private String password;
	
    /**
    * Constructor estandar
    * @return void
    *
    */
	public JwtRequest()
	{}

    /**
    * Request del JWT
    * @param username String
    * @param password String
    *
    */
	public JwtRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

    /**
    * Obtiene el username del Token
    *
    */
	public String getUsername() {
		return this.username;
	}

    /**
    * Devuelve el username del Token
    * @param username String
    *
    */
	public void setUsername(String username) {
		this.username = username;
	}

    /**
    * Obtiene el password del Token
    *
    */
	public String getPassword() {
		return this.password;
	}

    /**
    * Devuelve el password del Token
    * @param password String
    *
    */
	public void setPassword(String password) {
		this.password = password;
	}
}