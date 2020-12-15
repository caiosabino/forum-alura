package br.com.alura.forum.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private	Long	id;
	private	String	name;
	private	String	password;
	@Column(nullable	=	false,	unique	=	true)
	private	String	email;
	@ManyToMany(fetch	=	FetchType.EAGER)
	private List<Role> authorities	=	new ArrayList<>();

	/**
	 * @deprecated
	 */
	public User() {	}

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return this.id;
	}

	public	String	getName() {
		return	name;
	}
	@Override
	public Collection<?	extends GrantedAuthority> getAuthorities()	{
		return this.authorities;
	}
	@Override
	public	String	getPassword() {
		return this.password;
	}
	@Override
	public	String	getUsername() {
		return this.getEmail();
	}
	public	String	getEmail() {
		return this.email;
	}
	@Override
	public	boolean	isAccountNonExpired() {
		return true;
	}
	@Override
	public	boolean	isAccountNonLocked() {
		return true;
	}
	@Override
	public	boolean	isCredentialsNonExpired() {
		return true;
	}
	@Override
	public	boolean	isEnabled() {
		return true;
	}
}
