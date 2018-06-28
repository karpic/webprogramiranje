package org.arsenije.webproject.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.arsenije.webproject.beans.LoginCredetials;
import org.arsenije.webproject.beans.Token;
import org.arsenije.webproject.beans.User;
import org.arsenije.webproject.services.UsersService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/authentication")
public class Authentication {
	public static final String key = "webproject";
	
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(LoginCredetials loginCredentials) {

        try {

            // Authenticate the user using the credentials provided
        	User user = authenticate(loginCredentials.getUsername(), loginCredentials.getPassword());
            // Issue a token for the user
            String token = issueToken(loginCredentials.getUsername());
            Token tokenModel = new Token();
            // Return the token on the response
            tokenModel.setRole(user.getRole());
            tokenModel.setUsername(loginCredentials.getUsername());
            tokenModel.setToken(token);
            return Response.ok(tokenModel).build();

        } catch (Exception e) {
        	e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }      
    }

    private User authenticate(String username, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid  
    	UsersService usersService = new UsersService();
    	User user = usersService.getUser(username);
    	if(!user.getPassword().equals(password)){
    		throw new Exception();
    	}
    	return user;
    }

    private String issueToken(String username) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
    	//TODO POSTAVI EXPIRE TIME
    	String compactJws = Jwts.builder()
    			  .setSubject(username)
    			  .setIssuer("webproject")
    			  .signWith(SignatureAlgorithm.HS512, key)
    			  .compact();
    	//TODO ZAPISI U BAZU
    	return compactJws;
    }
    
    public static String validateToken(String authorizationHeader) {
		// Check if it was issued by the server and if it's not expired
		// Throw an Exception if the token is invalid
		String token = authorizationHeader.substring("Bearer".length()).trim();
		Jws<Claims> claims = Jwts.parser()
				.requireIssuer("webproject")
				.setSigningKey(Authentication.key)
				.parseClaimsJws(token);
		return claims.getBody().getSubject();
	}
}
