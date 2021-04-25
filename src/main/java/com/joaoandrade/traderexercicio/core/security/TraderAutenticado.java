package com.joaoandrade.traderexercicio.core.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.joaoandrade.traderexercicio.domain.model.enumeration.Perfil;

public class TraderAutenticado implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> perfis;

	public TraderAutenticado() {
	}

	public TraderAutenticado(Long id, String email, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.perfis = perfis.stream().map(perfil -> new SimpleGrantedAuthority(perfil.getDescricao()))
				.collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return perfis;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean isAdmin() {
		for (GrantedAuthority authority : this.perfis) {
			if (authority.getAuthority().equalsIgnoreCase("ROLE_ADMIN")) {
				return true;
			}
		}

		return false;
	}

}
